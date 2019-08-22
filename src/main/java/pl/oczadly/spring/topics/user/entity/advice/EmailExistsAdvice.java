package pl.oczadly.spring.topics.user.entity.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.oczadly.spring.topics.error.ErrorDTO;
import pl.oczadly.spring.topics.user.entity.exception.EmailExistsException;

@ControllerAdvice
public class EmailExistsAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailExistsException.class)
    ErrorDTO emailExistsExceptionHandler(EmailExistsException exception) {
        return new ErrorDTO(exception.getMessage());
    }
}
