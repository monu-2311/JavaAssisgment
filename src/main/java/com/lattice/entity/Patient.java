package com.lattice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="patient")
@NoArgsConstructor
@Getter
@Setter
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	private String name;
	
	private String phoneNumber;
	
	private String city;
	
	private String email;
	
	private String symptom;
}
