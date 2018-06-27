package it.oiritaly.batch.processors;


import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.processors.utils.AmazonEnvelopeUtils;
import it.oiritaly.batch.processors.utils.OiritalyUtils;
import it.oiritaly.data.models.amazon.*;
import it.oiritaly.data.models.jpa.DataFeed;
import it.oiritaly.data.models.jpa.Output;
import it.oiritaly.data.models.jpa.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.Override;
import java.time.ZoneId;
import java.util.*;

@Slf4j
public class JpaToXmlAmazonPriceProcessor extends JpaToXmlAmazonProcessor {

    private static ObjectFactory factory = new ObjectFactory();

    private static MwsConfigurationProperties configurationProperties;

    @Autowired
    public JpaToXmlAmazonPriceProcessor(MwsConfigurationProperties msp) {
        super(msp);
    }


    @Override
    public AmazonEnvelope process(DataFeed input) throws Exception {
        this.setMessageType(AmazonEnvelopeUtils.MessageType.PRICE);
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

            if(p.getOutput().contains(out) && isValid) {
                AmazonEnvelope.Message message = factory.createAmazonEnvelopeMessage();
                message.setMessageID(messages.size() + 1);
                message.setPrice(toAmazonPrice(p, dataFeed));
                messages.add(message);
            }
        }

        return messages;
    }

    private static Price toAmazonPrice(Product p, DataFeed dataFeed){
        Price price = factory.createPrice();
        price.setSKU(p.getMpn().toString());
        price.setStandardPrice(toAmazonStandardPrice(p));
        price.setSale(toAmazonSale(p, dataFeed));
        return price;
    }

    private static OverrideCurrencyAmount toAmazonStandardPrice(Product p){
        return OiritalyUtils.OIRCurrencyConverter(p, p.getContentLanguage().toString());
    }

    private static Price.Sale toAmazonSale(Product p, DataFeed dataFeed){
        Price.Sale sale = factory.createPriceSale();
        OverrideCurrencyAmount oca = OiritalyUtils.OIRCurrencyConverter(p, p.getContentLanguage().toString());
        sale.setSalePrice(oca);
        GregorianCalendar calendar = new GregorianCalendar();
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        calendar.setTimeZone(timeZone);
        calendar.setTime(dataFeed.getParseDateDateTime());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        try {
            XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            sale.setStartDate(xcal);
            Duration duration = DatatypeFactory.newInstance().newDuration("P0Y0M0DT0H1M0S");
            XMLGregorianCalendar xcal2= (XMLGregorianCalendar)xcal.clone();
            xcal2.add(duration);
            sale.setEndDate(xcal2);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        return sale;
    }

}