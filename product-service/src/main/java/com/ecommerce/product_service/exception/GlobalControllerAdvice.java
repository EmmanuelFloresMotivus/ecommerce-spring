package com.ecommerce.product_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        problemDetail.setTitle("Recurso no encontrado");
        problemDetail.setType(URI.create("http://api.ecommerce.com/errors/not-found"));

        problemDetail.setProperty("Timestamp", Instant.now());
        problemDetail.setProperty("Resource", ex.getResourceName());
        problemDetail.setProperty("Field", ex.getFieldName());
        problemDetail.setProperty("Value", ex.getFieldValue());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request ){
        log.warn("Error de validación: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "La validacion fallo en uno o mas campos"
                );

        problemDetail.setTitle("Error de Validacion");
        problemDetail.setType(URI.create("http://api.ecommerce.com/errors/error-validation"));
        problemDetail.setProperty("Timestamp", Instant.now());

        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> {
                    errorMap.put(error.getField(), error.getDefaultMessage());
                }
        );

        problemDetail.setProperty("errors", errorMap);

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex, WebRequest request){
        log.error("Error inesperado: ", ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ha ocurrido un error inesperado, Por favor contactar al administrador"
        );

        problemDetail.setTitle("Internal server Error");
        problemDetail.setType(URI.create("http://api.ecommerce.com/errors/internal"));
        problemDetail.setProperty("Timestamp", Instant.now());

        return problemDetail;
    }


}
