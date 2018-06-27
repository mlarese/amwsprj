package it.oiritaly.batch.processors.utils;

/**
 * Created by marco on 13/04/17.
 */
public class AmazonConditionInfoUtils {

    public enum ConditionType {

        NEW("New"),
        USEDLIKENEW("UsedLikeNew"),
        USEDVERYGOOD("UsedVeryGood"),
        USEDGOOD("UsedGood"),
        USEDACCEPTABLE("UsedAcceptable"),
        COLLECTIBLELIKENEW("CollectibleLikeNew"),
        COLLECTIBLEVERYGOOD("CollectibleVeryGood"),
        COLLECTIBLEACCEPTABLE("CollectibleAcceptable"),
        REFURBISHED("Refurbished"),
        CLUB("Club");

        private String value;

        ConditionType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public static ConditionType getEnum(String value) {
            for(ConditionType v : values())
                if(v.getValue().equalsIgnoreCase(value)) return v;
            throw new IllegalArgumentException();
        }


        public static ConditionType getEnumFromItalian(String italian){
            switch (italian.toLowerCase()){
                case "nuovo":
                    return NEW;
                case "usato":
                    return USEDLIKENEW;
                default:
                    return NEW;
            }
        }

    }

}
