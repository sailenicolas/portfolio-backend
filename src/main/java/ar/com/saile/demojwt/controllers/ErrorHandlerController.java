package ar.com.saile.demojwt.controllers;

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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    private String INCORRECT_REQUEST = "INCORRECT_REQUEST";

    private String BAD_REQUEST = "BAD_REQUEST";

    private String NO_AUTH = "NO_AUTH";

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException
            (RecordNotFoundException ex, WebRequest request) {

        Map<String, String> details = new HashMap<>();
        details.put("errorMessage", ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public final ResponseEntity<ErrorResponse> handleJWTVerificationException(JWTVerificationException ex, WebRequest webRequest) {

        Map<String, String> tokens = new HashMap<>();
        tokens.put("errorMessage", ex.getMessage());
        tokens.put("errorCode", String.valueOf(FORBIDDEN.value()));
        ErrorResponse error = new ErrorResponse(NO_AUTH, tokens);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public final ResponseEntity<ErrorResponse> handleJWTDecodeException(JWTDecodeException ex, WebRequest webRequest) {

        Map<String, String> tokens = new HashMap<>();
        tokens.put("errorMessage", ex.getMessage());
        tokens.put("errorCode", String.valueOf(FORBIDDEN.value()));
        ErrorResponse error = new ErrorResponse(NO_AUTH, tokens);
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MissingHeaderInfoException.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException
            (MissingHeaderInfoException ex, WebRequest request) {

        Map<String, String> details = new HashMap<>();
        details.put("errorMessage", ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
