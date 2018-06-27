package it.oiritaly.batch.processors.utils;

/**
 * Created by marco on 13/04/17.
 */
public class AmazonImageTypeUtils {

    public enum Type {

        MAIN("Main", "Image_0"),
        SWATCH("Swatch", ""),
        BKLB("BKLB", ""),
        PT1("PT1", "Image_1"),
        PT2("PT2", "Image_2"),
        PT3("PT3", "Image_3"),
        PT4("PT4", "Image_4"),
        PT5("PT5", "Image_5"),
        PT6("PT6", "Image_6"),
        PT7("PT7", "Image_7"),
        PT8("PT8", "Image_8"),
        SEARCH("Search", ""),
        PM01("PM01", ""),
        MAINOFFERIMAGE("MainOfferImage", ""),
        OFFERIMAGE1("OfferImage1", ""),
        OFFERIMAGE2("OfferImage2", ""),
        OFFERIMAGE3("OfferImage3", ""),
        OFFERIMAGE4("OfferImage4", ""),
        OFFERIMAGE5("OfferImage5", "");


        private String value;
        private String label;

        Type(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public static Type getEnum(String label) {
            for(Type v : values())
                if(v.getLabel().equalsIgnoreCase(label)) return v;
            throw new IllegalArgumentException();
        }
    }

}
