package com.lattice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lattice.entity.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

	List<Patient> findByCityContaining(String city);
	
	
	@Query("select u from Doctor u where u.city = :city and u.symptom= :symptom or u.symptom =: sysmtom2 or u.symptom=:sysmptom3")
	List<Patient> findBySymptomAndCity(String city,String Symptom1,String sysmtom2,String sysmptom3);
	
	@Query("select u from Doctor u where u.city = :city and u.symptom= :Symptom1 or u.symptom=:sysmtom2")
	List<Patient> findBySymptomAndCity(String city,String Symptom1,String sysmtom2);
	
	@Query("select u from Doctor u where u.city = :city and u.symptom= :symptom")
	List<Patient> findBySymptomAndCity(String city,String Symptom);
}
