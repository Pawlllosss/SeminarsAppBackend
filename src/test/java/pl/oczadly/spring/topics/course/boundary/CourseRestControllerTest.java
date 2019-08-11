package pl.oczadly.spring.topics.course.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.oczadly.spring.topics.TopicsApplication;
import pl.oczadly.spring.topics.course.control.CourseService;
import pl.oczadly.spring.topics.course.entity.Course;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TopicsApplication.class)
@AutoConfigureMockMvc
public class CourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private ObjectMapper objectMapper;

    public CourseRestControllerTest() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void whenGetAllCoursesThenReturnAllCoursesAsJson() throws Exception {
        final String courseName1 = "Inżynieria oprogramowania";
        final String courseName2 = "Programowanie niskopoziomowe";
        final String courseName3 = "Sieci komputerowe";

        List<Course> courses = List.of(
                new Course(courseName1),
                new Course(courseName2),
                new Course(courseName3)
        );

        given(courseService.getAllCourses()).willReturn(courses);

        mockMvc.perform(get("/course").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courses", hasSize(3)))
                .andExpect(jsonPath("$._embedded.courses[0].name", is(courseName1)))
                .andExpect(jsonPath("$._embedded.courses[1].name", is(courseName2)))
                .andExpect(jsonPath("$._embedded.courses[2].name", is(courseName3)));

        verify(courseService, times(1)).getAllCourses();
    }

    @WithMockUser(authorities = "CRUD_ALL_COURSES")
    @Test
    public void whenCreateCourseThenReturnCreatedCourse() throws Exception {
        final String courseName = "Programowanie niskopoziomowe";
        Course course = new Course(courseName);

        given(courseService.createCourse(any())).willReturn(course);

        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(course)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(courseName)));

        verify(courseService, times(1)).createCourse(any());
    }
}