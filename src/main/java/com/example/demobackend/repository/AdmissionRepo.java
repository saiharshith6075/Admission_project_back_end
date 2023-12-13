package com.example.demobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demobackend.model.Admission;

@Repository
public interface AdmissionRepo extends JpaRepository<Admission, Long> {
	List<Admission> findByStudentId(Long studentId);
	public List<Admission> findByStatus(String status);
    // Custom queries or methods can be added here if needed
}
