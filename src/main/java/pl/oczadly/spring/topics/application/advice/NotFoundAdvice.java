package pl.oczadly.spring.topics.application.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.oczadly.spring.topics.application.error.ErrorDTO;
import pl.oczadly.spring.topics.application.exception.NotFoundException;

@ControllerAdvice
public class NotFoundAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    ErrorDTO notFoundExceptionHandler(NotFoundException exception) {
        return new ErrorDTO(exception.getMessage());
    }
}