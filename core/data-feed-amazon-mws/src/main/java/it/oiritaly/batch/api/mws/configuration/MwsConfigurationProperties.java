package it.oiritaly.batch.api.mws.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by marco on 02/02/17.
 */

@ConfigurationProperties(prefix = "com.amazon.mws")
@Data
@Component
public class MwsConfigurationProperties {

    private String accessKeyId;

    private String secretAccessKey;

    private String appName;

    private String appVersion;

    private String merchantId;

    private String mwsEndpoint;

    private String marketplaceDeId;

    private String marketplaceEsId;

    private String marketplaceFrId;

    private String marketplaceUkId;

    private String marketplaceItId;

    private String outputBasePath;

    private String productDataFilename;

    private String relationshipDataFilename;

    private String inventoryAvailabilityDataFilename;

    private String productPricingDataFilename;

    private String productImageFilename;

    private String defaultTimeZone;
}
