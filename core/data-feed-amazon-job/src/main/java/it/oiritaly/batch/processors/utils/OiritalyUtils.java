package it.oiritaly.batch.processors.utils;

import it.oiritaly.data.models.amazon.*;
import it.oiritaly.data.models.jpa.CustomAttribute;
import it.oiritaly.data.models.jpa.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.management.InvalidAttributeValueException;
import java.lang.Override;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class OiritalyUtils {

    private static ObjectFactory factory = new ObjectFactory();

    public enum Gender {

        MALE("male"),
        FEMALE("female"),
        UNISEX("unisex");

        private String value;

        Gender(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }


        public static Gender getEnum(String value, String contentLanguage) {
            switch (contentLanguage){
                case "ES":
                    return getEnumFromSpanish(value);
                case "IT":
                    return getEnumFromItalian(value);
                case "EN":
                    return getEnumFromEnglish(value);
                default:
                    for(Gender v : values())
                        if(v.getValue().equalsIgnoreCase(value)) return v;

            }
            throw new IllegalArgumentException();
        }


        private static Gender getEnumFromSpanish(String spanish){
            switch (spanish.toLowerCase()){
                case "hombre":
                    return MALE;
                case "mujer":
                    return FEMALE;
                case "unisexo":
                    return UNISEX;
                case "niño":
                    return MALE;
                case "niña":
                    return FEMALE;
                default:
                    throw new IllegalArgumentException(spanish);
            }
        }


        private static Gender getEnumFromItalian(String italian){
            switch (italian.toLowerCase()){
                case "uomo":
                    return MALE;
                case "donna":
                    return FEMALE;
                case "unisex":
                    return UNISEX;
                case "bambino":
                    return MALE;
                case "bambina":
                    return FEMALE;
                default:
                    throw new IllegalArgumentException(italian);
            }
        }

        private static Gender getEnumFromEnglish(String english){
            switch (english.toLowerCase()){
                case "man":
                    return MALE;
                case "woman":
                    return FEMALE;
                case "unisex":
                    return UNISEX;
                case "children":
                    return UNISEX;
                default:
                    throw new IllegalArgumentException(english);
            }
        }

    }

    public enum CaseSize {
        IT("Larghezza della cassa (mm)"),
        ES("Dimensiones caja (mm)"),
        FR("Largeur boîtier (mm)"),
        DE("Gehäusebreite (mm)"),
        EN("Case size (mm)"),
        DEFAULT("Case size (mm)");



        private String value;

        CaseSize(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public static CaseSize getEnum(String value) {
            for(CaseSize v : values())
                if(v.getValue().equalsIgnoreCase(value)) return v;
            throw new IllegalArgumentException();
        }


    }

    public enum CaseColor {
        IT("Colore cassa"),
        ES("Color caja"),
        FR("Couleur boîtier"),
        DE("Gehäusefarbe"),
        EN("Case color"),
        DEFAULT("Case color");



        private String value;

        CaseColor(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return this.getValue();
        }

        public static CaseColor getEnum(String value) {
            for(CaseColor v : values())
                if(v.getValue().equalsIgnoreCase(value)) return v;
            throw new IllegalArgumentException();
        }


    }

    public static boolean isValid(Product p) throws IllegalArgumentException{
        boolean isValid = false;

        //TODO Per il momento parsiamo solo Watch e fineRing
        isValid =
            AmazonCategoryUtils.Category.getEnum(p.getCategory().getCode()).getAmazonClass().equals(Watch.class) ||
            AmazonCategoryUtils.Category.getEnum(p.getCategory().getCode()).getAmazonClass().equals(FineRing.class) ||
            AmazonCategoryUtils.Category.getEnum(p.getCategory().getCode()).getAmazonClass().equals(FineNecklaceBraceletAnklet.class) ||
            AmazonCategoryUtils.Category.getEnum(p.getCategory().getCode()).getAmazonClass().equals(FineEarring.class)
        ;
        return isValid;
    }



    public static OverrideCurrencyAmount OIRCurrencyConverter(Product p, String contentLanguage){
        String currency;
        double exchangeRate;

        switch (contentLanguage.toLowerCase()){
            case "en":
                currency = "GBP";
                exchangeRate = 0.9077;
                break;
            default:
                currency = "EUR";
                exchangeRate = 1;
                break;
        }


        double exchangedPrice = p.getSalePrice().getValue().doubleValue() * exchangeRate;
        exchangedPrice = RoundTo2Decimals(exchangedPrice);


        OverrideCurrencyAmount oca = factory.createOverrideCurrencyAmount();
        oca.setCurrency(BaseCurrencyCodeWithDefault.valueOf(currency));
        oca.setValue(exchangedPrice);
        return oca;
    }


    public static boolean isValidCustomAttribute(String name, Map<String, CustomAttribute> customAttributes) throws InvalidAttributeValueException{
        if (customAttributes.containsKey(name)){
            if(StringUtils.isNotBlank(customAttributes.get(name).getValue())){
                return true;
            } else {
                throw new InvalidAttributeValueException("Custom attribute "+name+" is empty");
            }
        } else return false;
    }

    public static void setCustomAttributefromValue(String name, Consumer<String> fn, Map<String, CustomAttribute> customAttributes) throws InvalidAttributeValueException {
        if(isValidCustomAttribute(name, customAttributes)){
            String value = customAttributes.get(name).getValue();
            fn.accept(value);
        }
    }

    public static void setCustomAttributefromLengthDimension(String name, Consumer<LengthDimension> fn, Map<String, CustomAttribute> customAttributes) throws NumberFormatException,InvalidAttributeValueException {
        if(isValidCustomAttribute(name, customAttributes)){
            String value = customAttributes.get(name).getValue();
            LengthUnitOfMeasure unit = LengthUnitOfMeasure.valueOf(customAttributes.get(name).getUnit().toUpperCase());
            try {
                LengthDimension dimension = factory.createLengthDimension();
                dimension.setValue(new BigDecimal(value).setScale(2, RoundingMode.CEILING));
                dimension.setUnitOfMeasure(unit);
                fn.accept(dimension);
            } catch (NumberFormatException nfe) {
                throw new NumberFormatException("Number format exception for dimension: value = "+value+" and unit = "+unit);
            }
        }
    }

    public static void setCustomAttributefromJewelryWeightDimension(String name, Consumer<JewelryWeightDimension> fn, Map<String, CustomAttribute> customAttributes) throws NumberFormatException,InvalidAttributeValueException {
        if(isValidCustomAttribute(name, customAttributes)){
            String value = customAttributes.get(name).getValue();
            JewelryWeightUnitOfMeasure unit = JewelryWeightUnitOfMeasure.valueOf(customAttributes.get(name).getUnit().toUpperCase());
            try {
                JewelryWeightDimension dimension = factory.createJewelryWeightDimension();
                dimension.setValue(new BigDecimal(value));
                dimension.setUnitOfMeasure(unit);
                fn.accept(dimension);
            } catch (NumberFormatException nfe) {
                throw new NumberFormatException("Number format exception for dimension: value = "+value+" and unit = "+unit);
            }
        }
    }



    private static double RoundTo2Decimals(double val) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df2 = new DecimalFormat("#.##", otherSymbols);
        df2.setRoundingMode(RoundingMode.CEILING);
        df2.setGroupingUsed(false);
        return Double.valueOf(df2.format(val));
    }




}
