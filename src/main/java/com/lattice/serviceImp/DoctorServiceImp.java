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
	public List<DoctorDto> SuggestingDoctorBasedOnSymptom(Integer patientId) {
	
	//Check if patient present in database or not if not then throw the ResourceNotFoundException exception
	Patient patient = this.patientRepo.findById(patientId).orElseThrow(()-> new ResourceNotFoundException("Patient", "PatientId", patientId));
	 
	//get the patient city and and store in location so I can use it to find if there any Doctor available or not
	String location = patient.getCity();
	
	//Return the String of specialty according to the patient Symptom
	String speciality = symptomTOspeciality(patient.getSymptom());
	
	//List of Doctor at patient location or city 
	List<Doctor> doctorCity = this.doctorRepo.findByCityContaining(location);
	
	//Handle the Exception if Doctor is not present at patient city or location 
	if(doctorCity.isEmpty()) throw new IllegalArgumentException("We are still waiting to expand to your location"); 
	
	//List of Doctor according to patient Symptom
	List<Doctor> doctorSpeciality = this.doctorRepo.findBySpecialityContaining(speciality);
	
	//Handle the Exception we don't have Doctor for the present Symptom in given city. 
	if(doctorSpeciality.isEmpty()) throw new IllegalArgumentException("There isn’t any doctor present at your location for your symptom"); 
	
	
	//List of all Doctor who present in given city and treat the patient symptom
	List<Doctor> doctorLists = this.doctorRepo.findBySpecialityAndCity(location, speciality);
	
	List<DoctorDto> doctorDtoLists = doctorLists.stream().map((dDL)->this.modelemapper.map(dDL, DoctorDto.class)).collect(Collectors.toList());	
	 
		return doctorDtoLists;
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
