package it.oiritaly.batch.processors;


import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.processors.utils.AmazonEnvelopeUtils;
import it.oiritaly.data.models.amazon.AmazonEnvelope;
import it.oiritaly.data.models.amazon.Header;
import it.oiritaly.data.models.amazon.ObjectFactory;
import it.oiritaly.data.models.jpa.DataFeed;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@Getter
@Setter
public class JpaToXmlAmazonProcessor implements ItemProcessor<DataFeed, AmazonEnvelope> {

    private static ObjectFactory factory = new ObjectFactory();

    private static MwsConfigurationProperties configurationProperties;

    private List<AmazonEnvelope.Message> amazonMessages;
    private AmazonEnvelopeUtils.MessageType messageType;

    @Autowired
    public JpaToXmlAmazonProcessor(MwsConfigurationProperties msp) {
        configurationProperties = msp;
    }


    @Override
    public AmazonEnvelope process(DataFeed input) throws Exception {
        //if (log.isDebugEnabled()) {
            log.info("Processing datafeed id " + input.get_id());
        //}

        AmazonEnvelope output = toAmazonEnvelope(input);
        output.setHeader(toAmazonHeader(input));
        output.getMessage().addAll(getAmazonMessages());

        //if (log.isDebugEnabled()) {
            log.info("Processed datafeed id " + output);
        //}

        return output;
    }

    private AmazonEnvelope toAmazonEnvelope(DataFeed dataFeed) {
        AmazonEnvelope envelope = factory.createAmazonEnvelope();
        GregorianCalendar calendar = new GregorianCalendar();
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        calendar.setTimeZone(timeZone);
        calendar.setTime(dataFeed.getParseDateDateTime());

        try {
            XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            envelope.setEffectiveDate(xcal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        envelope.setMessageType(this.getMessageType().toString());
        envelope.setPurgeAndReplace(false);
        return envelope;
    }

    private static Header toAmazonHeader(DataFeed dataFeed) {
        Header header = factory.createHeader();
        header.setDocumentVersion("1.01"); //Amazon supports only version 1.01
        header.setMerchantIdentifier(configurationProperties.getMerchantId());
        return header;
    }



}