package com.lattice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lattice.entity.Doctor;
import com.lattice.payload.DoctorDto;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer> {
	
	List<Doctor> findByCityContaining(String city);
	
	List<Doctor> findBySpecialityContaining(String speciality);
	
	@Query("select u from Doctor u where u.city = :city and u.speciality= :speciality")
	List<Doctor> findBySpecialityAndCity(String city,String speciality);
}
