package it.oiritaly.batch.processors;


import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.processors.utils.*;
import it.oiritaly.data.models.amazon.*;
import it.oiritaly.data.models.jpa.CustomAttribute;
import it.oiritaly.data.models.jpa.DataFeed;
import it.oiritaly.data.models.jpa.Output;
import it.oiritaly.data.models.jpa.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.InvalidAttributeValueException;
import java.lang.Override;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class JpaToXmlAmazonProductProcessor extends JpaToXmlAmazonProcessor {

    private static ObjectFactory factory = new ObjectFactory();

    private static MwsConfigurationProperties configurationProperties;


    @Autowired
    public JpaToXmlAmazonProductProcessor(MwsConfigurationProperties msp) {
        super(msp);
    }

    @Override
    public AmazonEnvelope process(DataFeed input) throws Exception {
        this.setMessageType(AmazonEnvelopeUtils.MessageType.PRODUCT);
        this.setAmazonMessages(toAmazonMessages(input));
        return super.process(input);
    }

    private static List<AmazonEnvelope.Message> toAmazonMessages(DataFeed dataFeed){
        List<AmazonEnvelope.Message> messages = new ArrayList<>();
        for(Product p: dataFeed.getRoot().getProducts()){

            Output out=new Output();
            out.setValue("Amazon");

            boolean isValid;

            try {
                isValid = OiritalyUtils.isValid(p);
            }
            catch (IllegalArgumentException e) {
                log.error("The category id " + p.getCategory().getCode() + " is not valid.");
                continue;
            }

            if(p.getOutput().contains(out) && isValid) {
                try {
                    AmazonEnvelope.Message message = factory.createAmazonEnvelopeMessage();
                    message.setMessageID(messages.size() + 1);
                    message.setProduct(toAmazonProduct(p));
                    messages.add(message);
                } catch (IllegalArgumentException e) {
                    log.error("Envelope non created because product id" + p.getId() + " has invalid attribute:" +e);
                    continue;
                }
            } else {
                log.info("Product "+p.getId()+" is not valid and/or is not set to output in "+out.getValue());
            }
        }

        return messages;
    }

    private static it.oiritaly.data.models.amazon.Product toAmazonProduct(Product p){

        Map<String, CustomAttribute> customAttributes  = p.getCustomAttributes().stream()
                .collect(Collectors.toMap(customAttribute -> customAttribute.getName(), customAttribute -> customAttribute));


        it.oiritaly.data.models.amazon.Product product = factory.createProduct();
        product.setSKU(p.getMpn().toString());
        product.setCondition(toAmazonConditionInfo(p));
        product.setDescriptionData(toAmazonDescriptionData(p, customAttributes));
        if (p.getGtin() != null) {
            product.setStandardProductID(toAmazonStandardProductID(p)); //EAN
        }
        product.setProductData(toAmazonProductData(p, customAttributes));
        return product;
    }

    private static ConditionInfo toAmazonConditionInfo(Product p){
        ConditionInfo conditionInfo = factory.createConditionInfo();
        conditionInfo.setConditionType(AmazonConditionInfoUtils.ConditionType.getEnumFromItalian(p.getCondition().toString()).toString());
        return conditionInfo;
    }

    private static it.oiritaly.data.models.amazon.Product.DescriptionData toAmazonDescriptionData(Product p, Map<String, CustomAttribute> customAttributes){
        it.oiritaly.data.models.amazon.Product.DescriptionData descriptionData = new  it.oiritaly.data.models.amazon.Product.DescriptionData();
        if(StringUtils.isNotEmpty(p.getTitle())){
            descriptionData.setTitle(p.getTitle());
        } else {
            descriptionData.setTitle(p.getMpn());
        }

        StringBuilder description = new StringBuilder(p.getDescription());
        if(customAttributes.get("summary_long")!=null){
            description.append(" "); //adds a space
            description.append(customAttributes.get("summary_long").getValue());
        }
        if(customAttributes.get("features")!=null){
            description.append(" "); //adds a space
            description.append(customAttributes.get("features").getValue());
        }

        descriptionData.setDescription(description.toString());
        descriptionData.setBrand(p.getBrand());
        descriptionData.setManufacturer(p.getBrand());
        descriptionData.setMfrPartNumber(p.getMpn());

        if(customAttributes.get("gender")!=null){
           descriptionData.getTargetAudience().add(StringUtils.capitalize(customAttributes.get("gender").getValue()));
        }

        descriptionData.setItemDimensions(toAmazonItemDimensions(p, customAttributes));

        return descriptionData;
    }

    private static Dimensions toAmazonItemDimensions(Product p, Map<String, CustomAttribute> customAttributes) {
        Dimensions dimensions = factory.createDimensions();
        if (customAttributes.get("weight") != null) {
            WeightDimension wd = new WeightDimension();
            wd.setValue(new BigDecimal(customAttributes.get("weight").getValue()));
            wd.setUnitOfMeasure(WeightUnitOfMeasure.valueOf(customAttributes.get("weight").getUnit().toUpperCase()));
            dimensions.setWeight(wd);
        }
        return dimensions;
    }

    private static StandardProductID toAmazonStandardProductID(Product p) {
        StandardProductID spid = factory.createStandardProductID();
        spid.setType(AmazonStandardProductIDUtils.Type.EAN.toString());
        spid.setValue((p.getGtin()).toString());
        return spid;
    }

    private static it.oiritaly.data.models.amazon.Product.ProductData toAmazonProductData(Product p, Map<String, CustomAttribute> customAttributes){
        it.oiritaly.data.models.amazon.Product.ProductData pdata = new it.oiritaly.data.models.amazon.Product.ProductData();
        pdata.setJewelry(toAmazonJewelry(p, customAttributes));
        return pdata;
    }

    private static Jewelry toAmazonJewelry(Product p, Map<String, CustomAttribute> customAttributes){
        Jewelry jw = factory.createJewelry();
        jw.setStyleName("style");

        Jewelry.ProductType pt = new Jewelry.ProductType();

        //is watch
        if(AmazonCategoryUtils.Category.getEnum(p.getCategory().getCode()).getAmazonClass().equals(Watch.class)) {
            pt.setWatch(setAmazonWatch(p, customAttributes));
        }
        //is fine ring
        else if(AmazonCategoryUtils.Category.getEnum(p.getCategory().getCode()).getAmazonClass().equals(FineRing.class)) {
            pt.setFineRing(setAmazonFineRing(p, customAttributes));
        }
        //is fine neck bracelet
        else if(AmazonCategoryUtils.Category.getEnum(p.getCategory().getCode()).getAmazonClass().equals(FineNecklaceBraceletAnklet.class)) {
            pt.setFineNecklaceBraceletAnklet(setAmazonFineNecklaceBraceletAnklet(p, customAttributes));
        }
        //is fine ear ring
        else if(AmazonCategoryUtils.Category.getEnum(p.getCategory().getCode()).getAmazonClass().equals(FineEarring.class)) {
            pt.setFineEarring(setAmazonFineEarRing(p, customAttributes));
        }
        //is fine other
        else {
            pt.setFineOther(setAmazonFineOther(p, customAttributes));
        }

        jw.setProductType(pt);

        if(customAttributes.get("color")!=null) {
            jw.setColor(customAttributes.get("color").getValue());
        }
        return jw;
    }

    private static FineEarring setAmazonFineEarRing(Product p, Map<String, CustomAttribute> customAttributes) {
        FineEarring fineEarring = factory.createFineEarring();
        StoneType stone = factory.createStoneType();

        if(p.getIsGroupParent() !=  null) {
            FineEarring.VariationData vd = fineEarring.getVariationData() != null ? fineEarring.getVariationData() : new FineEarring.VariationData();
            if (!p.getIsGroupParent()) {
                vd.setParentage("child");
            } else {
                vd.setParentage("parent");
            }
            fineEarring.setVariationData(vd);
        }

        try{
            setJewelAttributes(stone, customAttributes);

            if (OiritalyUtils.isValidCustomAttribute("material", customAttributes)) {
                fineEarring.getMaterial().add(customAttributes.get("material").getValue());
                if(fineEarring.getVariationData() != null){
                    fineEarring.getVariationData().setMetalType(customAttributes.get("material").getValue());
                }
            }

        } catch (InvalidAttributeValueException iave){
            log.warn("invalid attribute for item id"+p.getId()+" : "+iave);
        } catch(NumberFormatException nfe){
            log.warn("number format exception for item id"+p.getId()+" : "+nfe);
        }

        fineEarring.getStone().add(stone);
        return fineEarring;
    }


    private static FineRing setAmazonFineRing(Product p,  Map<String, CustomAttribute> customAttributes) {
        FineRing fineRing = factory.createFineRing();

        StoneType stone = factory.createStoneType();
        if(p.getIsGroupParent() !=  null) {
            FineRing.VariationData vd = fineRing.getVariationData() != null ? fineRing.getVariationData() : new FineRing.VariationData();
            if (p.getIsGroupParent()) {
                vd.setParentage("parent");
                vd.setVariationTheme("RingSize");
            } else {
                vd.setParentage("child");

            }
            fineRing.setVariationData(vd);
        }

        try{
            setJewelAttributes(stone, customAttributes);

            if (OiritalyUtils.isValidCustomAttribute("material", customAttributes)) {
                fineRing.getMaterial().add(customAttributes.get("material").getValue());
                if(fineRing.getVariationData() != null){
                    fineRing.getVariationData().setMetalType(customAttributes.get("material").getValue());
                }
            }

            if(OiritalyUtils.isValidCustomAttribute("size", customAttributes)){
                if(fineRing.getVariationData() != null) {
                    fineRing.getVariationData().setRingSize(AmazonRingSizeConverterUtils.RingSize.getConvertedvalue(customAttributes.get("size").getValue(), p.getContentLanguage()).toString());
                }
            }

        } catch (InvalidAttributeValueException iave){
            log.warn("invalid attribute for item id"+p.getId()+" : "+iave);
        } catch(NumberFormatException nfe){
            log.warn("number format exception for item id"+p.getId()+" : "+nfe);
        }

        fineRing.getStone().add(stone);
        return fineRing;
    }

    private static Watch setAmazonWatch(Product p, Map<String, CustomAttribute> customAttributes){
        Watch watch = factory.createWatch();

        try {
            OiritalyUtils.setCustomAttributefromValue("movement", watch::setMovementType, customAttributes);

            OiritalyUtils.setCustomAttributefromValue("mat_case", watch.getCaseMaterial()::add, customAttributes);

            OiritalyUtils.setCustomAttributefromValue("functions", watch.getSpecialFeatures()::add, customAttributes);

            OiritalyUtils.setCustomAttributefromValue("color_band", watch::setBandColor, customAttributes);

            OiritalyUtils.setCustomAttributefromValue("mat_band", watch::setBandMaterial, customAttributes);

            OiritalyUtils.setCustomAttributefromValue("display", watch::setDisplayType, customAttributes);

            OiritalyUtils.setCustomAttributefromValue("color_qua", watch::setDialColor, customAttributes);

            OiritalyUtils.setCustomAttributefromValue("collection", watch::setCollectionName, customAttributes);

            OiritalyUtils.setCustomAttributefromLengthDimension("waterResistantDepth", watch::setWaterResistantDepth, customAttributes);

            if (OiritalyUtils.isValidCustomAttribute("gender", customAttributes)) {
                watch.setTargetGender(OiritalyUtils.Gender.getEnum(customAttributes.get("gender").getValue(), p.getContentLanguage()).toString());
            }

            if (OiritalyUtils.isValidCustomAttribute("color_case", customAttributes)) {
                watch.getSpecialFeatures().add(OiritalyUtils.CaseColor.valueOf(p.getContentLanguage().toString().toUpperCase()) + ": " + customAttributes.get("color_case").getValue());
            }

            if (OiritalyUtils.isValidCustomAttribute("seal", customAttributes)) {
                String value = customAttributes.get("seal").getValue();
                if (Character.isDigit(value.charAt(0)) && Character.isDigit(value.charAt(0))) {
                    {
                        value = value.substring(0, 2);
                        PressureUnitOfMeasure unit = PressureUnitOfMeasure.valueOf("BARS");
                        PressureDimension dimension = factory.createPressureDimension();
                        dimension.setValue(new BigDecimal(value));
                        dimension.setUnitOfMeasure(unit);
                        watch.setMaximumWaterPressure(dimension);
                    }
                }
            } //endif


        } catch (InvalidAttributeValueException iave){
            log.warn("invalid attribute for item id"+p.getId()+" : "+iave);
        } catch(NumberFormatException nfe){
            log.warn("number format exception for item id"+p.getId()+" : "+nfe);
        }

        if(customAttributes.get("diam_case")!=null) {
            try {
                OiritalyUtils.setCustomAttributefromLengthDimension("diam_case", watch::setCaseSizeDiameter, customAttributes);
                LengthDimension dimension = factory.createLengthDimension();
                dimension.setValue(new BigDecimal(customAttributes.get("diam_case").getValue()));
                dimension.setUnitOfMeasure(LengthUnitOfMeasure.valueOf(customAttributes.get("diam_case").getUnit().toUpperCase()));
                watch.setCaseSizeDiameter(dimension);
            } catch (InvalidAttributeValueException iave){
                log.warn("invalid attribute for item id"+p.getId()+" : "+iave);
            } catch (NumberFormatException nfe) {
                    p.setDescription(p.getDescription() + OiritalyUtils.CaseSize.valueOf(p.getContentLanguage().toString().toUpperCase()) + customAttributes.get("diam_case").getValue());

            }
        }
        return watch;
    }

    private static FineNecklaceBraceletAnklet setAmazonFineNecklaceBraceletAnklet(Product p, Map<String, CustomAttribute> customAttributes) {
        FineNecklaceBraceletAnklet bracelet = factory.createFineNecklaceBraceletAnklet();
        StoneType stone = factory.createStoneType();

        if(p.getIsGroupParent() !=  null) {
            FineNecklaceBraceletAnklet.VariationData vd = bracelet.getVariationData() != null ? bracelet.getVariationData() : new FineNecklaceBraceletAnklet.VariationData();
            if (p.getIsGroupParent()) {
                vd.setParentage("parent");
            } else {
                vd.setParentage("child");
            }
            bracelet.setVariationData(vd);
        } /*else {
            FineNecklaceBraceletAnklet.VariationData vd = bracelet.getVariationData() != null ? bracelet.getVariationData() : new FineNecklaceBraceletAnklet.VariationData();
            vd.setParentage("child");
            bracelet.setVariationData(vd);
        }*/

        try{
            setJewelAttributes(stone, customAttributes);

            if (OiritalyUtils.isValidCustomAttribute("material", customAttributes)) {
                bracelet.getMaterial().add(customAttributes.get("material").getValue());
                if(bracelet.getVariationData() != null){
                    bracelet.getVariationData().setMetalType(customAttributes.get("material").getValue());
                }
            }

        } catch (InvalidAttributeValueException iave){
            log.warn("invalid attribute for item id"+p.getId()+" : "+iave);
        } catch(NumberFormatException nfe){
            log.warn("number format exception for item id"+p.getId()+" : "+nfe);
        }

        bracelet.getStone().add(stone);
        return bracelet;
    }

    private static FineOther setAmazonFineOther(Product p, Map<String, CustomAttribute> customAttributes) {
        FineOther other = factory.createFineOther();

        if(p.getIsGroupParent() !=  null) {
            FineOther.VariationData vd = other.getVariationData() != null ? other.getVariationData() : new FineOther.VariationData();
            if (p.getIsGroupParent()) {
                vd.setParentage("parent");
            } else {
                vd.setParentage("parent");
            }
            other.setVariationData(vd);
        }

        try{

            if (OiritalyUtils.isValidCustomAttribute("material", customAttributes)) {
                other.getMaterial().add(customAttributes.get("material").getValue());
                if(other.getVariationData() != null){
                    other.getVariationData().setMetalType(customAttributes.get("material").getValue());
                }
            }

        } catch (InvalidAttributeValueException iave){
            log.warn("invalid attribute for item id"+p.getId()+" : "+iave);
        } catch(NumberFormatException nfe){
            log.warn("number format exception for item id"+p.getId()+" : "+nfe);
        }

        return other;
    }

    private static void setJewelAttributes (StoneType stone, Map<String, CustomAttribute> customAttributes) throws InvalidAttributeValueException, NumberFormatException {

        OiritalyUtils.setCustomAttributefromValue("color_dia", stone::setStoneColor, customAttributes);

        OiritalyUtils.setCustomAttributefromValue("purity", stone::setStoneClarity, customAttributes);

        OiritalyUtils.setCustomAttributefromValue("stone", stone::setGemType, customAttributes);

        OiritalyUtils.setCustomAttributefromJewelryWeightDimension("carat_stone", stone::setStoneWeight, customAttributes);

    }

}