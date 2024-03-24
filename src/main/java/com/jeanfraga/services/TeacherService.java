package com.jeanfraga.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.jeanfraga.controllers.TeacherController;
import com.jeanfraga.data.dto.TeacherDTO;
import com.jeanfraga.exceptions.ResourceNotFoundException;
import com.jeanfraga.mapper.Mapper;
import com.jeanfraga.models.Teacher;
import com.jeanfraga.repositories.TeacherRepository;

@Service
public class TeacherService {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private PagedResourcesAssembler<TeacherDTO> assembler;
	
	
	public PagedModel<EntityModel<TeacherDTO>> findAll(Pageable pageable) {
		var teachersPage = teacherRepository.findAll(pageable);
		var teachersDTOsPage = teachersPage.map(t -> Mapper.parseObject(t, TeacherDTO.class));
		teachersDTOsPage.map(t -> t.add(linkTo(methodOn(TeacherController.class).findById(t.getKey())).withSelfRel()));
		
		
		
		Link link = linkTo(
				methodOn(TeacherController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(teachersDTOsPage, link);
	}
	
	public PagedModel<EntityModel<TeacherDTO>> findAllTeacherByName(String firstName, Pageable pageable) {
		var teachersPage = teacherRepository.findByFirstNameContaining(firstName, pageable);
		var teachersDTOsPage = teachersPage.map(t -> Mapper.parseObject(t, TeacherDTO.class));
		teachersDTOsPage.map(t -> t.add(linkTo(methodOn(TeacherController.class).findById(t.getKey())).withSelfRel()));
		
		
		
		Link link = linkTo(
				methodOn(TeacherController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(teachersDTOsPage, link);
	}
	
	public TeacherDTO findById(Long id) {
		var entity = teacherRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		var vo = Mapper.parseObject(entity,TeacherDTO.class);
		vo.add(linkTo(methodOn(TeacherController.class).findById(id)).withSelfRel());
		
		return vo;
	}
	
	public TeacherDTO create(TeacherDTO teacherDTO) {
		var entity = Mapper.parseObject(teacherDTO, Teacher.class);
		
		var vo = Mapper.parseObject(teacherRepository.save(entity), TeacherDTO.class);
		vo.add(linkTo(methodOn(TeacherController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public TeacherDTO update(TeacherDTO teacherDTO) {
		var teacher = teacherRepository.findById(teacherDTO.getKey())
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		
		teacher.setFirstName(teacherDTO.getFirstName());
		teacher.setLastName(teacherDTO.getLastName());
		teacher.setBirthday(teacherDTO.getBirthday());
		
		var entity = teacherRepository.save(teacher);
		
		var vo = Mapper.parseObject(entity, TeacherDTO.class);
		vo.add(linkTo(methodOn(TeacherController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		var entity = teacherRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		teacherRepository.delete(entity);
	}
	
}
