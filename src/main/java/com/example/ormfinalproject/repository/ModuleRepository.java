package com.example.ormfinalproject.repository;

import com.example.ormfinalproject.model.Course;
import com.example.ormfinalproject.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findByCourseOrderByOrderIndexAsc(Course course);
}


