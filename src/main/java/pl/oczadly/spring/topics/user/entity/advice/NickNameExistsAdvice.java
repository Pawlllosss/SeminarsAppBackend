package pl.oczadly.spring.topics.user.entity.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.oczadly.spring.topics.error.ErrorDTO;
import pl.oczadly.spring.topics.user.entity.exception.EmailExistsException;
import pl.oczadly.spring.topics.user.entity.exception.NickNameExistsException;

@ControllerAdvice
public class NickNameExistsAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NickNameExistsException.class)
    ErrorDTO nickNameExistsExceptionHandler(NickNameExistsException exception) {
        return new ErrorDTO(exception.getMessage());
    }
}
