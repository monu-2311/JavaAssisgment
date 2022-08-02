package com.lattice.service;

import com.lattice.payload.PatientDto;

public interface PatientService {

	
	PatientDto createPatient(PatientDto patientDto);
	
	
	void deletePatient(Integer patientId);
	
	
	
}
