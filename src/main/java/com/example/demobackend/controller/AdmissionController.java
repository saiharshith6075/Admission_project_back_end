package com.example.demobackend.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demobackend.config.MediaFileService;
import com.example.demobackend.model.Admission;
import com.example.demobackend.model.Enrollment;
import com.example.demobackend.model.Student;
import com.example.demobackend.repository.AdmissionRepo;
import com.example.demobackend.repository.EnrollmentRepo;
import com.example.demobackend.repository.StudentRepo;
import com.example.demobackend.service.AdmissionService;

@RestController
@RequestMapping("/admissions")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")

public class AdmissionController {
	@Autowired
	private AdmissionRepo admissionRepository;
	@Autowired
	private final AdmissionService admissionService;
	@Autowired
	private StudentRepo studentRepo;
	@Autowired
	private EnrollmentRepo enrollmentRepo;
	@Autowired
	private MediaFileService mediaFileService;

	public AdmissionController(AdmissionService admissionService) {
		this.admissionService = admissionService;
	}

	@GetMapping("/all")
	public List<Admission> getAllAdmissions() {
		return admissionRepository.findAll();
	}

	@GetMapping
	public ResponseEntity<List<Admission>> getAlladmissions() {
		List<Admission> admission = admissionService.getAlladmissions();
		return new ResponseEntity<>(admission, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Admission> getadmissionById(@PathVariable Long id) {
		Optional<Admission> admissionOptional = admissionRepository.findById(id);
		if (admissionOptional.isPresent()) {
			Admission admission = admissionOptional.get();
			return ResponseEntity.ok(admission);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<Admission> createAdmission(@ModelAttribute Admission admission,
			@RequestParam("documents") MultipartFile[] documents, @RequestParam("studentId") Long studentId) {
		System.out.println("Received " + documents.length + " documents.");

		Student student = studentRepo.findById(studentId)
				.orElseThrow(() -> new RuntimeException("student not found with id: " + studentId));

		// Set the agent for the property
		admission.setStudent(student);

		List<String> documentUrls = new ArrayList<>();

		for (MultipartFile document : documents) {
			try {
//				String fileName = mediaFileService.saveMediaFile(document);
				String fileName = mediaFileService.saveFile(document);
				documentUrls.add(fileName);
			} catch (IOException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		admission.setRequiredDocuments(documentUrls);

		Admission createdAdmission = admissionService.createAdmission(admission);
		return new ResponseEntity<>(createdAdmission, HttpStatus.CREATED);

	}

	@PutMapping("{id}")
	public ResponseEntity<Admission> updatestatus(@PathVariable long id, @RequestBody Map<String, String> data) {
		Admission app = admissionService.getadmissionById(id);
		app.setStatus(data.get("status"));
		app.setFeedback(data.get("feedback"));

		admissionRepository.save(app);

		return ResponseEntity.ok(app);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<List<Admission>> deleteAdmissionById(@PathVariable Long id) {
		try {
			// First, find the student record to check if it exists
			Student student = studentRepo.findById(id).orElse(null);
			if (student == null) {
				return ResponseEntity.notFound().build();
			}

			// Second, delete the associated admission records
			List<Admission> admissionsToDelete = admissionRepository.findByStudentId(id);
			admissionRepository.deleteAll(admissionsToDelete);

			// Third, delete the associated enrollment records
			List<Enrollment> enrollmentsToDelete = enrollmentRepo.findByStudentId(id);
			enrollmentRepo.deleteAll(enrollmentsToDelete);

			// Finally, delete the student record studentRepo.delete(student);
			studentRepo.delete(student);

			return ResponseEntity.ok(admissionRepository.findAll());
		} catch (Exception e) {
			// Log the error for debugging purposes
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// Other endpoints and methods
}