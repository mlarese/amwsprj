package it.oiritaly.batch.processors;


import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.processors.utils.AmazonEnvelopeUtils;
import it.oiritaly.batch.processors.utils.OiritalyUtils;
import it.oiritaly.data.models.amazon.*;
import it.oiritaly.data.models.jpa.DataFeed;
import it.oiritaly.data.models.jpa.Output;
import it.oiritaly.data.models.jpa.Product;
import it.oiritaly.data.models.jpa.rest.Brand;
import it.oiritaly.data.models.jpa.rest.Rule;
import it.oiritaly.data.repositories.jpa.rest.RuleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.Override;
import java.util.*;

@Slf4j
public class JpaToXmlAmazonInventoryProcessor extends JpaToXmlAmazonProcessor {

    private static ObjectFactory factory = new ObjectFactory();

    private static MwsConfigurationProperties configurationProperties;

    @Autowired
    public JpaToXmlAmazonInventoryProcessor(MwsConfigurationProperties msp) {
        super(msp);
    }

    @Autowired
    private RuleRepository ruleRepository;

    @Override
    public AmazonEnvelope process(DataFeed input) throws Exception {
        this.setMessageType(AmazonEnvelopeUtils.MessageType.INVENTORY);
        this.setAmazonMessages(toAmazonMessages(input));
        return super.process(input);
    }

    private List<AmazonEnvelope.Message> toAmazonMessages(DataFeed dataFeed) {
        List<AmazonEnvelope.Message> messages = new ArrayList<>();

        Date now = new Date();

        List<Rule> rules = ruleRepository.findAllByFromDateLessThanEqualAndToDateGreaterThanEqualAndIsActiveTrue(now, now);
        HashSet<String> blackBrands = new HashSet<String>();

        for (Rule rule : rules) {
            for (Brand brand : rule.getBrands()) {
                blackBrands.add(brand.getName());
            }
        }

        for (Product p : dataFeed.getRoot().getProducts()) {

            Output out = new Output();
            out.setValue("Amazon");

            boolean isValid;

            try {
                isValid = OiritalyUtils.isValid(p);
            } catch (IllegalArgumentException e) {
                log.error("The category id " + p.getCategory().getCode() + " is not valid.");
                continue;
            }

            if (p.getOutput().contains(out) && isValid) {
                AmazonEnvelope.Message message = factory.createAmazonEnvelopeMessage();
                if (blackBrands.contains(p.getBrand()))
                    message.setOperationType("Delete");
                else
                    message.setOperationType("Update");
                message.setMessageID(messages.size() + 1);
                message.setInventory(toAmazonInventory(p));
                messages.add(message);
            }
        }

        return messages;
    }

    private static Inventory toAmazonInventory(Product p) {
        Inventory inventory = factory.createInventory();
        inventory.setSKU(p.getMpn().toString());
        int quantity = p.getQuantity().intValue();
        inventory.setQuantity(quantity);
        inventory.setFulfillmentLatency(2);

        //questa cosa fa un bug grande come una montagna maledetto Amazon
        //https://sellercentral.amazon.com/forums/thread.jspa?threadID=169390
        /*
        Available
        This element can be used instead of the "Quantity" element below and is only valid for US merchant accounts. For merchant accounts in Canada, Europe, and Japan, use the "Quantity" element instead.
        Quantity
        For US merchant accounts, this element can be used instead of the "Available" element above. For merchant accounts in Canada, Europe, and Japan, a quantity is required for the "Quantity" element
         */

        //if (quantity <= 0) {
        //    inventory.setAvailable(false);
        //}



        return inventory;
    }
}