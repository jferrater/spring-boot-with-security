package com.github.joffryferrater;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.joffryferrater.models.Doctor;
import com.github.joffryferrater.models.Patient;
import com.github.joffryferrater.models.Radiograph;
import com.github.joffryferrater.repositories.DoctorRepository;
import com.github.joffryferrater.repositories.PatientRepository;

/**
 * 
 * @author Joffry Ferrater
 *
 */
@SpringBootApplication
public class RadiographServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadiographServiceApplication.class, args);
	}
	
	@Autowired
	DoctorRepository doctorRepo;
	@Autowired
	PatientRepository patientRepo;
	
	/*
	 * Default sample data.
	 */
	@PostConstruct
	public void init() {
		final String JAMES = "James";
		final String BOND = "Bond";
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors.add(new Doctor(JAMES));
		doctors.add(new Doctor(BOND));
		doctors.forEach(doctor -> doctorRepo.save(doctor));
		
		//Dr. James' Patient 1 data
		List<Radiograph> radiographs = new ArrayList<Radiograph>();
		Radiograph result1 = new Radiograph("Not feeling well", "Radiograph of Joffry");
		radiographs.add(result1);
		Patient patient1 = new Patient("19850127", "Joffry", "Ferrater", 31, radiographs);
		result1.setPatient(patient1);
		patient1.setDoctor(JAMES);
		patientRepo.save(patient1);
		
		//Dr. Bond's Patient 2 data
		List<Radiograph> radiographs2 = new ArrayList<Radiograph>();
		Radiograph result2 = new Radiograph("Cough", "Radiograph of Kumar");
		radiographs2.add(result2);
		Patient patient2 = new Patient("19830317", "Mirtunjay", "Kumar", 33, radiographs2);
		result2.setPatient(patient2);
		patient2.setDoctor(BOND);
		patientRepo.save(patient2);
		
		List<Radiograph> radiographs3 = new ArrayList<Radiograph>();
		Radiograph result3 = new Radiograph("Difficult to breath", "Jolly Jae first checkup");
		radiographs3.add(result3);
		Patient patient3 = new Patient("19800912", "Jolly Jae", "Ompod", 36, radiographs3);
		result3.setPatient(patient3);
		patient3.setDoctor(BOND);
		patientRepo.save(patient3);
	}
}
