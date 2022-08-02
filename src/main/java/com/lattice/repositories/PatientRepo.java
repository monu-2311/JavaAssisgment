package com.lattice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lattice.entity.Patient;

@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer> {

}
