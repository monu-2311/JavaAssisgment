package com.lattice.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lattice.entity.Patient;
import com.lattice.exception.ResourceNotFoundException;
import com.lattice.payload.PatientDto;
import com.lattice.repositories.PatientRepo;
import com.lattice.service.PatientService;

@Service
public class PatientServiceImp implements PatientService {
	
	//It's provied the save ,FindById and delete function for the patient Entity
	@Autowired
	private PatientRepo patientRepo;
	
	@Autowired
	private ModelMapper modelemapper;

	@Override
	public PatientDto createPatient(PatientDto patientDto) {
		
		Patient patient = this.modelemapper.map(patientDto, Patient.class);
		
		//Return the Save object and store the the savePatient object
		Patient savePatient = this.patientRepo.save(patient);
		
		return this.modelemapper.map(savePatient, PatientDto.class);
	}

	
	@Override
	public void deletePatient(Integer patientId) {
		// TODO Auto-generated method stub
		Patient patient = this.patientRepo.findById(patientId).orElseThrow(()-> new ResourceNotFoundException("Patient", "PatientId", patientId));
		this.patientRepo.delete(patient);
	}


}
