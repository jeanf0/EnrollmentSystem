package com.jeanfraga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.jeanfraga.controllers.TeacherController;
import com.jeanfraga.data.dto.TeacherDTO;
import com.jeanfraga.mapper.Mapper;
import com.jeanfraga.models.Teacher;
import com.jeanfraga.repositories.TeacherRepository;

@Service
public class TeacherService {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	
	public List<TeacherDTO> findAll() {
		var entities = teacherRepository.findAll();
		var teachers = Mapper.parseListObjects(entities, TeacherDTO.class);
		teachers.stream().forEach(t -> t.add(linkTo(methodOn(TeacherController.class).findById(t.getKey())).withSelfRel()));
		
		return teachers;
	}
	
	public TeacherDTO findById(Long id) {
		var entity = teacherRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Teacher not found!"));
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
				.orElseThrow(() -> new RuntimeException("Teacher not found!"));
		
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
				.orElseThrow(() -> new RuntimeException("Teacher not found!"));
		teacherRepository.delete(entity);
	}
	
}
