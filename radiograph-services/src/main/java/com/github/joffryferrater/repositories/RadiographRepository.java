package com.github.joffryferrater.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.github.joffryferrater.models.Radiograph;

/**
 * 
 * @author Joffry Ferrater
 *
 */
@RestResource(path="radiographs", rel="radiographs")
public interface RadiographRepository extends CrudRepository<Radiograph, Long> {

}
