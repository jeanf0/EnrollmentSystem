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

import com.jeanfraga.data.dto.CourseDTO;
import com.jeanfraga.services.CourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping
	@Operation(summary = "Finds all Courses", description = "Finds all Courses",
	tags = {"Courses"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<List<CourseDTO>> findAll() {
		return ResponseEntity.ok(courseService.findAll());
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Finds a Course by your ID", description = "Finds a Course by your ID",
	tags = {"Courses"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<CourseDTO> findById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(courseService.findById(id));
	}
	
	@PostMapping
	@Operation(summary = "Adds a new Course", description = "Adds a new Course by passing in a JSON representation of a Course",
	tags = {"Courses"},
	responses = {
			@ApiResponse(description = "Created", responseCode="201",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO courseDTO) {
		return ResponseEntity.status(201).body(courseService.create(courseDTO));
	}
	
	@PutMapping
	@Operation(summary = "Updates a Course", description = "Updates a Course by passing in a JSON representation of a Course",
	tags = {"Courses"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<CourseDTO> update(@RequestBody CourseDTO courseDTO) {
		return ResponseEntity.ok(courseService.update(courseDTO));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes a Course", description = "Deletes a Course by passing your ID",
	tags = {"Courses"},
	responses = {
			@ApiResponse(description = "No Content", responseCode="204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable Long id) {
		courseService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{courseId}/subject/{subjectId}")
	@Operation(summary = "Set a Subject on Course", description = "Set a Subject on Course by passing both IDs",
	tags = {"Courses"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<CourseDTO> setSubject(@PathVariable Long courseId, @PathVariable Long subjectId) {
		return ResponseEntity.ok(courseService.setSubject(courseId, subjectId));
	}
	
	@PutMapping("/{courseId}/teacher/{teacherId}")
	@Operation(summary = "Assign a Teacher on Course", description = "Assign a Teacher on Course by passing both IDs",
	tags = {"Courses"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<CourseDTO> assignTeacher(@PathVariable Long courseId, @PathVariable Long teacherId) {
		return ResponseEntity.ok(courseService.assignTeacher(courseId, teacherId));
	}
	
	@PutMapping("/{courseId}/student/{studentId}")
	@Operation(summary = "Enroll a Student on Course", description = "Enroll a Student on Course by passing both IDs",
	tags = {"Courses"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<CourseDTO> enrollStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
		return ResponseEntity.ok(courseService.enrollStudent(courseId, studentId));
	}

}
