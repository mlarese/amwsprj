package it.oiritaly.batch.processors.utils;

/**
 * Created by marco on 13/04/17.
 */
public class AmazonStandardProductIDUtils {

    public enum Type {

        ISBN("ISBN"),
        UPC("UPC"),
        EAN("EAN"),
        ASIN("ASIN"),
        GTIN("GTIN");


        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public static Type getEnum(String value) {
            for(Type v : values())
                if(v.getValue().equalsIgnoreCase(value)) return v;
            throw new IllegalArgumentException();
        }
    }

}
