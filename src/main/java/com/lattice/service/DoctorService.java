package com.lattice.service;

import java.util.List;

import com.lattice.payload.DoctorDto;

public interface DoctorService {

	
	DoctorDto createDoctor(DoctorDto doctorDto);
	
	void deleteDoctor(Integer doctorDto);
	
	List<DoctorDto> SuggestingDoctorBasedOnSymptom(Integer Id);
}
