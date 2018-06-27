package it.oiritaly.batch.processors.utils;

import java.util.Arrays;

public class AmazonRingSizeConverterUtils {

    public enum RingSize {
        _4("4.0", "14", "44", "3", "F"),
        _5("5.0", "14.5", "45", "3.5", "G"),
        _5_5("5.5", "14.5", "46", "3.5", "G"),
        _6("6.0", "15", "46", "4", "H"),
        _6_5("6.5", "15", "47", "4", "H 1/2"),
        _7("7.0", "15.75", "47", "4.5", "I"),
        _8("8.0", "15.75", "48", "4.5", "I 1/2"),
        _9("9.0", "15.75", "49", "5.5", "J"),
        _10("10.0", "16.5", "50", "5.5", "K"),
        _11("11.0", "16.5", "51", "6", "L 1/2"),
        _12("12.0", "17.25", "52", "6.5", "N"),
        _13("13.0", "17.25", "53", "6.5", "N"),
        _14("14.0", "17.25", "54", "7", "O"),
        _15("15.0", "17.5", "57", "7.75", "P"),
        _16("16.0", "18", "57", "7.75", "P"),
        _17("17.0", "18", "57", "8", "Q"),
        _18("18.0", "18.5", "58", "8.5", "R"),
        _19("19.0", "19", "59", "9", "S"),
        _20("20.0", "19", "59", "9", "S"),
        _21("21.0", "19.75", "62", "10", "T"),
        _22("22.0", "20.5", "62", "10", "U"),
        _23("23.0", "20.5", "64", "10.5", "U 1/2"),
        _24("24.0", "20.75", "65", "11", "V 1/2"),
        _25("25.0", "21.5", "65", "12", "W"),
        _26("26.0", "21.5", "66", "12", "X"),
        _27("27.0", "21.5", "66.5", "12", "Y"),
        _28("28.0", "21.75", "67.75", "12.5", "Z");


        private String[] sizes;

        RingSize(String... sizes) {
            this.sizes = sizes;
        }

        public String[] getSizes() {
            return sizes;
        }


        @Override
        public String toString() {
            return Arrays.toString(this.getSizes());
        }

        public static RingSize getEnum(String italianSize) {
            for(RingSize v : values())
                if(Double.valueOf(v.getSizes()[0]).equals(Double.valueOf(italianSize))) return v;
            throw new IllegalArgumentException(italianSize);
        }

        public static String getConvertedvalue(String italianSize, String contentLanguage){
            RingSize rs = getEnum(italianSize);

            switch (contentLanguage.toLowerCase()){
                case "it":
                    return rs.getSizes()[0];
                case "de":
                    return rs.getSizes()[1];
                case "fr":
                    return rs.getSizes()[2];
                case "en_us":
                    return rs.getSizes()[3];
                case "en":
                    return rs.getSizes()[4];
                default:
                    throw new IllegalArgumentException(contentLanguage);
            }


        }


    }


}
