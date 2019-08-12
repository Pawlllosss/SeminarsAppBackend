package pl.oczadly.spring.topics.annotation;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(authorities = "CRUD_ALL_COURSES")
public @interface WithMockCrudAllCoursesAuthority {
}
