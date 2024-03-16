package com.jeanfraga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return Mapper.parseListObjects(entities, TeacherDTO.class);
	}
	
	public TeacherDTO findById(Long id) {
		var entity = teacherRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Teacher not found!"));
		
		
		System.out.println(entity.toString());
		var vo = Mapper.parseObject(entity,TeacherDTO.class);
		System.out.println();
		return vo;
	}
	
	public TeacherDTO create(TeacherDTO teacherDTO) {
		var entity = Mapper.parseObject(teacherDTO, Teacher.class);
		
		return Mapper.parseObject(teacherRepository.save(entity), TeacherDTO.class);
	}
	
	public TeacherDTO update(TeacherDTO teacherDTO) {
		var teacher = teacherRepository.findById(teacherDTO.getId())
				.orElseThrow(() -> new RuntimeException("Teacher not found!"));
		
		teacher.setFirstName(teacherDTO.getFirstName());
		teacher.setLastName(teacherDTO.getLastName());
		teacher.setBirthday(teacherDTO.getBirthday());
		
		var entity = teacherRepository.save(teacher);
		
		return Mapper.parseObject(entity, TeacherDTO.class);
	}
	
	public void delete(Long id) {
		var entity = teacherRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Teacher not found!"));
		teacherRepository.delete(entity);
	}
	
}
