package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.exceptions.BindingResultException;
import ar.com.saile.demojwt.exceptions.ErrorResponse;
import ar.com.saile.demojwt.exceptions.MissingHeaderInfoException;
import ar.com.saile.demojwt.exceptions.RecordNotFoundException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
            (RecordNotFoundException ex, WebRequest request) {

        return errorResponseResponseEntity(NOT_FOUND.getReasonPhrase(), ex, NOT_FOUND.ordinal(), NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        return errorResponseResponseEntity(BAD_REQUEST.getReasonPhrase(), ex, BAD_REQUEST.value(), BAD_REQUEST);
    }

    @ExceptionHandler(BindingResultException.class)
    protected ResponseEntity<ErrorResponse> handleBindingResultException(BindingResultException ex, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        Map<String, Object> tokens2 = new HashMap<>();
        var token2 = ex.getFieldErrors().stream().map(vale -> {
            tokens2.put("message", vale.getDefaultMessage());
            tokens2.put("code", vale.getCode());
            tokens2.put("field", vale.getField());
            tokens2.put("extra", vale.getObjectName());
            return tokens2;
        }).collect(Collectors.toList());

        tokens.put("errorMessage", token2);
        tokens.put("errorCode", BAD_REQUEST);
        ErrorResponse error = new ErrorResponse(BAD_REQUEST.getReasonPhrase(), tokens);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public final ResponseEntity<ErrorResponse> handleJWTVerificationException(JWTVerificationException ex, WebRequest webRequest) {

        return errorResponseResponseEntity(UNAUTHORIZED.getReasonPhrase(), ex, UNAUTHORIZED.ordinal(), UNAUTHORIZED);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public final ResponseEntity<ErrorResponse> handleJWTDecodeException(JWTDecodeException ex, WebRequest webRequest) {

        return errorResponseResponseEntity(FORBIDDEN.getReasonPhrase(), ex, FORBIDDEN.ordinal(), FORBIDDEN);
    }

    protected ResponseEntity<ErrorResponse> errorResponseResponseEntity(String cause, Exception ex, Integer code, HttpStatus status) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", ex.getLocalizedMessage());
        tokens.put("errorCode", code);
        ErrorResponse error = new ErrorResponse(cause, tokens);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(MissingHeaderInfoException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException
            (MissingHeaderInfoException ex, WebRequest request) {

        return errorResponseResponseEntity(BAD_REQUEST.getReasonPhrase(), ex, BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
    }
}
