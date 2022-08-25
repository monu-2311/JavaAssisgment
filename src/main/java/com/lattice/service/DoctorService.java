package com.lattice.service;

import java.util.List;

import com.lattice.payload.DoctorDto;
import com.lattice.payload.PatientDto;

public interface DoctorService {

	
	DoctorDto createDoctor(DoctorDto doctorDto);
	
	void deleteDoctor(Integer doctorDto);
	
	List<PatientDto> SuggestingDoctorBasedOnSymptom(Integer Id);
}
