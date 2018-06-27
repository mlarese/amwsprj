package it.oiritaly.batch.api.mws;

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


import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.SubmitFeedResponse;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurator;
import it.oiritaly.batch.api.mws.model.SubmitFeedRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Service
public class SubmitFeedAsync {

    private MwsConfigurationProperties configurationProperties;

    @Autowired
    public SubmitFeedAsync(MwsConfigurationProperties msp) {
        configurationProperties = msp;
    }

    public List<Future<SubmitFeedResponse>> init(String serviceUrl, SubmitFeedRequest.FeedType feedType, IdList idList) {
        MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();

        String fileName = configurationProperties.getOutputBasePath();

        switch (feedType) {
            case _POST_PRODUCT_DATA_:
                fileName += idList.getId().size() == 1 ? idList.getId().get(0).toString() + configurationProperties.getProductDataFilename() : configurationProperties.getProductDataFilename();
                break;
            case _POST_PRODUCT_RELATIONSHIP_DATA_:
                fileName += idList.getId().size() == 1 ? idList.getId().get(0).toString() + configurationProperties.getRelationshipDataFilename() : configurationProperties.getRelationshipDataFilename();
                break;
            case _POST_INVENTORY_AVAILABILITY_DATA_:
                fileName += idList.getId().size() == 1 ? idList.getId().get(0).toString() + configurationProperties.getInventoryAvailabilityDataFilename() : configurationProperties.getInventoryAvailabilityDataFilename();
                break;
            case _POST_PRODUCT_PRICING_DATA_:
                fileName += idList.getId().size() == 1 ? idList.getId().get(0).toString() + configurationProperties.getProductPricingDataFilename() : configurationProperties.getProductPricingDataFilename();
                break;
            case _POST_PRODUCT_IMAGE_DATA_:
                fileName += idList.getId().size() == 1 ? idList.getId().get(0).toString() + configurationProperties.getProductImageFilename() : configurationProperties.getProductImageFilename();
                break;
        }

        /************************************************************************
         * The argument (35) set below is the number of threads client should
         * spawn for processing.
         ***********************************************************************/

        config.setMaxAsyncThreads(35);

        /************************************************************************
         * You can also try advanced configuration options. Available options are:
         *
         *  - Signature Version
         *  - Proxy Host and Proxy Port
         *  - User Agent String to be sent to Marketplace Web Service
         *
         ***********************************************************************/

        /************************************************************************
         * Instantiate Http Client Implementation of Marketplace Web Service
         ***********************************************************************/

        MarketplaceWebService service = MwsConfigurator.getMarketplaceWebService(configurationProperties, serviceUrl);

        SubmitFeedRequest requestOne = new SubmitFeedRequest();
        requestOne.setMerchant(configurationProperties.getMerchantId());

        requestOne.setMarketplaceIdList(idList);

        //requestOne.setMWSAuthToken(sellerDevAuthToken);

        requestOne.setFeedType(feedType.toString());

        // MWS exclusively offers a streaming interface for uploading your feeds. This is because
        // feed sizes can grow past the 1GB range - and as your business grows you could otherwise
        // silently reach the feed size where your in-memory solution will no longer work, leaving you
        // puzzled as to why a solution that worked for a long time suddenly stopped working though
        // you made no changes. For the same reason, we strongly encourage you to generate your feeds to
        // local disk then upload them directly from disk to MWS via Java - without buffering them in Java
        // memory in their entirety.
        //

        //requestOne.setFeedContent( new FileInputStream("my-feed-1.xml" /*or "my-flat-file-1.txt" if you use flat files*/);

        //InputStream is = this.getClass().getResourceAsStream("/OirDataFeed_ES_submit.xml");

        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String md5 = null;
        try {
            md5 = requestOne.computeContentMD5HeaderValue(is);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        requestOne.setContentMD5(md5);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        requestOne.setFeedContent(is);

        List<SubmitFeedRequest> requests = new ArrayList<SubmitFeedRequest>();
        requests.add(requestOne);

        try {
            return invokeSubmitFeed(service, requests);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }


    /**
     * Submit Feed request sample
     * Uploads a file for processing together with the necessary
     * metadata to process the file, such as which type of feed it is.
     * PurgeAndReplace if true means that your existing e.g. inventory is
     * wiped out and replace with the contents of this feed - use with
     * caution (the default is false).
     *
     * @param service  instance of MarketplaceWebService service
     * @param requests list of requests to process
     */
    public static List<Future<SubmitFeedResponse>> invokeSubmitFeed(MarketplaceWebService service, List<SubmitFeedRequest> requests) throws IOException {
        final StringWriter sw = new StringWriter();
        JsonFactory factory = new JsonFactory();
        JsonGenerator generator = factory.createGenerator(sw);

        generator.writeStartObject();
        generator.writeArrayFieldStart("responses");

        List<Future<SubmitFeedResponse>> responses = new ArrayList<Future<SubmitFeedResponse>>();
        for (SubmitFeedRequest request : requests) {
            System.out.println("Sending request for feed type "+request.getFeedType()+" to marketplace "+request.getMarketplaceIdList().getId().get(0).toString());
            responses.add(service.submitFeedAsync(request));
        }

        return responses;
    }

}
