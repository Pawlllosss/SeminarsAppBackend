package pl.oczadly.spring.topics.course.boundary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.oczadly.spring.topics.course.control.CourseService;
import pl.oczadly.spring.topics.course.entity.Course;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CourseRestController.class)
public class CourseRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    public void whenGetAllCoursesThenReturnAllCoursesAsJson() throws Exception {
        List<Course> courses = List.of(
                new Course("Inżynieria oprogramowania"),
                new Course("Programowanie niskopoziomowe"),
                new Course("Sieci komputerowe")
        );

        given(courseService.getAllCourses()).willReturn(courses);

        mockMvc.perform(get("/course").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.courses", hasSize(3)));


    }
}