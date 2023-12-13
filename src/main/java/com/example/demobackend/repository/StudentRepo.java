package com.example.demobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demobackend.model.Student;

public interface StudentRepo extends JpaRepository<Student, Long> {
	Student findByEmailAndPassword(String email, String password);
	Student findByEmail(String email);

}
