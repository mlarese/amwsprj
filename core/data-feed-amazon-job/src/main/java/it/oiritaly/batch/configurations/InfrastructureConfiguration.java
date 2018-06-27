package it.oiritaly.batch.configurations;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@EntityScan(basePackages = InfrastructureConfiguration.ENTITY_SCAN_BASE_PACKAGES)
@EnableJpaRepositories(
        entityManagerFactoryRef = InfrastructureConfiguration.ENTITY_MANAGER_FACTORY_BEAN_NAME,
        transactionManagerRef = InfrastructureConfiguration.TRANSACTION_MANAGER_BEAN_NAME,
        basePackages = InfrastructureConfiguration.JPA_REPOSITORIES_BASE_PACKAGES)
public class InfrastructureConfiguration {
    public static final String CONFIGURATION_PROPERTIES_PREFIX = "oiritaly.datasource";

    public static final String DATA_SOURCE_BEAN_NAME = "oiritalyDataSource";
    public static final String ENTITY_MANAGER_BEAN_NAME = "oiritalyEntityManager";
    public static final String ENTITY_MANAGER_FACTORY_BEAN_NAME = "oiritalyEntityManagerFactory";
    public static final String TRANSACTION_MANAGER_BEAN_NAME = "oiritalyTransactionManager";

    public static final String ENTITY_MANAGER_FACTORY_PERSISTENCE_UNIT_NAME = "oiritaly";

    public static final String JPA_REPOSITORIES_BASE_PACKAGES = "it.oiritaly.data.repositories.jpa";
    public static final String ENTITY_SCAN_BASE_PACKAGES = "it.oiritaly.data.models.jpa";

    @Bean(name = DATA_SOURCE_BEAN_NAME)
    @ConfigurationProperties(prefix = InfrastructureConfiguration.CONFIGURATION_PROPERTIES_PREFIX)
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


    private final JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    public InfrastructureConfiguration(JpaVendorAdapter jpaVendorAdapter) {
        this.jpaVendorAdapter = jpaVendorAdapter;
    }


    @Bean(name = ENTITY_MANAGER_BEAN_NAME)
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Value("${" + CONFIGURATION_PROPERTIES_PREFIX + ".dialect}")
    private String dialect;

    @Primary
    @Bean(name = ENTITY_MANAGER_FACTORY_BEAN_NAME)
    public EntityManagerFactory entityManagerFactory() {
        Properties properties = new Properties();
        // TODO configurable
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.globally_quoted_identifiers", "true");
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaProperties(properties);
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan(ENTITY_SCAN_BASE_PACKAGES);
        emf.setPersistenceUnitName(ENTITY_MANAGER_FACTORY_PERSISTENCE_UNIT_NAME);   // <- giving 'default' as name
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    @Bean(name = TRANSACTION_MANAGER_BEAN_NAME)
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory());
        return tm;
    }

//    public DataFeedRepository getDataFeedRepository() {
//        return dataFeedRepository;
//    }
}
