package com.lattice.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lattice.payload.ApiResponse;
import com.lattice.payload.PatientDto;
import com.lattice.service.PatientService;

@RestController
@RequestMapping("api/patient")
public class PatientContoller {
	
	@Autowired
	private PatientService patientService;
	
	@PostMapping("/")
	private ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto)throws IOException{

		String email = patientDto.getEmail(); 
		if(!email.matches(".+@gmail.com")) throw new IllegalArgumentException("Invalid Email");
		PatientDto patientDto1 = this.patientService.createPatient(patientDto);
		
		return new ResponseEntity<PatientDto>(patientDto1,HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{patientId}")
	public ResponseEntity<ApiResponse> deletePatient(@PathVariable Integer patientId ){
		this.patientService.deletePatient(patientId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Patient delete Successfully",true),HttpStatus.OK);
	}
	
	

}
