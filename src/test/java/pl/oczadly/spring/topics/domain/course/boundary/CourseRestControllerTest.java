package pl.oczadly.spring.topics.domain.course.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.oczadly.spring.topics.annotation.WithMockCrudAllCoursesAuthority;
import pl.oczadly.spring.topics.domain.course.control.CourseService;
import pl.oczadly.spring.topics.domain.course.entity.Course;
import pl.oczadly.spring.topics.domain.course.entity.dto.CourseDTO;

import java.time.LocalDateTime;
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

    private static final String COURSE_NAME_1 = "Inżynieria oprogramowania";
    private static final String COURSE_NAME_2 = "Programowanie niskopoziomowe";
    private static final String COURSE_NAME_3 = "Sieci komputerowe";
    private static final LocalDateTime COURSE_END_DATE = LocalDateTime.now().plusYears(1);

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
                Course.builder()
                    .name(COURSE_NAME_1)
                    .isActive(true)
                    .voteEndDate(COURSE_END_DATE)
                    .build(),
                Course.builder()
                    .name(COURSE_NAME_2)
                    .isActive(true)
                    .voteEndDate(COURSE_END_DATE)
                    .build(),
              Course.builder()
                    .name(COURSE_NAME_3)
                    .isActive(true)
                    .voteEndDate(COURSE_END_DATE)
                    .build()
        );

        given(courseService.getAllCourses()).willReturn(courses);
    }

    private void verifyGetAllCoursesCalledOnce() {
        verify(courseService, times(1)).getAllCourses();
    }

    //TODO: find proper way to send content
    @Test
    @Disabled
    @WithMockCrudAllCoursesAuthority
    public void whenCreateCourseThenReturnCreatedCourse() throws Exception {
        final String courseName = COURSE_NAME_1;
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(COURSE_NAME_1);
        courseDTO.setVoteEndDate(COURSE_END_DATE);
        Course course = Course.builder()
                            .name(COURSE_NAME_1)
                            .isActive(true)
                            .voteEndDate(COURSE_END_DATE)
                            .build();
        given(courseService.createCourse(any())).willReturn(course);

        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(courseDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(courseName)));

        verify(courseService, times(1)).createCourse(any());
    }
}