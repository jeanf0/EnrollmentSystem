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

import com.jeanfraga.data.dto.SubjectDTO;
import com.jeanfraga.services.SubjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/subject")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@GetMapping
	@Operation(summary = "Finds all subjects", description = "Finds all subjects",
	tags = {"Subject"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<PagedModel<EntityModel<SubjectDTO>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
													@RequestParam(value = "size", defaultValue = "12") Integer size,
													@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(subjectService.findAll(pageable));
	}
	
	@GetMapping("/findSubjectByName/{name}")
	@Operation(summary = "Finds subjects by name", description = "Finds subjects by name",
	tags = {"Subject"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class))
									)
			}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<PagedModel<EntityModel<SubjectDTO>>> findAllSubjectByName(
			@PathVariable(value = "name") String name,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
		return ResponseEntity.ok(subjectService.findAllSubjectByName(name, pageable));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Finds a Subject by your ID", description = "Finds a Subject by your ID",
	tags = {"Subject"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<SubjectDTO> findById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(subjectService.findById(id));
	}
	
	@PostMapping
	@Operation(summary = "Adds a new Subject", description = "Adds a new Subject by passing in a JSON representation of a Subject",
	tags = {"Subject"},
	responses = {
			@ApiResponse(description = "Created", responseCode="201",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<SubjectDTO> create(@RequestBody SubjectDTO subjectDTO) {
		return ResponseEntity.status(201).body(subjectService.create(subjectDTO));
	}
	
	@PutMapping
	@Operation(summary = "Updates a Subject", description = "Updates a Subject by passing in a JSON representation of a Subject",
	tags = {"Subject"},
	responses = {
			@ApiResponse(description = "Success", responseCode="200",
					content = {
							@Content(
									mediaType = "Application/json",
									array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class))
									)
					}),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<SubjectDTO> update(@RequestBody SubjectDTO subjectDTO) {
		return ResponseEntity.ok(subjectService.update(subjectDTO));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Deletes a Subject", description = "Deletes a Subject by passing your ID",
	tags = {"Subject"},
	responses = {
			@ApiResponse(description = "No Content", responseCode="204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode="400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode="401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode="404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode="500", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable Long id) {
		subjectService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	

}
