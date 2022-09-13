package com.cst438.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentListDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.CourseRepository;

@RestController
public class AssignmentController {
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	AssignmentGradeRepository assignmentGradeRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	
	@GetMapping("/course/assignment")
	public AssignmentListDTO getAssignmentByCourse() {
		List<Assignment> assignments = assignmentRepository.findAssignmentByCourse();
		AssignmentListDTO result = new AssignmentListDTO();
		for (Assignment a: assignments) {
			result.assignments.add(new AssignmentListDTO.AssignmentDTO(a.getId(), a.getCourse().getCourse_id(), a.getName(), a.getDueDate().toString() , a.getCourse().getTitle()));
		}
		return result;
	}
	
	@PostMapping("/course/assignment")
	public void addAssignment(@RequestBody Assignment assignment) {
		assignmentRepository.save(assignment);
	}
	
	@DeleteMapping("/course/assignment/{id}")
	public String deleteAssignment(@PathVariable int id) {
		Optional<Assignment> assignment = assignmentRepository.findById(id);
		if(assignment.isPresent()) {
			assignmentRepository.delete(assignment.get());
			return "Assignment with " + id + " has been deleted.";
		}
		else {
			throw new RuntimeException("Assignment not found with " + id);
		}
	}
	
	@PutMapping("/course/assignment/{id}")
	@Transactional
	public Assignment updateAssignment(@RequestBody Assignment assignment) { 
		return assignmentRepository.save(assignment);
	}
}
