package com.example.demobackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demobackend.model.Enrollment;
import com.example.demobackend.repository.EnrollmentRepo;



@Service
public class EnrollmentService {

	@Autowired
	private EnrollmentRepo enrolrep;
	
	public Enrollment getEnrolmentById(long id) {
		return enrolrep.findById(id).get();
	}
	
}
