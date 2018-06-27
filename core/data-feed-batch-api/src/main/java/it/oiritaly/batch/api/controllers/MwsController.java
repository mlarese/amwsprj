package it.oiritaly.batch.api.controllers;


import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.IdList;
import com.amazonaws.mws.model.SubmitFeedResponse;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import io.swagger.annotations.ApiParam;
import it.oiritaly.batch.api.mws.GetFeedSubmissionResult;
import it.oiritaly.batch.api.mws.ListFeedSubmissions;
import it.oiritaly.batch.api.mws.ListOrders;
import it.oiritaly.batch.api.mws.SubmitFeedAsync;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.api.mws.model.SubmitFeedRequest;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/mws")
public class MwsController {

    @Autowired
    private ListFeedSubmissions listFeedSubmissions;

    @Autowired
    private ListOrders listOrders;

    @Autowired
    private SubmitFeedAsync submitFeedAsync;

    @Autowired
    private GetFeedSubmissionResult getFeedSubmissionResult;

    @Autowired
    private MwsConfigurationProperties configurationProperties;

    @RequestMapping(value = "/feeds", produces = {"application/json"}, method = RequestMethod.GET)
    public String getFeeds(@RequestParam(value = "serviceUrl", defaultValue = "https://mws-eu.amazonservices.com/") String serviceUrl) {
        return listFeedSubmissions.init(serviceUrl);
    }

    @RequestMapping(value = "/feeds", produces = {"application/json"}, method = RequestMethod.POST)
    public String postFeeds(@RequestParam(value = "serviceUrl", defaultValue = "https://mws-eu.amazonservices.com/") String serviceUrl,
                            @ApiParam(allowableValues = "_POST_PRODUCT_DATA_, _POST_PRODUCT_RELATIONSHIP_DATA_, _POST_INVENTORY_AVAILABILITY_DATA_, _POST_PRODUCT_PRICING_DATA_, _POST_PRODUCT_IMAGE_DATA_", required = true) @RequestParam(value = "feedType", defaultValue = "_POST_PRODUCT_DATA_") String feedType,
                            @RequestParam(value = "marketplaceIds", defaultValue = "A1RKKUPIHCS9HS") String[] marketplaceIds
    ) {
        SubmitFeedRequest.FeedType ft = SubmitFeedRequest.FeedType.valueOf(feedType);

        IdList idList = new IdList();

        List<String> ids = new ArrayList<>();
        for (String id : marketplaceIds) {
            ids.add(id);
        }

        idList.setId(ids);

        List<Future<SubmitFeedResponse>> responses = submitFeedAsync.init(serviceUrl, ft, idList);

        final StringWriter sw = new StringWriter();
        JsonFactory factory = new JsonFactory();
        JsonGenerator generator = null;
        try {
            generator = factory.createGenerator(sw);


            generator.writeStartObject();
            generator.writeArrayFieldStart("responses");

            for (Future<SubmitFeedResponse> future : responses) {
                while (!future.isDone()) {
                    Thread.yield();
                }
                try {
                    SubmitFeedResponse response = future.get();
                    // Original request corresponding to this response, if needed:

                    System.out.println("Response request id: " + response.getResponseMetadata().getRequestId());
                    System.out.println(response.getResponseHeaderMetadata());
                    System.out.println("******");
                    System.out.println(response.toXML());
                    System.out.println("******");
                    System.out.println(response.toJSON());
                    System.out.println("******");
                    System.out.println();
                    generator.writeRaw(response.toJSON());
                } catch (Exception e) {
                    if (e.getCause() instanceof MarketplaceWebServiceException) {
                        MarketplaceWebServiceException exception = MarketplaceWebServiceException.class.cast(e.getCause());
                        System.out.println("Caught Exception: " + exception.getMessage());
                        System.out.println("Response Status Code: " + exception.getStatusCode());
                        System.out.println("Error Code: " + exception.getErrorCode());
                        System.out.println("Error Type: " + exception.getErrorType());
                        System.out.println("Request ID: " + exception.getRequestId());
                        System.out.print("XML: " + exception.getXML());
                        System.out.println("ResponseHeaderMetadata: " + exception.getResponseHeaderMetadata());
                    } else {
                        e.printStackTrace();
                    }
                }
            }

            generator.writeEndArray();
            generator.writeEndObject();

            generator.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "OK";
    }

    @RequestMapping(value = "/orders", produces = {"application/json"}, method = RequestMethod.GET)
    public String getOrders(@RequestParam(value = "serviceUrl", defaultValue = "https://mws-eu.amazonservices.com/") String serviceUrl) {
        return listOrders.init(serviceUrl);
    }

    @RequestMapping(value = "/feed-submission-result", produces = {"application/json"}, method = RequestMethod.GET)
    public String getFeedSubmissionResult(@RequestParam(value = "serviceUrl", defaultValue = "https://mws-eu.amazonservices.com/") String serviceUrl,
                                          @RequestParam(value = "feedSubmissionId", required = true) String feedSubmissionId) {
        return getFeedSubmissionResult.init(serviceUrl, feedSubmissionId);
    }

    @RequestMapping(value = "/data-feed/download", produces = {"application/zip"}, method = RequestMethod.GET)
    public void downloadDataFeedSource(@RequestParam(value = "marketplaceId") String marketplaceId, HttpServletResponse response) throws IOException {
        //setting headers
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"" + marketplaceId + "data-feed.zip\"");

        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        ArrayList<File> files = new ArrayList<>();

        files.add(new File(configurationProperties.getOutputBasePath() + marketplaceId + configurationProperties.getProductDataFilename()));
        files.add(new File(configurationProperties.getOutputBasePath() + marketplaceId + configurationProperties.getProductImageFilename()));
        files.add(new File(configurationProperties.getOutputBasePath() + marketplaceId + configurationProperties.getInventoryAvailabilityDataFilename()));
        files.add(new File(configurationProperties.getOutputBasePath() + marketplaceId + configurationProperties.getProductPricingDataFilename()));

        //packing files
        for (File file : files) {
            //new zip entry and copying inputstream with file to zipOutputStream, after all closing streams
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.close();
    }

    @RequestMapping(value = "/feed-submission-result/{feedSubmissionId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public FileSystemResource downloadFeedSubmissionResult(@PathVariable("feedSubmissionId") String feedSubmissionId) {
        return new FileSystemResource(new File(configurationProperties.getOutputBasePath() + feedSubmissionId + ".xml"));
    }

}
