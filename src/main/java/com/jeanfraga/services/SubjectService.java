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

import com.jeanfraga.controllers.SubjectController;
import com.jeanfraga.data.dto.SubjectDTO;
import com.jeanfraga.exceptions.ResourceNotFoundException;
import com.jeanfraga.mapper.Mapper;
import com.jeanfraga.models.Subject;
import com.jeanfraga.repositories.SubjectRepository;

@Service
public class SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private PagedResourcesAssembler<SubjectDTO> assembler;
	
	
	public PagedModel<EntityModel<SubjectDTO>> findAll(Pageable pageable) {
		var subjectsPage = subjectRepository.findAll(pageable);
		var subjectsDTOsPage = subjectsPage.map(s -> Mapper.parseObject(s, SubjectDTO.class));
		subjectsDTOsPage.map(s -> s.add(linkTo(methodOn(SubjectController.class).findById(s.getKey())).withSelfRel()));
		
		
		
		Link link = linkTo(
				methodOn(SubjectController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(subjectsDTOsPage, link);
	}
	
	public PagedModel<EntityModel<SubjectDTO>> findAllSubjectByName(String name, Pageable pageable) {
		var subjectsPage = subjectRepository.findByNameContaining(name, pageable);
		var subjectsDTOsPage = subjectsPage.map(s -> Mapper.parseObject(s, SubjectDTO.class));
		subjectsDTOsPage.map(s -> s.add(linkTo(methodOn(SubjectController.class).findById(s.getKey())).withSelfRel()));
		
		
		
		Link link = linkTo(
				methodOn(SubjectController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(subjectsDTOsPage, link);
	}
	
	public SubjectDTO findById(Long id) {
		var entity = subjectRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		
		var vo = Mapper.parseObject(entity,SubjectDTO.class);
		vo.add(linkTo(methodOn(SubjectController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public SubjectDTO create(SubjectDTO subjectDTO) {
		var entity = Mapper.parseObject(subjectDTO, Subject.class);
		
		var vo = Mapper.parseObject(subjectRepository.save(entity), SubjectDTO.class);
		vo.add(linkTo(methodOn(SubjectController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public SubjectDTO update(SubjectDTO subjectDTO) {
		var subject = subjectRepository.findById(subjectDTO.getKey())
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		
		subject.setName(subjectDTO.getName());
		subject.setDescription(subjectDTO.getDescription());
		subject.setWorkload(subjectDTO.getWorkload());
		
		var entity = subjectRepository.save(subject);
		
		var vo = Mapper.parseObject(entity ,SubjectDTO.class);
		vo.add(linkTo(methodOn(SubjectController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		var entity = subjectRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		subjectRepository.delete(entity);
	}
}
