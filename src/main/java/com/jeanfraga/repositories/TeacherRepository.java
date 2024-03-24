package com.jeanfraga.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeanfraga.models.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	
	Page<Teacher> findByFirstNameContaining(String firstName, Pageable pageable);
}
