package com.jeanfraga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeanfraga.data.dto.TeacherDTO;
import com.jeanfraga.services.TeacherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/teacher")
@Tag(name = "Teacher", description = "Endpoints for Teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	
	@GetMapping
	@Operation(summary = "Finds all teachers", description = "Finds all teachers",
	tags = {"Teacher"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<PagedModel<EntityModel<TeacherDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
													@RequestParam(value = "size", defaultValue = "12") Integer size,
													@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(teacherService.findAll(pageable));
	}
	
	@GetMapping("/findTeacherByName/{firstName}")
	@Operation(summary = "Finds teachers by name", description = "Finds teachers by name",
	tags = {"Teacher"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class))
									)
			}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<PagedModel<EntityModel<TeacherDTO>>> findAllTeacherByName(
			@PathVariable(value = "firstName") String firstName,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(teacherService.findAllTeacherByName(firstName, pageable));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Finds a Teacher by your ID", description = "Finds a Teacher by your ID",
	tags = {"Teacher"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<TeacherDTO> findById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(teacherService.findById(id));
	}
	
	@PostMapping
	@Operation(summary = "Adds a new Teacher", description = "Adds a new Teacher by passing in a JSON representation of a Teacher",
	tags = {"Teacher"},
	responses = {
			@ApiResponse(description = "Created", responseCode="201",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<TeacherDTO> create(@RequestBody TeacherDTO teacherDTO) {
		return ResponseEntity.status(201).body(teacherService.create(teacherDTO));
	}
	
	@PutMapping
	@Operation(summary = "Updates a Teacher", description = "Updates a Teacher by passing in a JSON representation of a Teacher",
	tags = {"Teacher"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<TeacherDTO> update(@RequestBody TeacherDTO teacherDTO) {
		return ResponseEntity.ok(teacherService.update(teacherDTO));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes a Teacher", description = "Deletes a Teacher by passing your ID",
	tags = {"Teacher"},
	responses = {
			@ApiResponse(description = "No Content", responseCode="204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable Long id) {
		teacherService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	

}
