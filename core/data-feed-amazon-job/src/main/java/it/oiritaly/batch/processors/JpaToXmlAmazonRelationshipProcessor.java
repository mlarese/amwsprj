package it.oiritaly.batch.processors;


import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.processors.utils.*;
import it.oiritaly.data.models.amazon.*;
import it.oiritaly.data.models.jpa.DataFeed;
import it.oiritaly.data.models.jpa.Output;
import it.oiritaly.data.models.jpa.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.Override;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JpaToXmlAmazonRelationshipProcessor extends JpaToXmlAmazonProcessor {

    private static ObjectFactory factory = new ObjectFactory();

    private static MwsConfigurationProperties configurationProperties;

    @Autowired
    public JpaToXmlAmazonRelationshipProcessor(MwsConfigurationProperties msp) {
        super(msp);
    }

    @Override
    public AmazonEnvelope process(DataFeed input) throws Exception {
        this.setMessageType(AmazonEnvelopeUtils.MessageType.RELATIONSHIP);
        this.setAmazonMessages(toAmazonMessages(input));
        return super.process(input);
    }

    private static List<AmazonEnvelope.Message> toAmazonMessages(DataFeed dataFeed){
        List<AmazonEnvelope.Message> messages = new ArrayList<>();

        for(Product p: dataFeed.getRoot().getProducts()){

            Output out=new Output();
            out.setValue("Amazon");

            boolean isValid;

            try {
                isValid = OiritalyUtils.isValid(p);
            }
            catch (IllegalArgumentException e) {
                log.error("The category id " + p.getCategory().getCode() + " is not valid.");
                continue;
            }

            //only for valid children with output set to amazon
            if(p.getOutput().contains(out) && isValid && p.getIsGroupParent()!=null && p.getIsGroupParent()==false) {
                AmazonEnvelope.Message message = factory.createAmazonEnvelopeMessage();
                message.setMessageID(messages.size() + 1);
                message.setRelationship(toAmazonRelationship(p));
                messages.add(message);
            } else {
                log.info("Product "+p.getId()+" is not valid and/or is not set to output in "+out.getValue());
            }
        }

        return messages;
    }

    private static it.oiritaly.data.models.amazon.Relationship toAmazonRelationship(Product p){

       /*
        Map<String, CustomAttribute> customAttributes  = p.getCustomAttributes().stream()
                .collect(Collectors.toMap(customAttribute -> customAttribute.getName(), customAttribute -> customAttribute));
        */

        it.oiritaly.data.models.amazon.Relationship relationship = factory.createRelationship();
        relationship.setParentSKU(p.getItemGroupId().toString());

        Relationship.Relation relation = factory.createRelationshipRelation();
        relation.setSKU(p.getMpn().toString());
        relation.setType("Variation");

        relationship.getRelation().add(relation);



        return relationship;
    }

}