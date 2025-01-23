package git.matheusoliveira04.api.store.controllers.exceptions;

import git.matheusoliveira04.api.store.services.excepitions.ApiClientException;
import git.matheusoliveira04.api.store.services.excepitions.IntegrityViolationException;
import git.matheusoliveira04.api.store.services.excepitions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> getObjectNotFoundException(ObjectNotFoundException exception, HttpServletRequest request) {
      StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), request.getRequestURI(), Collections.singletonList(exception.getMessage()));
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IntegrityViolationException.class)
    public ResponseEntity<StandardError> getIntegrityViolationException(IntegrityViolationException exception, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), Collections.singletonList(exception.getMessage()));
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> getMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI(),
                                                this.buildMessageOfMethodArgumentNotValidException(exception));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> getDataIntegrityViolationException(DataIntegrityViolationException exception, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI(),
                Collections.singletonList(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ApiClientException.class)
    public ResponseEntity<StandardError> getApiClientException(ApiClientException exception, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI(),
                Collections.singletonList(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private List<String> buildMessageOfMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> fieldsAndMessages = new HashMap<>();
        List<String> buildMessages = new ArrayList<>();

        exception.getAllErrors().forEach((exceptionIndex) -> fieldsAndMessages.put(((FieldError) exceptionIndex).getField(), exceptionIndex.getDefaultMessage()));
        fieldsAndMessages.forEach((field, message) -> buildMessages.add(field + ": " + message));
        return buildMessages;
    }

}
