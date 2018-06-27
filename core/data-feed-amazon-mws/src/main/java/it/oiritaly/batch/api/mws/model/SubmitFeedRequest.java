package it.oiritaly.batch.api.mws.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by marco on 21/02/17.
 */
public class SubmitFeedRequest extends com.amazonaws.mws.model.SubmitFeedRequest {

    public enum FeedType {

        _POST_PRODUCT_DATA_("_POST_PRODUCT_DATA_"),
        _POST_INVENTORY_AVAILABILITY_DATA_("_POST_INVENTORY_AVAILABILITY_DATA_"),
        _POST_PRODUCT_PRICING_DATA_("_POST_PRODUCT_PRICING_DATA_"),
        _POST_PRODUCT_IMAGE_DATA_("_POST_PRODUCT_IMAGE_DATA_"),
        _POST_PRODUCT_RELATIONSHIP_DATA_("_POST_PRODUCT_RELATIONSHIP_DATA_")
        ;

        private String value;

        FeedType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public static FeedType getEnum(String value) {
            for (FeedType v : values())
                if (v.getValue().equalsIgnoreCase(value)) return v;
            throw new IllegalArgumentException();
        }
    }


    public String computeContentMD5HeaderValue( FileInputStream fis )
            throws NoSuchAlgorithmException, IOException {

        DigestInputStream dis = new DigestInputStream(fis, MessageDigest.getInstance("MD5"));

        fis.getChannel().position(0);
        fis.close();

        return computeMDValue(dis);
    }

    public String computeContentMD5HeaderValue( InputStream is )
            throws NoSuchAlgorithmException, IOException {

        DigestInputStream dis = new DigestInputStream(is, MessageDigest.getInstance("MD5"));

        return computeMDValue(dis);
    }



    private String computeMDValue(DigestInputStream dis) throws IOException{
        byte[] buffer = new byte[8192];
        while (dis.read(buffer) > 0) {
        }

        String md5Content = new String(org.apache.commons.codec.binary.Base64.encodeBase64(dis.getMessageDigest().digest()));

        dis.close();
        return md5Content;
    }
}
