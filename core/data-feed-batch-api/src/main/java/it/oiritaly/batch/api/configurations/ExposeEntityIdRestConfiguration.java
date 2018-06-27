package it.oiritaly.batch.api.configurations;

import it.oiritaly.data.models.jpa.rest.Brand;
import it.oiritaly.data.models.jpa.rest.Rule;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class ExposeEntityIdRestConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Brand.class);
        config.exposeIdsFor(Rule.class);
    }
}
