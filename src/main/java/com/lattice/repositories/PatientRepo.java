package com.lattice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lattice.entity.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

	List<Patient> findByCityContaining(String city);
	
	
	@Query("select u from Patient u where u.city = ?1 AND u.symptom= ?2 OR u.symptom =?3 OR u.symptom=?4")
	List<Patient> findBySymptomAndCity(String city,String symptom1,String sysmtom2,String sysmptom3);
	
	@Query("select u from Patient u where u.city = ?1 AND u.symptom= ?2 OR u.symptom=?3")
	List<Patient> findBySymptomAndCity(String city,String symptom1,String sysmtom2);
	
	@Query("select u from Patient u where u.city = ?1 AND u.symptom= ?2")
	List<Patient> findBySymptomAndCity(String city,String symptom);
}
