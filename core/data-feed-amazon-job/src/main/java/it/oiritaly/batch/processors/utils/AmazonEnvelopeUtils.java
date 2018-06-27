package it.oiritaly.batch.processors.utils;

/**
 * Created by marco on 13/04/17.
 */
public class AmazonEnvelopeUtils {

    public enum MessageType {

        /*
        <xsd:enumeration value="CatPIL"/>
        <xsd:enumeration value="Customer"/>
        <xsd:enumeration value="FulfillmentCenter"/>
        <xsd:enumeration value="FulfillmentOrderCancellationRequest"/>
        <xsd:enumeration value="FulfillmentOrderRequest"/>
                                    <xsd:enumeration value="Image"/>
        <xsd:enumeration value="Inventory"/>
        <xsd:enumeration value="Item"/>
        <xsd:enumeration value="Listings"/>
        <xsd:enumeration value="Loyalty"/>
        <xsd:enumeration value="MultiChannelOrderReport"/>
        <xsd:enumeration value="NavigationReport"/>
        <xsd:enumeration value="Offer"/>
        <xsd:enumeration value="OrderAcknowledgement"/>
        <xsd:enumeration value="OrderAdjustment"/>
        <xsd:enumeration value="OrderFulfillment"/>
        <xsd:enumeration value="OrderNotificationReport"/>
        <xsd:enumeration value="OrderReport"/>
        <xsd:enumeration value="Override"/>
        <xsd:enumeration value="Price"/>
        <xsd:enumeration value="ProcessingReport"/>
        <xsd:enumeration value="Product"/>
        <xsd:enumeration value="ProductImage"/>
        <xsd:enumeration value="Relationship"/>
        <xsd:enumeration value="ReverseItem"/>
        <xsd:enumeration value="SettlementReport"/>
        <xsd:enumeration value="Store"/>
        <xsd:enumeration value="WebstoreItem"/>
         */


        PRODUCT("Product"),
        INVENTORY("Inventory"),
        PRICE("Price"),
        PRODUCTIMAGE("ProductImage"),
        RELATIONSHIP("Relationship")
        ;

        private String value;

        MessageType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public static MessageType getEnum(String value) {
            for(MessageType v : values())
                if(v.getValue().equalsIgnoreCase(value)) return v;
            throw new IllegalArgumentException();
        }
    }

}
