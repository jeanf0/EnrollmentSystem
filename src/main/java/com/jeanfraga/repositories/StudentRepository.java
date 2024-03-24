package com.jeanfraga.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeanfraga.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

	Page<Student> findByFirstNameContaining(String firstName, Pageable pageable);
}
