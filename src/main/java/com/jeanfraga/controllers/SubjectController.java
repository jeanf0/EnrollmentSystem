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

import com.jeanfraga.data.dto.SubjectDTO;
import com.jeanfraga.services.SubjectService;

@RestController
@RequestMapping("/subject")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@GetMapping
	public ResponseEntity<List<SubjectDTO>> findAll() {
		return ResponseEntity.ok(subjectService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubjectDTO> findById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(subjectService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<SubjectDTO> create(@RequestBody SubjectDTO subjectDTO) {
		return ResponseEntity.status(201).body(subjectService.create(subjectDTO));
	}
	
	@PutMapping
	public ResponseEntity<SubjectDTO> update(@RequestBody SubjectDTO subjectDTO) {
		return ResponseEntity.ok(subjectService.update(subjectDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		subjectService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	

}
