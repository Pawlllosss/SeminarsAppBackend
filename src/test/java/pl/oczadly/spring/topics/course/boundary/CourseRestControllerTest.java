package pl.oczadly.spring.topics.course.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.oczadly.spring.topics.annotation.WithMockCrudAllCoursesAuthority;
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

@ExtendWith(MockitoExtension.class)
public class CourseRestControllerTest {

    private static final String COURSE_NAME_2 = "Programowanie niskopoziomowe";
    private static final String COURSE_NAME_1 = "Inżynieria oprogramowania";
    private static final String COURSE_NAME_3 = "Sieci komputerowe";

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseRestController courseRestController;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(courseRestController).build();

        courseRestController.setCourseResourceAssembler(new CourseResourceAssembler());
    }

    @Test
    public void whenGetAllCoursesThenReturnAllCoursesAsJson() throws Exception {
        mockGetAllCourses();

        mockMvc.perform(get("/course").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].name", is(COURSE_NAME_1)))
                .andExpect(jsonPath("$.content[1].name", is(COURSE_NAME_2)))
                .andExpect(jsonPath("$.content[2].name", is(COURSE_NAME_3)));

        verifyGetAllCoursesCalledOnce();
    }

    private void mockGetAllCourses() {
        List<Course> courses = List.of(
                new Course(COURSE_NAME_1),
                new Course(COURSE_NAME_2),
                new Course(COURSE_NAME_3)
        );

        given(courseService.getAllCourses()).willReturn(courses);
    }

    private void verifyGetAllCoursesCalledOnce() {
        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    @WithMockCrudAllCoursesAuthority
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