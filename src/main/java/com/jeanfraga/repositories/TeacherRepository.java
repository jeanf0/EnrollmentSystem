package com.jeanfraga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanfraga.models.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{

}
