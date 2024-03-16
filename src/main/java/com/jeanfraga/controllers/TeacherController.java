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

import com.jeanfraga.data.dto.TeacherDTO;
import com.jeanfraga.services.TeacherService;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	
	@GetMapping
	public ResponseEntity<List<TeacherDTO>> findAll() {
		return ResponseEntity.ok(teacherService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TeacherDTO> findById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(teacherService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<TeacherDTO> create(@RequestBody TeacherDTO teacherDTO) {
		return ResponseEntity.status(201).body(teacherService.create(teacherDTO));
	}
	
	@PutMapping
	public ResponseEntity<TeacherDTO> update(@RequestBody TeacherDTO teacherDTO) {
		return ResponseEntity.ok(teacherService.update(teacherDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		teacherService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	

}
