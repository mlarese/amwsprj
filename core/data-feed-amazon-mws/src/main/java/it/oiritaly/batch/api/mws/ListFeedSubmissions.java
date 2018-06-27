package it.oiritaly.batch.api.mws;

import com.amazonaws.mws.MarketplaceWebService;
import com.amazonaws.mws.MarketplaceWebServiceException;
import com.amazonaws.mws.model.FeedSubmissionInfo;
import com.amazonaws.mws.model.GetFeedSubmissionListRequest;
import com.amazonaws.mws.model.GetFeedSubmissionListResponse;
import com.amazonaws.mws.model.IdList;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ListFeedSubmissions {

    private MwsConfigurationProperties configurationProperties;

    @Autowired
    public ListFeedSubmissions(MwsConfigurationProperties msp) {
        configurationProperties = msp;
    }

    public String init(String serviceUrl){

        MarketplaceWebService service = MwsConfigurator.getMarketplaceWebService(configurationProperties, serviceUrl);

        final String merchantId = configurationProperties.getMerchantId();

        GetFeedSubmissionListRequest request = new GetFeedSubmissionListRequest();
        request.setMerchant( merchantId );

        try {
            GetFeedSubmissionListResponse response = service.getFeedSubmissionList(request);
            return response.toJSON();
        } catch (MarketplaceWebServiceException e) {
            return e.toString();
        }

    }


    public FeedSubmissionInfo getFeedSubmissionInfo(String serviceUrl, String feedId){
        MarketplaceWebService service = MwsConfigurator.getMarketplaceWebService(configurationProperties, serviceUrl);

        final String merchantId = configurationProperties.getMerchantId();

        GetFeedSubmissionListRequest request = new GetFeedSubmissionListRequest();
        IdList list = new IdList();
        List<String> ids = new ArrayList<>();
        ids.add(feedId);

        list.setId(ids);

        request.setFeedSubmissionIdList(list);
        request.setMerchant( merchantId );

        try {
            GetFeedSubmissionListResponse response = service.getFeedSubmissionList(request);
            return response.getGetFeedSubmissionListResult().getFeedSubmissionInfoList().get(0);
        } catch (MarketplaceWebServiceException e) {
            log.error(e.toString());
            return null;
        }
    }




}
