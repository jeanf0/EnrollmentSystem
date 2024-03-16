package com.jeanfraga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanfraga.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}
