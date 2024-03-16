package com.jeanfraga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeanfraga.data.dto.SubjectDTO;
import com.jeanfraga.mapper.Mapper;
import com.jeanfraga.models.Subject;
import com.jeanfraga.repositories.SubjectRepository;

@Service
public class SubjectService {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	
	public List<SubjectDTO> findAll() {
		var entities = subjectRepository.findAll();
		return Mapper.parseListObjects(entities, SubjectDTO.class);
	}
	
	public SubjectDTO findById(Long id) {
		var entity = subjectRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Subject not found!"));
		
		var vo = Mapper.parseObject(entity,SubjectDTO.class);
		return vo;
	}
	
	public SubjectDTO create(SubjectDTO subjectDTO) {
		var entity = Mapper.parseObject(subjectDTO, Subject.class);
		
		return Mapper.parseObject(subjectRepository.save(entity), SubjectDTO.class);
	}
	
	public SubjectDTO update(SubjectDTO subjectDTO) {
		var subject = subjectRepository.findById(subjectDTO.getId())
				.orElseThrow(() -> new RuntimeException("Subject not found!"));
		
		subject.setName(subjectDTO.getName());
		subject.setDescription(subjectDTO.getDescription());
		subject.setWorkload(subjectDTO.getWorkload());
		
		var entity = subjectRepository.save(subject);
		
		return Mapper.parseObject(entity ,SubjectDTO.class);
	}
	
	public void delete(Long id) {
		var entity = subjectRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Subject not found!"));
		subjectRepository.delete(entity);
	}
}
