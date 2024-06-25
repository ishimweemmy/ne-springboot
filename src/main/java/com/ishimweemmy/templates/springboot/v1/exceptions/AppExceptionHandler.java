package com.ishimweemmy.templates.springboot.v1.exceptions;

import com.ishimweemmy.templates.springboot.v1.payload.response.ApiResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@ControllerAdvice
public class AppExceptionHandler {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AppFailureException.class);

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleAppException(AppException exception, WebRequest request) {
        logger.error("AppException: ", exception);
        return new ResponseEntity<>(ApiResponse.error(exception.getMessage(), exception), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException exception, WebRequest request) {
        logger.error("BadRequestException: ", exception);
        return new ResponseEntity<>(ApiResponse.error(exception.getMessage(), exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<ApiResponse> handleInvalidFileException(InvalidFileException exception, WebRequest request) {
        logger.error("InvalidFileException: ", exception);
        return new ResponseEntity<>(ApiResponse.error(exception.getMessage(), exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        logger.error("ResourceNotFoundException: ", exception);
        return new ResponseEntity<>(ApiResponse.error(exception.getMessage(), exception), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidations(MethodArgumentNotValidException exception) {
        FieldError error = Objects.requireNonNull(exception.getFieldError());
        String message = error.getField() + ": " + error.getDefaultMessage();
        logger.error("MethodArgumentNotValidException: ", exception);
        return ResponseEntity.badRequest().body(ApiResponse.error(message, error));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> handleSqlExceptions(ConstraintViolationException exception) {
        logger.error("ConstraintViolationException: ", exception);
        return ResponseEntity.badRequest().body(ApiResponse.error(exception.getMessage() + " - " + exception.getSQL() + " - " + exception.getSQLState(), exception.getSQLException()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleAnyError(RuntimeException exception) {
        logger.error("RuntimeException: ", exception);
        return ResponseEntity.badRequest().body(ApiResponse.error(exception.getMessage(), exception));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllExceptions(Exception exception, WebRequest request) {
        logger.error("Unhandled Exception: ", exception);
        return new ResponseEntity<>(ApiResponse.error("An unexpected error occurred", exception), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
