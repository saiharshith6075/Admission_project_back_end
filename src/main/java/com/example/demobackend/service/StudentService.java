package com.example.demobackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demobackend.model.Student;
import com.example.demobackend.repository.StudentRepo;

@Service
public class StudentService {
	@Autowired
	private StudentRepo studentRepo;
	
	
	 public StudentService(StudentRepo studentRepo) {
		super();
		this.studentRepo = studentRepo;
	}

	public Student insertStudent(Student student) {
	        return studentRepo.save(student);
	    }

	    public List<Student> getAllStudents() {
	        return studentRepo.findAll();
	    }
	    public Student getStudentById(long id) {
			return studentRepo.findById(id).get();
		}
	    
	    public Student login(String email,String password) {
	        Student student = studentRepo.findByEmail(email);
	        if(student != null && student.getPassword().equals(password))
	        {
	        	return student;
	        }
	        else {
	        return null;}
	    }
	  

}
