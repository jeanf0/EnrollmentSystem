package com.jeanfraga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeanfraga.data.dto.StudentDTO;
import com.jeanfraga.services.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public ResponseEntity<List<StudentDTO>> findAll() {
		return ResponseEntity.ok(studentService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StudentDTO> findById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(studentService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO studentDTO) {
		return ResponseEntity.status(201).body(studentService.create(studentDTO));
	}
	
	@PutMapping
	public ResponseEntity<StudentDTO> update(@RequestBody StudentDTO studentDTO) {
		return ResponseEntity.ok(studentService.update(studentDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		studentService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	

}
