package ru.nota.hexagonal.template.adapter.in.rest.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.nota.hexagonal.template.exception.InternalServerException;
import ru.nota.hexagonal.template.exception.NotFoundException;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        final var result = ex.getBindingResult();
        final var fieldErrors = result.getFieldErrors();
        final var errorDescription = fieldErrors
            .stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
            .entrySet()
            .stream()
            .map(entry -> entry.getKey() + " " + entry.getValue())
            .collect(Collectors.joining(";"));
        return new ResponseEntity<>(errorDescription, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e,
                                                                     HttpServletRequest request) {
        log.error(
            "Ошибка констреинта валидации: {} {} uri: {}",
            e.getClass().getSimpleName(), e.getMessage(), request.getRequestURI()
        );
        log.error(e.getMessage(), e);
        final var fieldErrors = e.getConstraintViolations();
        final var errorDescription = fieldErrors
            .stream()
            .collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage))
            .entrySet()
            .stream()
            .map(entry -> entry.getKey() + " " + entry.getValue())
            .collect(Collectors.joining("; "));

        return new ResponseEntity<>(errorDescription, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class, InternalServerException.class})
    public ResponseEntity<Object> handleBaseException(RuntimeException e, HttpServletRequest request) {
        log.error(
            "Запрос к {}. Ошибка {}",
            request.getRequestURI(), e.getClass().getSimpleName()
        );
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        log.error(
            "Запрос к {}. Сообщение {}",
            request.getRequestURI(), e.getMessage()
        );
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
