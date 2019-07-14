package pl.oczadly.spring.topics.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.oczadly.spring.topics.course.entity.CourseNotFoundException;
import pl.oczadly.spring.topics.error.ErrorDTO;

//TODO: create separate Advice for generic NotFound exception
@ControllerAdvice
public class CourseNotFoundAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CourseNotFoundException.class)
    ErrorDTO courseNotFoundHandler(CourseNotFoundException exception) {
        return new ErrorDTO(exception.getMessage());
    }
}
