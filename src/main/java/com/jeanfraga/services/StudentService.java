package com.jeanfraga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeanfraga.data.dto.StudentDTO;
import com.jeanfraga.mapper.Mapper;
import com.jeanfraga.models.Student;
import com.jeanfraga.repositories.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	public List<StudentDTO> findAll() {
		var entities = studentRepository.findAll();
		return Mapper.parseListObjects(entities, StudentDTO.class);
	}
	
	public StudentDTO findById(Long id) {
		var entity = studentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found!"));
		
		var vo = Mapper.parseObject(entity,StudentDTO.class);
		return vo;
	}
	
	public StudentDTO create(StudentDTO studentDTO) {
		var entity = Mapper.parseObject(studentDTO, Student.class);
		
		return Mapper.parseObject(studentRepository.save(entity), StudentDTO.class);
	}
	
	public StudentDTO update(StudentDTO studentDTO) {
		var student = studentRepository.findById(studentDTO.getId())
				.orElseThrow(() -> new RuntimeException("Student not found!"));
		
		student.setFirstName(studentDTO.getFirstName());
		student.setLastName(studentDTO.getLastName());
		student.setBirthday(studentDTO.getBirthday());
		
		var entity = studentRepository.save(student);
		
		return Mapper.parseObject(entity ,StudentDTO.class);
	}
	
	public void delete(Long id) {
		var entity = studentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found!"));
		studentRepository.delete(entity);
	}
	
	
}
