package com.cst438;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGrade;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentListDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.GradebookDTO;
import com.cst438.services.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestAddAssignment {
	static final String URL = "http://localhost:8080";
	public static final int TEST_COURSE_ID = 40442;

	@MockBean
	AssignmentRepository assignmentRepository;

	@MockBean
	AssignmentGradeRepository assignmentGradeRepository;

	@MockBean
	CourseRepository courseRepository; // must have this to keep Spring test happy

	@MockBean
	RegistrationService registrationService; // must have this to keep Spring test happy

	@Autowired
	private MockMvc mvc;

	@Test
	public void gradeAssignment() throws Exception {

		MockHttpServletResponse response;

		// mock database data

		Course course = new Course();
		course.setCourse_id(TEST_COURSE_ID);

		// given -- stubs for database repositories that return test data
		given(courseRepository.findById(TEST_COURSE_ID)).willReturn(Optional.of(course));
		// end of mock data

		// set up a mock for the assignment repository save method
		Assignment a = new Assignment();
		a.setId(123);
		given(courseRepository.findById(TEST_COURSE_ID)).willReturn(Optional.of(course));
		
		// then do an http get request for assignment 1
		AssignmentListDTO.AssignmentDTO aDTO = new AssignmentListDTO.AssignmentDTO();
		// setting values for name, courseId, dueDate
		aDTO.assignmentName  = "test assignment";
		aDTO.courseId = TEST_COURSE_ID;
		aDTO.dueDate = "2022-9-12";
//		response = mvc.perform(MockMvcRequestBuilders.post("/assignment").accept(MediaType.APPLICATION_JSON).content(asJsonString(aDTO)).contentType(MediaType.APPLICATION_JSON))

		// verify return data with entry for one student without no score
//		assertEquals(200, response.getStatus());
		
		// get response body and convert to Java object.
//		AssignmentListDTO.AssignmentDTO returnedDTO = fromJsonString(response.getContentAsString(), AssignmentDTO.class);
		
		// check that returned assignmentID is not 0.
//		assertNotEquals(0, returnedDTO.assignmentId);
		
		// verify that a save was called on repository
	}
	
	private static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static <T> T fromJsonString(String str, Class<T> valueType) {
		try {
			return new ObjectMapper().readValue(str, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
