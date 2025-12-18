package org.example.lesson_2.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex,
                                                        HttpServletRequest request) {
        String message = String.format(
                "Validation failed for object '%s': %s",
                ex.getBindingResult().getObjectName(),
                ex.getBindingResult().getFieldErrors().stream()
                        .map(err -> String.format("Field '%s' %s", err.getField(), err.getDefaultMessage()))
                        .collect(Collectors.joining(", "))
        );

        return buildProblem(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex,
                                                HttpServletRequest request) {
        return buildProblem(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                     HttpServletRequest request) {
        String message = String.format(
                "Method parameter '%s': %s",
                ex.getName(),
                ex.getMostSpecificCause().getMessage()
        );
        return buildProblem(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, HttpServletRequest request) {
        return buildProblem(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    private ResponseEntity<Object> buildProblem(HttpStatus status, String detail, HttpServletRequest request) {
        String path = request.getRequestURI();

        // RFC 9457 / Problem Details fields
        Map<String, Object> body = new HashMap<>();
        body.put("type", "about:blank");
        body.put("title", status.getReasonPhrase());
        body.put("status", status.value());
        body.put("detail", detail);
        body.put("instance", path);

        // Aliases that match the structure from the lab description
        body.put("error", status.getReasonPhrase());
        body.put("message", detail);
        body.put("path", path);

        return new ResponseEntity<>(body, status);
    }
}
