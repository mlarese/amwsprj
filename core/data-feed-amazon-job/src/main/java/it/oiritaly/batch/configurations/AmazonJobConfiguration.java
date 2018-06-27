package it.oiritaly.batch.configurations;

import it.oiritaly.batch.api.mws.configuration.MwsConfigurationProperties;
import it.oiritaly.batch.api.mws.model.SubmitFeedRequest;
import it.oiritaly.batch.processors.*;
import it.oiritaly.batch.readers.DataFeedItemReader;
import it.oiritaly.data.models.jpa.DataFeed;
import it.oiritaly.data.repositories.jpa.DataFeedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.net.MalformedURLException;

@Slf4j
@Configuration
@Import(InfrastructureConfiguration.class)
public class AmazonJobConfiguration {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;
    private final DataFeedRepository dataFeedRepository;
    private final String OVERRIDDEN_BY_EXPRESSION = null;

    @Autowired
    private MwsConfigurationProperties configurationProperties;

    @Autowired
    public AmazonJobConfiguration(JobBuilderFactory jobs, StepBuilderFactory steps, DataFeedRepository dataFeedRepository) {
        this.jobs = jobs;
        this.steps = steps;
        this.dataFeedRepository = dataFeedRepository;
    }

    @Bean
    public ItemProcessor<it.oiritaly.data.models.jpa.DataFeed, it.oiritaly.data.models.amazon.AmazonEnvelope> jpaToXmlProductProcessor() {
        return new JpaToXmlAmazonProductProcessor(configurationProperties);
    }

    @Bean
    public ItemProcessor<it.oiritaly.data.models.jpa.DataFeed, it.oiritaly.data.models.amazon.AmazonEnvelope> jpaToXmlRelationshipProcessor() {
        return new JpaToXmlAmazonRelationshipProcessor(configurationProperties);
    }

    @Bean
    public ItemProcessor<it.oiritaly.data.models.jpa.DataFeed, it.oiritaly.data.models.amazon.AmazonEnvelope> jpaToXmlInventoryProcessor() {
        return new JpaToXmlAmazonInventoryProcessor(configurationProperties);
    }

    @Bean
    public ItemProcessor<it.oiritaly.data.models.jpa.DataFeed, it.oiritaly.data.models.amazon.AmazonEnvelope> jpaToXmlPriceProcessor() {
        return new JpaToXmlAmazonPriceProcessor(configurationProperties);
    }

    @Bean
    public ItemProcessor<it.oiritaly.data.models.jpa.DataFeed, it.oiritaly.data.models.amazon.AmazonEnvelope> JpaToXmlAmazonProductImageProcessor() {
        return new JpaToXmlAmazonProductImageProcessor(configurationProperties);
    }

    @Bean
    @StepScope
    public SubmitFeedTasklet postProductDataTasklet(@Value("#{jobParameters[marketplaceId]}") String marketplaceId) {
        return new SubmitFeedTasklet(marketplaceId, SubmitFeedRequest.FeedType._POST_PRODUCT_DATA_);
    }

    @Bean
    @StepScope
    public SubmitFeedTasklet postRelationshipDataTasklet(@Value("#{jobParameters[marketplaceId]}") String marketplaceId) {
        return new SubmitFeedTasklet(marketplaceId, SubmitFeedRequest.FeedType._POST_PRODUCT_RELATIONSHIP_DATA_);
    }

    @Bean
    @StepScope
    public SubmitFeedTasklet postInventoryAvailabilityDataTasklet(@Value("#{jobParameters[marketplaceId]}") String marketplaceId) {
        return new SubmitFeedTasklet(marketplaceId, SubmitFeedRequest.FeedType._POST_INVENTORY_AVAILABILITY_DATA_);
    }

    @Bean
    @StepScope
    public SubmitFeedTasklet postProductImageDataTasklet(@Value("#{jobParameters[marketplaceId]}") String marketplaceId) {
        return new SubmitFeedTasklet(marketplaceId, SubmitFeedRequest.FeedType._POST_PRODUCT_IMAGE_DATA_);
    }

    @Bean
    @StepScope
    public SubmitFeedTasklet postProductPricingDataTasklet(@Value("#{jobParameters[marketplaceId]}") String marketplaceId) {
        return new SubmitFeedTasklet(marketplaceId, SubmitFeedRequest.FeedType._POST_PRODUCT_PRICING_DATA_);
    }

    @Bean
    public Job xmlImporterJob(Step importStep, Step exportProductStep,  Step exportRelationshipStep, Step exportInventoryStep, Step exportPriceStep, Step exportProductImageStep) {
        return this.jobs
                .get("amazon")
                .start(importStep)
                .next(exportProductStep)
                .next(exportRelationshipStep)
                .next(exportInventoryStep)
                .next(exportPriceStep)
                .next(exportProductImageStep)
                .next(postProductDataStep())
                .next(postRelationshipDataStep())
                .next(postProductImageDataStep())
                .next(postProductPricingDataStep())
                .next(postInventoryAvailabilityDataStep())
                .build();
    }

    @Bean
    public Step importStep() {
        return this.steps
                .get("importStep")
                .<it.oiritaly.data.models.xml.DataFeed, DataFeed>chunk(10)
                .reader(xmlReader(OVERRIDDEN_BY_EXPRESSION, OVERRIDDEN_BY_EXPRESSION, OVERRIDDEN_BY_EXPRESSION, OVERRIDDEN_BY_EXPRESSION, OVERRIDDEN_BY_EXPRESSION))
                .processor(xmlToJpaProcessor())
                .writer(repositoryWriter())
                .build();
    }

    @Bean
    public Step exportProductStep() {
        return this.steps
                .get("exportProductStep")
                .<it.oiritaly.data.models.jpa.DataFeed, it.oiritaly.data.models.amazon.AmazonEnvelope>chunk(10)
                .reader(jpaReader())
                .processor(jpaToXmlProductProcessor())
                .writer(productWriter(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean
    public Step exportRelationshipStep() {
        return this.steps
                .get("exportRelationshipStep")
                .<it.oiritaly.data.models.jpa.DataFeed, it.oiritaly.data.models.amazon.AmazonEnvelope>chunk(10)
                .reader(jpaReader())
                .processor(jpaToXmlRelationshipProcessor())
                .writer(relatonshipWriter(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean
    public Step exportInventoryStep() {
        return this.steps
                .get("exportInventoryStep")
                .<it.oiritaly.data.models.jpa.DataFeed, it.oiritaly.data.models.amazon.AmazonEnvelope>chunk(10)
                .reader(jpaReader())
                .processor(jpaToXmlInventoryProcessor())
                .writer(inventoryWriter(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean
    public Step exportPriceStep() {
        return this.steps
                .get("exportPriceStep")
                .<it.oiritaly.data.models.jpa.DataFeed, it.oiritaly.data.models.amazon.AmazonEnvelope>chunk(10)
                .reader(jpaReader())
                .processor(jpaToXmlPriceProcessor())
                .writer(priceWriter(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean
    public Step exportProductImageStep() {
        return this.steps
                .get("exportProductImageStep")
                .<it.oiritaly.data.models.jpa.DataFeed, it.oiritaly.data.models.amazon.AmazonEnvelope>chunk(10)
                .reader(jpaReader())
                .processor(JpaToXmlAmazonProductImageProcessor())
                .writer(productImageWriter(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean
    public Step postProductDataStep() {
        return this.steps
                .get("postProductDataStep")
                .tasklet(postProductDataTasklet(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean
    public Step postRelationshipDataStep() {
        return this.steps
                .get("postRelationshipDataStep")
                .tasklet(postRelationshipDataTasklet(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean
    public Step postProductImageDataStep() {
        return this.steps
                .get("postProductImageDataStep")
                .tasklet(postProductImageDataTasklet(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean
    public Step postProductPricingDataStep() {
        return this.steps
                .get("postProductPricingDataStep")
                .tasklet(postProductPricingDataTasklet(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean
    public Step postInventoryAvailabilityDataStep() {
        return this.steps
                .get("postInventoryAvailabilityDataStep")
                .tasklet(postInventoryAvailabilityDataTasklet(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean
    public ItemProcessor<it.oiritaly.data.models.xml.DataFeed, DataFeed> xmlToJpaProcessor() {
        return new XmlToJpaProcessor();
    }

    @Bean
    @StepScope
    public ItemStreamReader<it.oiritaly.data.models.xml.DataFeed> xmlReader(@Value("#{jobParameters[pathToFile]}") String pathToFile,
                                                                            @Value("#{jobParameters[ftpHost]}") String ftpHost,
                                                                            @Value("#{jobParameters[ftpPort]}") String ftpPort,
                                                                            @Value("#{jobParameters[ftpUser]}") String ftpUser,
                                                                            @Value("#{jobParameters[ftpPassword]}") String ftpPassword) {
        StaxEventItemReader<it.oiritaly.data.models.xml.DataFeed> reader = new StaxEventItemReader<>();
        reader.setStrict(true);
        try {
            reader.setResource(new UrlResource("ftp://" + ftpUser + ":" + ftpPassword + "@" + ftpHost + ":" + ftpPort + pathToFile));

        } catch (MalformedURLException e) {
            log.error("Malformed URL to XML.");
        }
        reader.setFragmentRootElementName("datafeed");
        Jaxb2Marshaller dataFeedMarshaller = new Jaxb2Marshaller();
        dataFeedMarshaller.setClassesToBeBound(it.oiritaly.data.models.xml.DataFeed.class);
        reader.setUnmarshaller(dataFeedMarshaller);
        return reader;
    }

    @Bean
    public ItemWriter<DataFeed> repositoryWriter() {
        RepositoryItemWriter<DataFeed> writer = new RepositoryItemWriter<>();
        writer.setRepository(dataFeedRepository);
        writer.setMethodName("save");
        return writer;
    }


    @Bean
    @StepScope
    public ItemReader<it.oiritaly.data.models.jpa.DataFeed> jpaReader() {
        return new DataFeedItemReader();
    }


    @Bean
    @StepScope
    public ItemStreamWriter<it.oiritaly.data.models.amazon.AmazonEnvelope> productWriter(@Value("#{jobParameters[marketplaceId]}") String marketplaceId) {
        CustomStaxEventItemWriter<it.oiritaly.data.models.amazon.AmazonEnvelope> writer = new CustomStaxEventItemWriter(false);
        writer.setResource(new FileSystemResource(configurationProperties.getOutputBasePath() + marketplaceId + configurationProperties.getProductDataFilename()));
        writer.setRootTagName("");
        writer.setMarshaller(unmarshaller());
        return writer;
    }

    @Bean
    @StepScope
    public ItemStreamWriter<it.oiritaly.data.models.amazon.AmazonEnvelope> relatonshipWriter(@Value("#{jobParameters[marketplaceId]}") String marketplaceId) {
        CustomStaxEventItemWriter<it.oiritaly.data.models.amazon.AmazonEnvelope> writer = new CustomStaxEventItemWriter(false);
        writer.setResource(new FileSystemResource(configurationProperties.getOutputBasePath() + marketplaceId + configurationProperties.getRelationshipDataFilename()));
        writer.setRootTagName("");
        writer.setMarshaller(unmarshaller());
        return writer;
    }

    @Bean
    @StepScope
    public ItemStreamWriter<it.oiritaly.data.models.amazon.AmazonEnvelope> inventoryWriter(@Value("#{jobParameters[marketplaceId]}") String marketplaceId) {
        CustomStaxEventItemWriter<it.oiritaly.data.models.amazon.AmazonEnvelope> writer = new CustomStaxEventItemWriter(false);
        writer.setResource(new FileSystemResource(configurationProperties.getOutputBasePath() + marketplaceId + configurationProperties.getInventoryAvailabilityDataFilename()));
        writer.setRootTagName("");
        writer.setMarshaller(unmarshaller());
        return writer;
    }

    @Bean
    @StepScope
    public ItemStreamWriter<it.oiritaly.data.models.amazon.AmazonEnvelope> priceWriter(@Value("#{jobParameters[marketplaceId]}") String marketplaceId) {
        CustomStaxEventItemWriter<it.oiritaly.data.models.amazon.AmazonEnvelope> writer = new CustomStaxEventItemWriter(false);
        writer.setResource(new FileSystemResource(configurationProperties.getOutputBasePath() + marketplaceId + configurationProperties.getProductPricingDataFilename()));
        writer.setRootTagName("");
        writer.setMarshaller(unmarshaller());
        return writer;
    }

    @Bean
    @StepScope
    public ItemStreamWriter<it.oiritaly.data.models.amazon.AmazonEnvelope> productImageWriter(@Value("#{jobParameters[marketplaceId]}") String marketplaceId) {
        CustomStaxEventItemWriter<it.oiritaly.data.models.amazon.AmazonEnvelope> writer = new CustomStaxEventItemWriter(false);
        writer.setResource(new FileSystemResource(configurationProperties.getOutputBasePath() + marketplaceId + configurationProperties.getProductImageFilename()));
        writer.setRootTagName("");
        writer.setMarshaller(unmarshaller());
        return writer;
    }

    @Bean
    public Marshaller unmarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(it.oiritaly.data.models.amazon.AmazonEnvelope.class);
        return marshaller;
    }
}
