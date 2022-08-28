package com.lattice.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lattice.entity.Doctor;
import com.lattice.entity.Patient;
import com.lattice.exception.ResourceNotFoundException;
import com.lattice.payload.DoctorDto;
import com.lattice.payload.PatientDto;
import com.lattice.repositories.DoctorRepo;
import com.lattice.repositories.PatientRepo;
import com.lattice.service.DoctorService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.util.stream.Collectors;   


@Service
public class DoctorServiceImp implements DoctorService {

	//It provide the built-in function for Delete and Create the Doctor Entity
	@Autowired
	private DoctorRepo doctorRepo;
	
	
	//Used to convert the one object into the another object with same field
	@Autowired
	private ModelMapper modelemapper;
	
	
	//Used for when we need to find the patient in SuggestingDoctorBasedOnSymptom function
	@Autowired
	private PatientRepo patientRepo;
	
	
	//Function to Create the Doctor
	@Override
	public DoctorDto createDoctor(DoctorDto doctorDto) {
		Doctor doctor = this.modelemapper.map(doctorDto, Doctor.class);
		
		Doctor saveDoctor =this.doctorRepo.save(doctor);
		return this.modelemapper.map(saveDoctor, DoctorDto.class);
	}

	
	//Function to Delete the Doctor
	@Override
	public void deleteDoctor(Integer doctorId) {
		Doctor doctor = this.doctorRepo.findById(doctorId).orElseThrow(()-> new ResourceNotFoundException("Doctor","doctorId",doctorId));
		this.doctorRepo.delete(doctor);
	}


	//Function Used to return of List of doctor according to  patient city and Symptom
	@Override
	public List<PatientDto> SuggestingDoctorBasedOnSymptom(Integer doctorId) {
		//find the paitent Deltails
	    Doctor doc = this.doctorRepo.findById(doctorId).orElseThrow(()->new ResourceNotFoundException("doctorId", "doctorId", doctorId));
	    
	    //store paitent city into city
	    String city  = doc.getCity();
	    String sys = doc.getSpeciality();
	    
	    List<Patient> pat1 = this.patientRepo.findByCityContaining(city);
	    if(pat1.isEmpty()) {
	    	throw new IllegalArgumentException("We are still waiting for expand");
	    }
	    
	    List<Patient> paitent = new ArrayList<Patient>();
	    
	    if(sys.equalsIgnoreCase("Orthopedic")) {
	    	
	        paitent = this.patientRepo.findBySymptomAndCity(city, "Arthritis", "Backpain", "Tissue injuries");
	    	if(paitent.isEmpty()) {
		    	throw new IllegalArgumentException("Patient Is not able");
		    }
	    	
	    }else if(sys.equalsIgnoreCase("Dermatology")) {
	    	 paitent = this.patientRepo.findBySymptomAndCity(city, "Skin infection", "skin burn");
	    	if(paitent.isEmpty()) {
		    	throw new IllegalArgumentException("Patient Is not able");
		    }
	    	
	    }else if(sys.equalsIgnoreCase("Gynecology")) {
	    	paitent = this.patientRepo.findBySymptomAndCity(city, "Dysmenorrhea");
	    	if(paitent.isEmpty()) {
		    	throw new IllegalArgumentException("Patient Is not able");
		    }
	    	
	    }else{
	    	paitent = this.patientRepo.findBySymptomAndCity(city, "Ear pain");
	    	if(paitent.isEmpty()) {
		    	throw new IllegalArgumentException("Patient Is not able");
		    }
	    }
	  
	    List<PatientDto> ptDtoList = paitent.stream().map((e)-> this.modelemapper.map(e,PatientDto.class)).collect(Collectors.toList());
    	
    	return ptDtoList;
	}
		
	
	//Return the Specialty according to Symptom
	public String symptomTOspeciality(String symptom) {
		
		if(symptom.equalsIgnoreCase("Arthritis") || symptom.equalsIgnoreCase("Backpain") || symptom.equalsIgnoreCase("Tissue injuries")) return "Orthopedic";
		else if(symptom.equalsIgnoreCase("Skin infection") || symptom.equalsIgnoreCase("skin burn")) return "Dermatology";			
	    else if(symptom.equalsIgnoreCase("Dysmenorrhea")) return "Gynecology";	
		else if(symptom.equalsIgnoreCase("Ear pain")) return "ENT";
			
		
		return "Null";
	}
	
	

}
