package it.oiritaly.batch.processors;


import it.oiritaly.data.models.jpa.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class XmlToJpaProcessor implements ItemProcessor<it.oiritaly.data.models.xml.DataFeed, DataFeed> {

    @Override
    public DataFeed process(it.oiritaly.data.models.xml.DataFeed input) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("Processing: " + input);
        }
        DataFeed output = new DataFeed();
        output.setRoot(input.getRoot() == null ? null :
                toJpa(input.getRoot()));
        //output.setParseDate(LocalDateTime.now());
        output.setParseDateDateTime(new Date());
        if (log.isDebugEnabled()) {
            log.debug("Processed: " + output);
        }
        return output;
    }

    private static Root toJpa(it.oiritaly.data.models.xml.Root root) {
        Root result = new Root();
        result.setId(root.getId());
        result.setTitle(root.getTitle());
        result.setSubtitle(root.getSubtitle());
        result.setRights(root.getRights());
        result.setUpdated(root.getUpdated());
        result.setAuthor(root.getAuthor() == null ? null :
                toJpa(root.getAuthor())
        );
        log.info("******* TOTAL PRODUCTS ARE "+root.getProducts().size());

        result.setProducts(root.getProducts() == null ? null :
                root.getProducts().stream().map(XmlToJpaProcessor::toJpa).collect(Collectors.toList())
        );
        return result;
    }

    private static Product toJpa(it.oiritaly.data.models.xml.Product product) {
        Product result = new Product();
        result.setId(product.getId());
        result.setTitle(product.getTitle());
        result.setDescription(product.getDescription());
        result.setLink(product.getLink());
        result.setContentLanguage(product.getContent_language());
        result.setTargetCountry(product.getTarget_country());
        result.setAgeGroup(product.getAge_group());
        result.setAvailability(product.getAvailability());
        result.setBrand(product.getBrand());
        result.setCondition(product.getCondition());
        result.setGtin(product.getGtin());
        result.setMpn(product.getMpn());
        result.setQuantity(product.getQuantity());
        result.setDelete(product.getDelete());
        result.setIsGroupParent(product.getIs_group_parent());
        result.setItemGroupId(product.getItem_group_id());
        result.setCategory(product.getCategory() == null ? null :
                toJpa(product.getCategory()));
        result.setPrice(product.getPrice() == null ? null :
                toJpa(product.getPrice()));
        result.setSalePrice(product.getSalePrice() == null ? null :
                toJpa(product.getSalePrice()));
        result.setImageLinks(product.getImageLinks() == null ? null :
                product.getImageLinks().stream().map(XmlToJpaProcessor::toJpa).collect(Collectors.toList())
        );
        result.setCustomAttributes(product.getCustomAttributes() == null ? null :
                product.getCustomAttributes().stream().map(XmlToJpaProcessor::toJpa).collect(Collectors.toList())
        );
        result.setOutput(product.getOutput() == null ? null :
                product.getOutput().stream().map(XmlToJpaProcessor::toJpa).collect(Collectors.toList())
        );
        return result;
    }

    private static Category toJpa(it.oiritaly.data.models.xml.Category category) {
        Category result = new Category();
        result.setCode(category.getCode());
        result.setName(category.getName());
        return result;
    }

    private static Price toJpa(it.oiritaly.data.models.xml.Price price) {
        Price result = new Price();
        result.setCurrency(price.getCurrency());
        result.setValue(price.getValue());
        return result;
    }

    private static ImageLink toJpa(it.oiritaly.data.models.xml.ImageLink imageLink) {
        ImageLink result = new ImageLink();
        result.setLink(imageLink.getLink());
        result.setLabel(imageLink.getLabel());
        return result;
    }

    private static CustomAttribute toJpa(it.oiritaly.data.models.xml.CustomAttribute customAttribute) {
        CustomAttribute result = new CustomAttribute();
        result.setName(customAttribute.getName());
        result.setValue(customAttribute.getValue());
        result.setUnit(customAttribute.getUnit());
        return result;
    }

    private static Output toJpa(String s) {
        Output result = new Output();
        result.setValue(s);
        return result;
    }

    private static Author toJpa(it.oiritaly.data.models.xml.Author author) {
        Author result = new Author();
        result.setEmail(author.getEmail());
        result.setName(author.getName());
        result.setUri(author.getUri());
        return result;
    }
}
