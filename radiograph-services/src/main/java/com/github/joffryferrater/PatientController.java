package com.github.joffryferrater;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.joffryferrater.models.Patient;
import com.github.joffryferrater.models.Radiograph;
import com.github.joffryferrater.repositories.PatientRepository;
import com.github.joffryferrater.repositories.RadiographRepository;

/**
 * 
 * @author Joffry Ferrater
 *
 */
@RestController
public class PatientController {
	
	@Autowired
	private RadiographRepository radiographRepo;
	
	@Autowired
	private PatientRepository patientRepo;
	
	private final EntityLinks entityLinks;
	
	public PatientController(EntityLinks entityLinks) {
		this.entityLinks = entityLinks;
	}
	
    @RequestMapping(method = RequestMethod.POST,
    		        produces = MediaType.APPLICATION_JSON_VALUE,
    		        value = "/patients/{PATIENT_ID}/radiographs") 
    public HttpEntity<Resource<Radiograph>> addRadiograph(@PathVariable("PATIENT_ID") Long PATIENT_ID, 
    		@RequestBody Radiograph radiograph) {
        Patient patient = patientRepo.findOne(PATIENT_ID);
        if(patient==null) {
    		return new ResponseEntity<Resource<Radiograph>>(HttpStatus.NOT_FOUND);
        }
        List<Radiograph> radiographs = patient.getRadiographs();
        Radiograph newRadiograph = new Radiograph(radiograph);
        newRadiograph.setPatient(patient);
        radiographs.add(newRadiograph);
        radiographRepo.save(newRadiograph);
        Resource<Radiograph> resource = new Resource<Radiograph>(newRadiograph);
        resource.add(this.entityLinks.linkToSingleResource(Radiograph.class, 
        		newRadiograph.getId()));
        resource.add(this.entityLinks.linkToSingleResource(Patient.class, PATIENT_ID));
		return new ResponseEntity<Resource<Radiograph>>(resource, HttpStatus.OK);
    }

    
    @RequestMapping(method = RequestMethod.GET, 
    		        produces = MediaType.APPLICATION_JSON_VALUE, 
    		        value = "/patients/{PATIENT_ID}/radiographs")
	public HttpEntity<Resources<Radiograph>> showPatientRadiographs(@PathVariable("PATIENT_ID") Long PATIENT_ID) {
    	Patient patient = patientRepo.findOne(PATIENT_ID);
    	if(patient == null) {
    		return new ResponseEntity<Resources<Radiograph>>(HttpStatus.NOT_FOUND);
    	}
    	List<Radiograph> radiographs = patient.getRadiographs();
		Resources<Radiograph> resources = new Resources<Radiograph>(radiographs);
		radiographs.forEach(radiograph -> resources.add(this.entityLinks.
				linkToSingleResource(Radiograph.class, radiograph.getId())));
		
		return new ResponseEntity<Resources<Radiograph>>(resources, HttpStatus.OK);
	}
}
