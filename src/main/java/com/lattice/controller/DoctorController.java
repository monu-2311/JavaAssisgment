package com.lattice.controller;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lattice.payload.ApiResponse;
import com.lattice.payload.DoctorDto;
import com.lattice.payload.PatientDto;
import com.lattice.service.DoctorService;

@RestController
@RequestMapping("api/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	
	@PostMapping("/")
	private ResponseEntity<DoctorDto> createDoctor(@Valid @RequestBody DoctorDto doctorDto)throws IOException{

		String Email = doctorDto.getEmail(); 
		if(!Email.matches(".+@gmail.com")) throw new IllegalArgumentException("Invalid Email");
	
		
		DoctorDto doctorDto1 = this.doctorService.createDoctor(doctorDto);
		
		return new ResponseEntity<DoctorDto>(doctorDto1,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{doctorId}")
	public ResponseEntity<ApiResponse> deletePatient(@PathVariable Integer doctorId ){
		this.doctorService.deleteDoctor(doctorId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Doctor delete Successfully",true),HttpStatus.OK);
	}
	
	
	@GetMapping("/doctor/{doctorId}")
	public ResponseEntity<List<PatientDto>> SuggestingDoctorBasedOnSymptom(@PathVariable Integer doctorId ){
		List<PatientDto> doctorDtoList = this.doctorService.SuggestingDoctorBasedOnSymptom(doctorId);
		
		return new ResponseEntity<List<PatientDto>>(doctorDtoList,HttpStatus.CREATED);
	}
}
