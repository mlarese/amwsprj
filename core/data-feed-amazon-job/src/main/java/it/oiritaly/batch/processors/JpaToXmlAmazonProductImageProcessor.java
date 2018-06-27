package it.oiritaly.batch.processors;


import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.processors.utils.AmazonEnvelopeUtils;
import it.oiritaly.batch.processors.utils.AmazonImageTypeUtils;
import it.oiritaly.batch.processors.utils.OiritalyUtils;
import it.oiritaly.data.models.amazon.*;
import it.oiritaly.data.models.jpa.DataFeed;
import it.oiritaly.data.models.jpa.ImageLink;
import it.oiritaly.data.models.jpa.Output;
import it.oiritaly.data.models.jpa.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.Override;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JpaToXmlAmazonProductImageProcessor extends JpaToXmlAmazonProcessor {

    private static ObjectFactory factory = new ObjectFactory();

    private static MwsConfigurationProperties configurationProperties;

    @Autowired
    public JpaToXmlAmazonProductImageProcessor(MwsConfigurationProperties msp) {
        super(msp);
    }


    @Override
    public AmazonEnvelope process(DataFeed input) throws Exception {
        this.setMessageType(AmazonEnvelopeUtils.MessageType.PRODUCTIMAGE);
        this.setAmazonMessages(toAmazonMessages(input));
        return super.process(input);
    }

    private static List<AmazonEnvelope.Message> toAmazonMessages(DataFeed dataFeed) {
        List<AmazonEnvelope.Message> messages = new ArrayList<>();

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
                for (ImageLink imageLink : p.getImageLinks()) {
                    AmazonEnvelope.Message message = factory.createAmazonEnvelopeMessage();
                    message.setMessageID(messages.size() + 1);
                    ProductImage image = factory.createProductImage();
                    image.setSKU(p.getMpn().toString());
                    URI imageLocation = imageLink.getLink();
                    if (imageLocation != null)
                        image.setImageLocation(imageLink.getLink().toString());
                    else
                        log.error("The image URI is missing.");
                    try {
                        image.setImageType(AmazonImageTypeUtils.Type.getEnum(imageLink.getLabel()).toString());
                    } catch (IllegalArgumentException e) {
                        log.error("The ImageLink " + imageLink.getLabel() + " is not valid.");
                        continue;
                    }
                    message.setProductImage(image);
                    messages.add(message);
                    log.info(String.valueOf(messages.size()));
                }
            }
        }
        return messages;
    }
}