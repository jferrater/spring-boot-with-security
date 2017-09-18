package com.github.joffryferrater.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.github.joffryferrater.models.Doctor;

/**
 *  * 
 * @author Joffry Ferrater
 *
 */
@RestResource(path="doctors", rel="doctors")
public interface DoctorRepository extends CrudRepository<Doctor, Long>{

}
