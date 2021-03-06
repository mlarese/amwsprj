//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.03 at 03:15:27 PM EDT 
//


package it.oiritaly.data.models.amazon;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}Header"/>
 *         &lt;element name="MessageType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="CatPIL"/>
 *               &lt;enumeration value="Customer"/>
 *               &lt;enumeration value="FulfillmentCenter"/>
 *               &lt;enumeration value="FulfillmentOrderCancellationRequest"/>
 *               &lt;enumeration value="FulfillmentOrderRequest"/>
 *               &lt;enumeration value="Image"/>
 *               &lt;enumeration value="Inventory"/>
 *               &lt;enumeration value="Item"/>
 *               &lt;enumeration value="Listings"/>
 *               &lt;enumeration value="Loyalty"/>
 *               &lt;enumeration value="MultiChannelOrderReport"/>
 *               &lt;enumeration value="NavigationReport"/>
 *               &lt;enumeration value="Offer"/>
 *               &lt;enumeration value="OrderAcknowledgement"/>
 *               &lt;enumeration value="OrderAdjustment"/>
 *               &lt;enumeration value="OrderFulfillment"/>
 *               &lt;enumeration value="OrderNotificationReport"/>
 *               &lt;enumeration value="OrderReport"/>
 *               &lt;enumeration value="Override"/>
 *               &lt;enumeration value="Price"/>
 *               &lt;enumeration value="ProcessingReport"/>
 *               &lt;enumeration value="Product"/>
 *               &lt;enumeration value="ProductImage"/>
 *               &lt;enumeration value="Relationship"/>
 *               &lt;enumeration value="ReverseItem"/>
 *               &lt;enumeration value="SettlementReport"/>
 *               &lt;enumeration value="Store"/>
 *               &lt;enumeration value="WebstoreItem"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{}MarketplaceName" minOccurs="0"/>
 *         &lt;element name="PurgeAndReplace" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Message" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MessageID" type="{}IDNumber"/>
 *                   &lt;element name="OperationType" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Update"/>
 *                         &lt;enumeration value="Delete"/>
 *                         &lt;enumeration value="PartialUpdate"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;choice>
 *                     &lt;element ref="{}CatPIL"/>
 *                     &lt;element ref="{}Customer"/>
 *                     &lt;element ref="{}FulfillmentCenter"/>
 *                     &lt;element ref="{}FulfillmentOrderCancellationRequest"/>
 *                     &lt;element ref="{}FulfillmentOrderRequest"/>
 *                     &lt;element ref="{}Image"/>
 *                     &lt;element ref="{}Inventory"/>
 *                     &lt;element ref="{}Item"/>
 *                     &lt;element ref="{}Listings"/>
 *                     &lt;element ref="{}Loyalty"/>
 *                     &lt;element ref="{}MultiChannelOrderReport"/>
 *                     &lt;element ref="{}NavigationReport"/>
 *                     &lt;element ref="{}Offer"/>
 *                     &lt;element ref="{}OrderAcknowledgement"/>
 *                     &lt;element ref="{}OrderAdjustment"/>
 *                     &lt;element ref="{}OrderFulfillment"/>
 *                     &lt;element ref="{}OrderNotificationReport"/>
 *                     &lt;element ref="{}OrderReport"/>
 *                     &lt;element ref="{}Override"/>
 *                     &lt;element ref="{}Price"/>
 *                     &lt;element ref="{}ProcessingReport"/>
 *                     &lt;element ref="{}Product"/>
 *                     &lt;element ref="{}ProductImage"/>
 *                     &lt;element ref="{}Relationship"/>
 *                     &lt;element ref="{}ReverseItem"/>
 *                     &lt;element ref="{}SettlementReport"/>
 *                     &lt;element ref="{}Store"/>
 *                     &lt;element ref="{}WebstoreItem"/>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "header",
    "messageType",
    "marketplaceName",
    "purgeAndReplace",
    "effectiveDate",
    "message"
})
@XmlRootElement(name = "AmazonEnvelope")
public class AmazonEnvelope {

    @XmlElement(name = "Header", required = true)
    protected Header header;
    @XmlElement(name = "MessageType", required = true)
    protected String messageType;
    @XmlElement(name = "MarketplaceName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String marketplaceName;
    @XmlElement(name = "PurgeAndReplace")
    protected Boolean purgeAndReplace;
    @XmlElement(name = "EffectiveDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    @XmlElement(name = "Message", required = true)
    protected List<AmazonEnvelope.Message> message;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link Header }
     *     
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link Header }
     *     
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the messageType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Sets the value of the messageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageType(String value) {
        this.messageType = value;
    }

    /**
     * 
     * 						The MarketplaceName is only supported for 
     * 						Override feeds.
     * 						If included here, the MarketplaceName will
     * 						apply to all messages in the feed.
     * 						
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarketplaceName() {
        return marketplaceName;
    }

    /**
     * Sets the value of the marketplaceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarketplaceName(String value) {
        this.marketplaceName = value;
    }

    /**
     * Gets the value of the purgeAndReplace property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPurgeAndReplace() {
        return purgeAndReplace;
    }

    /**
     * Sets the value of the purgeAndReplace property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPurgeAndReplace(Boolean value) {
        this.purgeAndReplace = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the message property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AmazonEnvelope.Message }
     * 
     * 
     */
    public List<AmazonEnvelope.Message> getMessage() {
        if (message == null) {
            message = new ArrayList<AmazonEnvelope.Message>();
        }
        return this.message;
    }

    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="MessageID" type="{}IDNumber"/>
     *         &lt;element name="OperationType" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Update"/>
     *               &lt;enumeration value="Delete"/>
     *               &lt;enumeration value="PartialUpdate"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;choice>
     *           &lt;element ref="{}CatPIL"/>
     *           &lt;element ref="{}Customer"/>
     *           &lt;element ref="{}FulfillmentCenter"/>
     *           &lt;element ref="{}FulfillmentOrderCancellationRequest"/>
     *           &lt;element ref="{}FulfillmentOrderRequest"/>
     *           &lt;element ref="{}Image"/>
     *           &lt;element ref="{}Inventory"/>
     *           &lt;element ref="{}Item"/>
     *           &lt;element ref="{}Listings"/>
     *           &lt;element ref="{}Loyalty"/>
     *           &lt;element ref="{}MultiChannelOrderReport"/>
     *           &lt;element ref="{}NavigationReport"/>
     *           &lt;element ref="{}Offer"/>
     *           &lt;element ref="{}OrderAcknowledgement"/>
     *           &lt;element ref="{}OrderAdjustment"/>
     *           &lt;element ref="{}OrderFulfillment"/>
     *           &lt;element ref="{}OrderNotificationReport"/>
     *           &lt;element ref="{}OrderReport"/>
     *           &lt;element ref="{}Override"/>
     *           &lt;element ref="{}Price"/>
     *           &lt;element ref="{}ProcessingReport"/>
     *           &lt;element ref="{}Product"/>
     *           &lt;element ref="{}ProductImage"/>
     *           &lt;element ref="{}Relationship"/>
     *           &lt;element ref="{}ReverseItem"/>
     *           &lt;element ref="{}SettlementReport"/>
     *           &lt;element ref="{}Store"/>
     *           &lt;element ref="{}WebstoreItem"/>
     *         &lt;/choice>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "messageID",
        "operationType",
        "catPIL",
        "customer",
        "fulfillmentCenter",
        "fulfillmentOrderCancellationRequest",
        "fulfillmentOrderRequest",
        "image",
        "inventory",
        "item",
        "listings",
        "loyalty",
        "multiChannelOrderReport",
        "navigationReport",
        "offer",
        "orderAcknowledgement",
        "orderAdjustment",
        "orderFulfillment",
        "orderNotificationReport",
        "orderReport",
        "override",
        "price",
        "processingReport",
        "product",
        "productImage",
        "relationship",
        "reverseItem",
        "settlementReport",
        "store",
        "webstoreItem"
    })
    public static class Message {

        @XmlElement(name = "MessageID", required = true)
        protected Integer messageID;
        @XmlElement(name = "OperationType")
        protected String operationType;
        @XmlElement(name = "CatPIL")
        protected it.oiritaly.data.models.amazon.CatPIL catPIL;
        @XmlElement(name = "Customer")
        protected it.oiritaly.data.models.amazon.Customer customer;
        @XmlElement(name = "FulfillmentCenter")
        protected it.oiritaly.data.models.amazon.FulfillmentCenter fulfillmentCenter;
        @XmlElement(name = "FulfillmentOrderCancellationRequest")
        protected it.oiritaly.data.models.amazon.FulfillmentOrderCancellationRequest fulfillmentOrderCancellationRequest;
        @XmlElement(name = "FulfillmentOrderRequest")
        protected it.oiritaly.data.models.amazon.FulfillmentOrderRequest fulfillmentOrderRequest;
        @XmlElement(name = "Image")
        protected Image image;
        @XmlElement(name = "Inventory")
        protected it.oiritaly.data.models.amazon.Inventory inventory;
        @XmlElement(name = "Item")
        protected Item item;
        @XmlElement(name = "Listings")
        protected it.oiritaly.data.models.amazon.Listings listings;
        @XmlElement(name = "Loyalty")
        protected it.oiritaly.data.models.amazon.Loyalty loyalty;
        @XmlElement(name = "MultiChannelOrderReport")
        protected it.oiritaly.data.models.amazon.MultiChannelOrderReport multiChannelOrderReport;
        @XmlElement(name = "NavigationReport")
        protected it.oiritaly.data.models.amazon.NavigationReport navigationReport;
        @XmlElement(name = "Offer")
        protected it.oiritaly.data.models.amazon.Offer offer;
        @XmlElement(name = "OrderAcknowledgement")
        protected it.oiritaly.data.models.amazon.OrderAcknowledgement orderAcknowledgement;
        @XmlElement(name = "OrderAdjustment")
        protected it.oiritaly.data.models.amazon.OrderAdjustment orderAdjustment;
        @XmlElement(name = "OrderFulfillment")
        protected it.oiritaly.data.models.amazon.OrderFulfillment orderFulfillment;
        @XmlElement(name = "OrderNotificationReport")
        protected it.oiritaly.data.models.amazon.OrderNotificationReport orderNotificationReport;
        @XmlElement(name = "OrderReport")
        protected it.oiritaly.data.models.amazon.OrderReport orderReport;
        @XmlElement(name = "Override")
        protected Override override;
        @XmlElement(name = "Price")
        protected Price price;
        @XmlElement(name = "ProcessingReport")
        protected it.oiritaly.data.models.amazon.ProcessingReport processingReport;
        @XmlElement(name = "Product")
        protected Product product;
        @XmlElement(name = "ProductImage")
        protected it.oiritaly.data.models.amazon.ProductImage productImage;
        @XmlElement(name = "Relationship")
        protected Relationship relationship;
        @XmlElement(name = "ReverseItem")
        protected it.oiritaly.data.models.amazon.ReverseItem reverseItem;
        @XmlElement(name = "SettlementReport")
        protected it.oiritaly.data.models.amazon.SettlementReport settlementReport;
        @XmlElement(name = "Store")
        protected it.oiritaly.data.models.amazon.Store store;
        @XmlElement(name = "WebstoreItem")
        protected it.oiritaly.data.models.amazon.WebstoreItem webstoreItem;

        /**
         * Gets the value of the messageID property.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getMessageID() {
            return messageID;
        }

        /**
         * Sets the value of the messageID property.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setMessageID(Integer value) {
            this.messageID = value;
        }

        /**
         * Gets the value of the operationType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOperationType() {
            return operationType;
        }

        /**
         * Sets the value of the operationType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOperationType(String value) {
            this.operationType = value;
        }

        /**
         * Gets the value of the catPIL property.
         * 
         * @return
         *     possible object is
         *     {@link CatPIL }
         *     
         */
        public it.oiritaly.data.models.amazon.CatPIL getCatPIL() {
            return catPIL;
        }

        /**
         * Sets the value of the catPIL property.
         * 
         * @param value
         *     allowed object is
         *     {@link CatPIL }
         *     
         */
        public void setCatPIL(it.oiritaly.data.models.amazon.CatPIL value) {
            this.catPIL = value;
        }

        /**
         * Gets the value of the customer property.
         * 
         * @return
         *     possible object is
         *     {@link Customer }
         *     
         */
        public it.oiritaly.data.models.amazon.Customer getCustomer() {
            return customer;
        }

        /**
         * Sets the value of the customer property.
         * 
         * @param value
         *     allowed object is
         *     {@link Customer }
         *     
         */
        public void setCustomer(it.oiritaly.data.models.amazon.Customer value) {
            this.customer = value;
        }

        /**
         * Gets the value of the fulfillmentCenter property.
         * 
         * @return
         *     possible object is
         *     {@link FulfillmentCenter }
         *     
         */
        public it.oiritaly.data.models.amazon.FulfillmentCenter getFulfillmentCenter() {
            return fulfillmentCenter;
        }

        /**
         * Sets the value of the fulfillmentCenter property.
         * 
         * @param value
         *     allowed object is
         *     {@link FulfillmentCenter }
         *     
         */
        public void setFulfillmentCenter(it.oiritaly.data.models.amazon.FulfillmentCenter value) {
            this.fulfillmentCenter = value;
        }

        /**
         * Gets the value of the fulfillmentOrderCancellationRequest property.
         * 
         * @return
         *     possible object is
         *     {@link FulfillmentOrderCancellationRequest }
         *     
         */
        public it.oiritaly.data.models.amazon.FulfillmentOrderCancellationRequest getFulfillmentOrderCancellationRequest() {
            return fulfillmentOrderCancellationRequest;
        }

        /**
         * Sets the value of the fulfillmentOrderCancellationRequest property.
         * 
         * @param value
         *     allowed object is
         *     {@link FulfillmentOrderCancellationRequest }
         *     
         */
        public void setFulfillmentOrderCancellationRequest(it.oiritaly.data.models.amazon.FulfillmentOrderCancellationRequest value) {
            this.fulfillmentOrderCancellationRequest = value;
        }

        /**
         * Gets the value of the fulfillmentOrderRequest property.
         * 
         * @return
         *     possible object is
         *     {@link FulfillmentOrderRequest }
         *     
         */
        public it.oiritaly.data.models.amazon.FulfillmentOrderRequest getFulfillmentOrderRequest() {
            return fulfillmentOrderRequest;
        }

        /**
         * Sets the value of the fulfillmentOrderRequest property.
         * 
         * @param value
         *     allowed object is
         *     {@link FulfillmentOrderRequest }
         *     
         */
        public void setFulfillmentOrderRequest(it.oiritaly.data.models.amazon.FulfillmentOrderRequest value) {
            this.fulfillmentOrderRequest = value;
        }

        /**
         * Gets the value of the image property.
         * 
         * @return
         *     possible object is
         *     {@link Image }
         *     
         */
        public Image getImage() {
            return image;
        }

        /**
         * Sets the value of the image property.
         * 
         * @param value
         *     allowed object is
         *     {@link Image }
         *     
         */
        public void setImage(Image value) {
            this.image = value;
        }

        /**
         * Gets the value of the inventory property.
         * 
         * @return
         *     possible object is
         *     {@link Inventory }
         *     
         */
        public it.oiritaly.data.models.amazon.Inventory getInventory() {
            return inventory;
        }

        /**
         * Sets the value of the inventory property.
         * 
         * @param value
         *     allowed object is
         *     {@link Inventory }
         *     
         */
        public void setInventory(it.oiritaly.data.models.amazon.Inventory value) {
            this.inventory = value;
        }

        /**
         * Gets the value of the item property.
         * 
         * @return
         *     possible object is
         *     {@link Item }
         *     
         */
        public Item getItem() {
            return item;
        }

        /**
         * Sets the value of the item property.
         * 
         * @param value
         *     allowed object is
         *     {@link Item }
         *     
         */
        public void setItem(Item value) {
            this.item = value;
        }

        /**
         * Gets the value of the listings property.
         * 
         * @return
         *     possible object is
         *     {@link Listings }
         *     
         */
        public it.oiritaly.data.models.amazon.Listings getListings() {
            return listings;
        }

        /**
         * Sets the value of the listings property.
         * 
         * @param value
         *     allowed object is
         *     {@link Listings }
         *     
         */
        public void setListings(it.oiritaly.data.models.amazon.Listings value) {
            this.listings = value;
        }

        /**
         * Gets the value of the loyalty property.
         * 
         * @return
         *     possible object is
         *     {@link Loyalty }
         *     
         */
        public it.oiritaly.data.models.amazon.Loyalty getLoyalty() {
            return loyalty;
        }

        /**
         * Sets the value of the loyalty property.
         * 
         * @param value
         *     allowed object is
         *     {@link Loyalty }
         *     
         */
        public void setLoyalty(it.oiritaly.data.models.amazon.Loyalty value) {
            this.loyalty = value;
        }

        /**
         * Gets the value of the multiChannelOrderReport property.
         * 
         * @return
         *     possible object is
         *     {@link MultiChannelOrderReport }
         *     
         */
        public it.oiritaly.data.models.amazon.MultiChannelOrderReport getMultiChannelOrderReport() {
            return multiChannelOrderReport;
        }

        /**
         * Sets the value of the multiChannelOrderReport property.
         * 
         * @param value
         *     allowed object is
         *     {@link MultiChannelOrderReport }
         *     
         */
        public void setMultiChannelOrderReport(it.oiritaly.data.models.amazon.MultiChannelOrderReport value) {
            this.multiChannelOrderReport = value;
        }

        /**
         * Gets the value of the navigationReport property.
         * 
         * @return
         *     possible object is
         *     {@link NavigationReport }
         *     
         */
        public it.oiritaly.data.models.amazon.NavigationReport getNavigationReport() {
            return navigationReport;
        }

        /**
         * Sets the value of the navigationReport property.
         * 
         * @param value
         *     allowed object is
         *     {@link NavigationReport }
         *     
         */
        public void setNavigationReport(it.oiritaly.data.models.amazon.NavigationReport value) {
            this.navigationReport = value;
        }

        /**
         * Gets the value of the offer property.
         * 
         * @return
         *     possible object is
         *     {@link Offer }
         *     
         */
        public it.oiritaly.data.models.amazon.Offer getOffer() {
            return offer;
        }

        /**
         * Sets the value of the offer property.
         * 
         * @param value
         *     allowed object is
         *     {@link Offer }
         *     
         */
        public void setOffer(it.oiritaly.data.models.amazon.Offer value) {
            this.offer = value;
        }

        /**
         * Gets the value of the orderAcknowledgement property.
         * 
         * @return
         *     possible object is
         *     {@link OrderAcknowledgement }
         *     
         */
        public it.oiritaly.data.models.amazon.OrderAcknowledgement getOrderAcknowledgement() {
            return orderAcknowledgement;
        }

        /**
         * Sets the value of the orderAcknowledgement property.
         * 
         * @param value
         *     allowed object is
         *     {@link OrderAcknowledgement }
         *     
         */
        public void setOrderAcknowledgement(it.oiritaly.data.models.amazon.OrderAcknowledgement value) {
            this.orderAcknowledgement = value;
        }

        /**
         * Gets the value of the orderAdjustment property.
         * 
         * @return
         *     possible object is
         *     {@link OrderAdjustment }
         *     
         */
        public it.oiritaly.data.models.amazon.OrderAdjustment getOrderAdjustment() {
            return orderAdjustment;
        }

        /**
         * Sets the value of the orderAdjustment property.
         * 
         * @param value
         *     allowed object is
         *     {@link OrderAdjustment }
         *     
         */
        public void setOrderAdjustment(it.oiritaly.data.models.amazon.OrderAdjustment value) {
            this.orderAdjustment = value;
        }

        /**
         * Gets the value of the orderFulfillment property.
         * 
         * @return
         *     possible object is
         *     {@link OrderFulfillment }
         *     
         */
        public it.oiritaly.data.models.amazon.OrderFulfillment getOrderFulfillment() {
            return orderFulfillment;
        }

        /**
         * Sets the value of the orderFulfillment property.
         * 
         * @param value
         *     allowed object is
         *     {@link OrderFulfillment }
         *     
         */
        public void setOrderFulfillment(it.oiritaly.data.models.amazon.OrderFulfillment value) {
            this.orderFulfillment = value;
        }

        /**
         * Gets the value of the orderNotificationReport property.
         * 
         * @return
         *     possible object is
         *     {@link OrderNotificationReport }
         *     
         */
        public it.oiritaly.data.models.amazon.OrderNotificationReport getOrderNotificationReport() {
            return orderNotificationReport;
        }

        /**
         * Sets the value of the orderNotificationReport property.
         * 
         * @param value
         *     allowed object is
         *     {@link OrderNotificationReport }
         *     
         */
        public void setOrderNotificationReport(it.oiritaly.data.models.amazon.OrderNotificationReport value) {
            this.orderNotificationReport = value;
        }

        /**
         * Gets the value of the orderReport property.
         * 
         * @return
         *     possible object is
         *     {@link OrderReport }
         *     
         */
        public it.oiritaly.data.models.amazon.OrderReport getOrderReport() {
            return orderReport;
        }

        /**
         * Sets the value of the orderReport property.
         * 
         * @param value
         *     allowed object is
         *     {@link OrderReport }
         *     
         */
        public void setOrderReport(it.oiritaly.data.models.amazon.OrderReport value) {
            this.orderReport = value;
        }

        /**
         * Gets the value of the override property.
         * 
         * @return
         *     possible object is
         *     {@link Override }
         *     
         */
        public Override getOverride() {
            return override;
        }

        /**
         * Sets the value of the override property.
         * 
         * @param value
         *     allowed object is
         *     {@link Override }
         *     
         */
        public void setOverride(Override value) {
            this.override = value;
        }

        /**
         * Gets the value of the price property.
         * 
         * @return
         *     possible object is
         *     {@link Price }
         *     
         */
        public Price getPrice() {
            return price;
        }

        /**
         * Sets the value of the price property.
         * 
         * @param value
         *     allowed object is
         *     {@link Price }
         *     
         */
        public void setPrice(Price value) {
            this.price = value;
        }

        /**
         * Gets the value of the processingReport property.
         * 
         * @return
         *     possible object is
         *     {@link ProcessingReport }
         *     
         */
        public it.oiritaly.data.models.amazon.ProcessingReport getProcessingReport() {
            return processingReport;
        }

        /**
         * Sets the value of the processingReport property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProcessingReport }
         *     
         */
        public void setProcessingReport(it.oiritaly.data.models.amazon.ProcessingReport value) {
            this.processingReport = value;
        }

        /**
         * Gets the value of the product property.
         * 
         * @return
         *     possible object is
         *     {@link Product }
         *     
         */
        public Product getProduct() {
            return product;
        }

        /**
         * Sets the value of the product property.
         * 
         * @param value
         *     allowed object is
         *     {@link Product }
         *     
         */
        public void setProduct(Product value) {
            this.product = value;
        }

        /**
         * Gets the value of the productImage property.
         * 
         * @return
         *     possible object is
         *     {@link ProductImage }
         *     
         */
        public it.oiritaly.data.models.amazon.ProductImage getProductImage() {
            return productImage;
        }

        /**
         * Sets the value of the productImage property.
         * 
         * @param value
         *     allowed object is
         *     {@link ProductImage }
         *     
         */
        public void setProductImage(it.oiritaly.data.models.amazon.ProductImage value) {
            this.productImage = value;
        }

        /**
         * Gets the value of the relationship property.
         * 
         * @return
         *     possible object is
         *     {@link Relationship }
         *     
         */
        public Relationship getRelationship() {
            return relationship;
        }

        /**
         * Sets the value of the relationship property.
         * 
         * @param value
         *     allowed object is
         *     {@link Relationship }
         *     
         */
        public void setRelationship(Relationship value) {
            this.relationship = value;
        }

        /**
         * Gets the value of the reverseItem property.
         * 
         * @return
         *     possible object is
         *     {@link ReverseItem }
         *     
         */
        public it.oiritaly.data.models.amazon.ReverseItem getReverseItem() {
            return reverseItem;
        }

        /**
         * Sets the value of the reverseItem property.
         * 
         * @param value
         *     allowed object is
         *     {@link ReverseItem }
         *     
         */
        public void setReverseItem(it.oiritaly.data.models.amazon.ReverseItem value) {
            this.reverseItem = value;
        }

        /**
         * Gets the value of the settlementReport property.
         * 
         * @return
         *     possible object is
         *     {@link SettlementReport }
         *     
         */
        public it.oiritaly.data.models.amazon.SettlementReport getSettlementReport() {
            return settlementReport;
        }

        /**
         * Sets the value of the settlementReport property.
         * 
         * @param value
         *     allowed object is
         *     {@link SettlementReport }
         *     
         */
        public void setSettlementReport(it.oiritaly.data.models.amazon.SettlementReport value) {
            this.settlementReport = value;
        }

        /**
         * Gets the value of the store property.
         * 
         * @return
         *     possible object is
         *     {@link Store }
         *     
         */
        public it.oiritaly.data.models.amazon.Store getStore() {
            return store;
        }

        /**
         * Sets the value of the store property.
         * 
         * @param value
         *     allowed object is
         *     {@link Store }
         *     
         */
        public void setStore(it.oiritaly.data.models.amazon.Store value) {
            this.store = value;
        }

        /**
         * Gets the value of the webstoreItem property.
         * 
         * @return
         *     possible object is
         *     {@link WebstoreItem }
         *     
         */
        public it.oiritaly.data.models.amazon.WebstoreItem getWebstoreItem() {
            return webstoreItem;
        }

        /**
         * Sets the value of the webstoreItem property.
         * 
         * @param value
         *     allowed object is
         *     {@link WebstoreItem }
         *     
         */
        public void setWebstoreItem(it.oiritaly.data.models.amazon.WebstoreItem value) {
            this.webstoreItem = value;
        }

    }

}
