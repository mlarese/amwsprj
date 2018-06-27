package it.oiritaly.batch.api.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class QuartzDatabaseInitializer {

    @Autowired
    private QuartzConfigurationProperties properties;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    protected void initialize() {
        if (this.properties.getDatabaseInitScript() != null) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new FileSystemResource(this.properties.getDatabaseInitScript()));
            populator.setContinueOnError(false);
            DatabasePopulatorUtils.execute(populator, this.dataSource);
        }
    }

}
