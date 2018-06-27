package it.oiritaly.batch.api.mws;

import com.amazonservices.mws.client.MwsUtl;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrders;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersException;
import com.amazonservices.mws.orders._2013_09_01.model.ListOrdersResponse;
import com.amazonservices.mws.orders._2013_09_01.model.ResponseHeaderMetadata;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurator;
import it.oiritaly.batch.api.mws.orders.model.ListOrdersRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ListOrders {

    private MwsConfigurationProperties configurationProperties;

    @Autowired
    public ListOrders(MwsConfigurationProperties msp) {
        configurationProperties = msp;
    }

    /**
     * Call the service, log response and exceptions.
     *
     * @param client
     * @param request
     *
     * @return The response.
     */
    public static ListOrdersResponse invokeListOrders(
            MarketplaceWebServiceOrders client,
            ListOrdersRequest request) {
        try {
            // Call the service.
            ListOrdersResponse response = client.listOrders(request);
            ResponseHeaderMetadata rhmd = response.getResponseHeaderMetadata();
            // We recommend logging every the request id and timestamp of every call.
            System.out.println("Response:");
            System.out.println("RequestId: "+rhmd.getRequestId());
            System.out.println("Timestamp: "+rhmd.getTimestamp());
            String responseJSON = response.toJSON();
            return response;
        } catch (MarketplaceWebServiceOrdersException ex) {
            // Exception properties are important for diagnostics.
            System.out.println("Service Exception:");
            ResponseHeaderMetadata rhmd = ex.getResponseHeaderMetadata();
            if(rhmd != null) {
                System.out.println("RequestId: "+rhmd.getRequestId());
                System.out.println("Timestamp: "+rhmd.getTimestamp());
            }
            System.out.println("Message: "+ex.getMessage());
            System.out.println("StatusCode: "+ex.getStatusCode());
            System.out.println("ErrorCode: "+ex.getErrorCode());
            System.out.println("ErrorType: "+ex.getErrorType());
            throw ex;
        }
    }

    public String init(String serviceUrl) {


        MarketplaceWebServiceOrdersClient client = MwsConfigurator.getClient(configurationProperties,serviceUrl);

        // Create a request.
        ListOrdersRequest request = new ListOrdersRequest(configurationProperties);
        String sellerId = configurationProperties.getMerchantId();
        request.setSellerId(sellerId);
        //String mwsAuthToken = "token";
        //request.setMWSAuthToken(mwsAuthToken);
        XMLGregorianCalendar createdAfter = MwsUtl.getDTF().newXMLGregorianCalendar();
        createdAfter.setDay(30);
        createdAfter.setMonth(11);
        createdAfter.setYear(2016);
        createdAfter.setTime(23,00,00);
        createdAfter.setTimezone(0);
        request.setCreatedAfter(createdAfter);
        XMLGregorianCalendar createdBefore = MwsUtl.getDTF().newXMLGregorianCalendar();
        createdBefore=null;
        request.setCreatedBefore(createdBefore);
        XMLGregorianCalendar lastUpdatedAfter = MwsUtl.getDTF().newXMLGregorianCalendar();
        lastUpdatedAfter=null;
        request.setLastUpdatedAfter(lastUpdatedAfter);
        XMLGregorianCalendar lastUpdatedBefore = MwsUtl.getDTF().newXMLGregorianCalendar();
        lastUpdatedBefore=null;
        request.setLastUpdatedBefore(lastUpdatedBefore);
        List<String> orderStatus = new ArrayList<String>();
        request.setOrderStatus(orderStatus);
        List<String> marketplaceId = new ArrayList<String>();
        marketplaceId.add("APJ6JRA9NG5V4");
        request.setMarketplaceId(marketplaceId);
        List<String> fulfillmentChannel = new ArrayList<String>();
        request.setFulfillmentChannel(fulfillmentChannel);
        List<String> paymentMethod = new ArrayList<String>();
        request.setPaymentMethod(paymentMethod);
        String buyerEmail = "example";
        buyerEmail=null;
        request.setBuyerEmail(buyerEmail);
        String sellerOrderId = "example";
        sellerOrderId = null;
        request.setSellerOrderId(sellerOrderId);
        Integer maxResultsPerPage = 100;
        request.setMaxResultsPerPage(maxResultsPerPage);
        List<String> tfmShipmentStatus = new ArrayList<String>();
        request.setTFMShipmentStatus(tfmShipmentStatus);

        // Make the call.
        ListOrdersResponse lor= ListOrders.invokeListOrders(client, request);
        return lor.toJSON();
    }

}
