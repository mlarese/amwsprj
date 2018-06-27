/******************************************************************************* 
 *  Copyright 2009 Amazon Services.
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *
 *  You may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 * ***************************************************************************** 
 *
 *  Marketplace Web Service Java Library
 *  API Version: 2009-01-01
 *  Generated: Wed Feb 18 13:28:48 PST 2009 
 *
 */


package it.oiritaly.batch.api.mws;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.GetFeedSubmissionResultRequest;
import com.amazonaws.mws.model.GetFeedSubmissionResultResponse;
import com.amazonaws.mws.model.ResponseMetadata;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurator;
import it.oiritaly.data.models.amazon.ProcessingReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.math.BigInteger;

@Slf4j
@Service
public class GetFeedSubmissionResult {

    private MwsConfigurationProperties configurationProperties;

    @Autowired
    public GetFeedSubmissionResult(MwsConfigurationProperties msp) {
        configurationProperties = msp;
    }

    public String init(String serviceUrl, String feedSubmissionId) {

        MarketplaceWebServiceClient service = MwsConfigurator.getMarketplaceWebService(configurationProperties, serviceUrl);

        final String merchantId = configurationProperties.getMerchantId();

        GetFeedSubmissionResultRequest request = new GetFeedSubmissionResultRequest();
        request.setMerchant(merchantId);


        request.setFeedSubmissionId(feedSubmissionId);

        // Note that depending on the size of the feed sent in, and the number of errors and warnings,
        // the result can reach sizes greater than 1GB. For this reason we recommend that you _always_ 
        // program to MWS in a streaming fashion. Otherwise, as your business grows you may silently reach
        // the in-memory size limit and have to re-work your solution.
        //

        OutputStream processingResult = null;
        try {
            processingResult = new FileOutputStream(configurationProperties.getOutputBasePath() + feedSubmissionId + ".xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        request.setFeedSubmissionResultOutputStream(processingResult);

        return invokeGetFeedSubmissionResult(service, request);
    }


    /**
     * Get Feed Submission Result  request sample
     * retrieves the feed processing report
     *
     * @param service instance of MarketplaceWebService service
     * @param request Action to invoke
     */
    public static String invokeGetFeedSubmissionResult(MarketplaceWebService service, GetFeedSubmissionResultRequest request) {
        try {

            GetFeedSubmissionResultResponse response = service.getFeedSubmissionResult(request);

            System.out.println("GetFeedSubmissionResult Action Response");
            System.out.println("=============================================================================");
            System.out.println();

            System.out.print("    GetFeedSubmissionResultResponse");
            System.out.println();
            System.out.print("    GetFeedSubmissionResultResult");
            System.out.println();
            System.out.print("            MD5Checksum");
            System.out.println();
            System.out.print("                " + response.getGetFeedSubmissionResultResult().getMD5Checksum());
            System.out.println();
            if (response.isSetResponseMetadata()) {
                System.out.print("        ResponseMetadata");
                System.out.println();
                ResponseMetadata responseMetadata = response.getResponseMetadata();
                if (responseMetadata.isSetRequestId()) {
                    System.out.print("            RequestId");
                    System.out.println();
                    System.out.print("                " + responseMetadata.getRequestId());
                    System.out.println();
                }
            }
            System.out.println();

            System.out.println("Feed Processing Result");
            System.out.println("=============================================================================");
            System.out.println();
            System.out.println(response.getResponseHeaderMetadata());
            System.out.println();
            System.out.println();

            return response.toXML();

        } catch (MarketplaceWebServiceException ex) {
            System.out.println("Caught Exception: " + ex.getMessage());
            System.out.println("Response Status Code: " + ex.getStatusCode());
            System.out.println("Error Code: " + ex.getErrorCode());
            System.out.println("Error Type: " + ex.getErrorType());
            System.out.println("Request ID: " + ex.getRequestId());
            System.out.println("XML: " + ex.getXML());
            System.out.println("ResponseHeaderMetadata: " + ex.getResponseHeaderMetadata());

            return "{ko:\"" + ex + "\"}";
        }
    }


    public String writeFeedSubmissionResult(String serviceUrl, String feedSubmissionId) throws MarketplaceWebServiceException {

        MarketplaceWebServiceClient service = MwsConfigurator.getMarketplaceWebService(configurationProperties, serviceUrl);

        final String merchantId = configurationProperties.getMerchantId();

        GetFeedSubmissionResultRequest request = new GetFeedSubmissionResultRequest();
        request.setMerchant(merchantId);

        request.setFeedSubmissionId(feedSubmissionId);

        // Note that depending on the size of the feed sent in, and the number of errors and warnings,
        // the result can reach sizes greater than 1GB. For this reason we recommend that you _always_
        // program to MWS in a streaming fashion. Otherwise, as your business grows you may silently reach
        // the in-memory size limit and have to re-work your solution.
        //

        String fileName = null;

        OutputStream processingResult = null;
        try {
            fileName = configurationProperties.getOutputBasePath() + feedSubmissionId + ".xml";
            processingResult = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        request.setFeedSubmissionResultOutputStream(processingResult);

        GetFeedSubmissionResultResponse response = service.getFeedSubmissionResult(request);

        return fileName;
    }


    public ProcessingReport.ProcessingSummary parseFeedSubmissionResultFile(String fileName) {
        ProcessingReport.ProcessingSummary summary = new ProcessingReport.ProcessingSummary();

        try {

            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(fileName));
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("MessagesProcessed")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        summary.setMessagesProcessed(new BigInteger(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("MessagesSuccessful")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        summary.setMessagesSuccessful(new BigInteger(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("MessagesWithError")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        summary.setMessagesWithError(new BigInteger(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("MessagesWithWarning")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        summary.setMessagesWithWarning(new BigInteger(xmlEvent.asCharacters().getData()));
                    }
                } else if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("ProcessingSummary")) {
                        return summary;
                    }

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return summary;

    }


}