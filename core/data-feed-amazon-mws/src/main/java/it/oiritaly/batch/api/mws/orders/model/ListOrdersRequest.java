package it.oiritaly.batch.api.mws.orders.model;

import com.amazonservices.mws.client.MwsWriter;
import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by marco on 07/02/17.
 */
@Slf4j
public class ListOrdersRequest extends com.amazonservices.mws.orders._2013_09_01.model.ListOrdersRequest {

    private MwsConfigurationProperties configurationProperties;

    @Autowired
    public ListOrdersRequest(MwsConfigurationProperties msp) {
        super();
        configurationProperties = msp;
    }

    /**
     * Write members to a MwsWriter adding AWSAccessKeyId
     *
     * @param w
     *      The writer to write to.
     */
    @Override
    public void writeFragmentTo(MwsWriter w) {
        super.writeFragmentTo(w);
        w.write("AWSAccessKeyId", configurationProperties.getAccessKeyId());
    }


}
