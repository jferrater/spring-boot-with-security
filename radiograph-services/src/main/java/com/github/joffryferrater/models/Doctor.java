package com.github.joffryferrater.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User (Doctor) model object.
 * 
 * @author Joffry Ferrater
 *
 */
@Entity
@Table(name="doctors")
public class Doctor {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DOCTOR_ID")
	private Long id;
	@JsonProperty("Username")
	@Column(unique=true)
	private String username;
	
	public Doctor() {
		super();
	}
	
	public Doctor(String username) {
		this();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
