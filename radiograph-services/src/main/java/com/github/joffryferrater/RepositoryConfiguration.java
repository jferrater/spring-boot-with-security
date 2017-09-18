package com.github.joffryferrater;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.github.joffryferrater.models.Patient;
import com.github.joffryferrater.models.Radiograph;

/**
 * 
 * @author Joffry Ferrater
 *
 */
@Configuration
public class RepositoryConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Patient.class);
        config.exposeIdsFor(Radiograph.class);
    }
}