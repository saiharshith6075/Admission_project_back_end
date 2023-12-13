package com.example.demobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demobackend.model.Course;

public interface CourseRepo extends JpaRepository <Course, Long> {
	public Course findByname(String s); 

}
