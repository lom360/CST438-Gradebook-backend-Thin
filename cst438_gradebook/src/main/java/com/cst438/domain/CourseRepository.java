package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository <Course, Integer> {
	@Query(value="select * from course where instructor= ?", nativeQuery=true)
	Course findCourseByEmail(String email); // I ADDED THIS CODE HERE
	
}
