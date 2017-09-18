package com.github.joffryferrater.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Radiograph model object. 
 * Stores radiograph test results information.
 * 
 * @author Joffry Ferrater
 *
 */
@Entity
@Table(name="radiographs")
public class Radiograph {

	@Id 
	@Column(name="RAD_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	@JsonProperty("Reason")
	private String reason;
	@JsonProperty("Description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="PATIENT_ID")
	@JsonIgnore
	private Patient patient;

	@JsonProperty("DateOfTest")
	private String dateOfTest;
	
	public Radiograph(String reason, String description) {
		this();
		this.reason = reason;
		this.description = description;
		this.dateOfTest = this.getDateOfTest();
		
	}
	
	public Radiograph() {
		super();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.setDateOfTest(format.format(new Date()));
	}
	
	public Radiograph(Radiograph radiograph) {
		this(radiograph.getReason(), radiograph.getDescription());
	}


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateOfTest() {
		return dateOfTest;
	}

	public void setDateOfTest(String dateOfTest) {
		this.dateOfTest = dateOfTest;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}


}
