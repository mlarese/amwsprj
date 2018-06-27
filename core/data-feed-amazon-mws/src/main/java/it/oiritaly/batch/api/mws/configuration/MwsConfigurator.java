/*******************************************************************************
 * Copyright 2009-2015 Amazon Services. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 *
 * You may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations under the License.
 *******************************************************************************
 * Marketplace Web Service Orders
 * API Version: 2013-09-01
 * Library Version: 2015-09-24
 * Generated: Fri Sep 25 20:06:20 GMT 2015
 */
package it.oiritaly.batch.api.mws.configuration;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceClient;
import com.amazonaws.mws.MarketplaceWebServiceConfig;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersAsyncClient;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersClient;
import com.amazonservices.mws.orders._2013_09_01.MarketplaceWebServiceOrdersConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for MarketplaceWebServiceOrders samples.
 */
@Slf4j
public class MwsConfigurator {

    /** The client, lazy initialized. Async client is also a sync client. */
    private static MarketplaceWebServiceOrdersAsyncClient clientOrders = null;

    private static MarketplaceWebServiceClient clientFeeds = null;



    ////////////
    // ORDERS
    ///////////

    /**
     * Get a client connection used for orders ready to use.
     *
     * @return A ready to use client connection.
     */
    public static MarketplaceWebServiceOrdersClient getClient(MwsConfigurationProperties configurationProperties, String serviceURL) {
        return getAsyncClient(configurationProperties,serviceURL);
    }

    /**
     * Get an async client connection ready to use.
     *
     * @return A ready to use client connection.
     */
    public static synchronized MarketplaceWebServiceOrdersAsyncClient getAsyncClient(MwsConfigurationProperties configurationProperties, String serviceURL) {
        if (clientOrders==null) {
            MarketplaceWebServiceOrdersConfig config = new MarketplaceWebServiceOrdersConfig();
            config.setServiceURL(serviceURL);
            // Set other client connection configurations here.
            clientOrders = new MarketplaceWebServiceOrdersAsyncClient(configurationProperties.getAccessKeyId(), configurationProperties.getSecretAccessKey(),
                    configurationProperties.getAppName(), configurationProperties.getAppVersion(), config, null);
        }
        return clientOrders;
    }




    ////////////
    // FEEDS
    ///////////

    /**
     *
     * Get the marketplace web service connection used for feeds ready to use
     *
     * @param configurationProperties
     * @param serviceURL
     * @return A ready to use MarketplaceWebServiceClient connection
     */
    public static MarketplaceWebServiceClient getMarketplaceWebService(MwsConfigurationProperties configurationProperties, String serviceURL){
        return getFeedsClient(configurationProperties,serviceURL);
    }


    /**
     * Get an async client connection ready to use.
     *
     * @return A ready to use client connection.
     */
    public static synchronized MarketplaceWebServiceClient getFeedsClient(MwsConfigurationProperties configurationProperties, String serviceURL) {
        if (clientFeeds==null) {
            MarketplaceWebServiceConfig config = new MarketplaceWebServiceConfig();
            config.setServiceURL(serviceURL);

            clientFeeds = new MarketplaceWebServiceClient(
                    configurationProperties.getAccessKeyId(), configurationProperties.getSecretAccessKey(), configurationProperties.getAppName(), configurationProperties.getAppVersion(), config);
        }
        return clientFeeds;
    }


}
