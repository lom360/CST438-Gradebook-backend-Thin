package com.cst438.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentListDTO;
import com.cst438.domain.AssignmentListDTO.AssignmentDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.GradebookDTO;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class AssignmentController {
	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	AssignmentGradeRepository assignmentGradeRepository;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
		
	@GetMapping("course/{id}/assignment")
	public AssignmentListDTO getCourseAssignments(@PathVariable("id") int id) {
		List<Assignment> assignments = assignmentRepository.findAssignmentByCourseId(id);
		Course c = courseRepository.findById(id).get();
		if(c == null) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Course does not exist. Therefore no assignments.");
		}
		AssignmentListDTO result = new AssignmentListDTO();
		for (Assignment a: assignments) {
			result.assignments.add(new AssignmentListDTO.AssignmentDTO(a.getId(), a.getCourse().getCourse_id(), a.getName(), a.getDueDate().toString() , a.getCourse().getTitle()));
		}
		return result;
	}
	
	@GetMapping("course/{c_id}/assignment/{a_id}")
	public AssignmentListDTO.AssignmentDTO getAssignemnt(@PathVariable("c_id") int c_id, @PathVariable("a_id") int a_id) {
		Assignment a = assignmentRepository.findById(a_id).get();
		if(a.getCourse().getCourse_id() != c_id) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment does not exist in Course");
		}
//		if (a == null) {
//			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment not found. ");
//		}
		
		String date = a.getDueDate().toString();
		AssignmentDTO assignmentDTO = new AssignmentDTO(a.getId(), a.getCourse().getCourse_id(), a.getName(), date,a.getCourse().getTitle());
		
		return assignmentDTO;
	}
	
	@PostMapping("course/{id}/assignment")
	public AssignmentListDTO.AssignmentDTO addAssignment(@PathVariable("id") int courseId, @RequestBody AssignmentListDTO.AssignmentDTO assignmentDTO) throws ParseException {
		
		// lookup the course
//		Course c = courseRepository.findById(assignmentDTO.courseId).get();
		Course c = courseRepository.findById(courseId).get();
		if(c == null) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment not found. "+assignmentDTO.courseId );
		}
		
		// create a new assignment entity
		Assignment assignment = new Assignment();
		
		// copy data from assignmentDTO to assignment
		assignment.setName(assignmentDTO.assignmentName);
		
		// TODO convert dueDate String to dueDate java.sql.Date
//		 assignment.setDueDate(assignmentDTO.dueDate);
		SimpleDateFormat dateConversion = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = dateConversion.parse(assignmentDTO.dueDate);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		assignment.setDueDate(sqlDate);
		
		assignment.setCourse(c);
		assignment.setNeedsGrading(1);
		// save the assignment entity, save returns an updated assignment entity with assignment id primary key
		Assignment newAssignment = assignmentRepository.save(assignment);
		
		assignmentDTO.assignmentId = newAssignment.getId();
		
		// return assignmentDTO that now contains the primary key.
		return assignmentDTO;
	}
	
	
	@PutMapping("/course/{c_id}/assignment/{a_id}")
	@Transactional
	public void updateAssignment(@PathVariable("c_id") int c_id, @PathVariable("a_id") int a_id, @RequestBody AssignmentListDTO.AssignmentDTO assignmentDTO) throws ParseException { 
		Assignment assignment = assignmentRepository.findById(a_id).get();
		if(assignment.getCourse().getCourse_id() != c_id) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment does not exist in Course");
		}
//		if (a == null) {
//			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment not found. ");
//		}
		SimpleDateFormat dateConversion = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = dateConversion.parse(assignmentDTO.dueDate);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		assignment.setName(assignmentDTO.assignmentName);
		assignment.setDueDate(sqlDate);
	}
	
	@DeleteMapping("/course/{c_id}/assignment/{a_id}")
	public String deleteAssignment(@PathVariable int c_id, @PathVariable int a_id) {
		Optional<Assignment> assignment = assignmentRepository.findById(a_id);
		Assignment a = assignmentRepository.findById(a_id).get();
		if(a.getCourse().getCourse_id() != c_id) {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment does not exist in Course");
		}

		if(assignment.isPresent()) {
			assignmentRepository.delete(assignment.get());
			return "Assignment with " + a_id + " has been deleted.";
		}
		else {
			throw new RuntimeException("Assignment not found with " + a_id);
		}
	}
	
}
