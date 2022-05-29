package ar.com.saile.demojwt.controllers;

import ar.com.saile.demojwt.exceptions.*;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
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

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Method not supported");
        tokens.put("errorCode", METHOD_NOT_ALLOWED);
        ErrorResponse error = new ErrorResponse(METHOD_NOT_ALLOWED.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Media Type not supported");
        tokens.put("errorCode", UNSUPPORTED_MEDIA_TYPE);
        ErrorResponse error = new ErrorResponse(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Media Type not acceptable");
        tokens.put("errorCode", UNSUPPORTED_MEDIA_TYPE);
        ErrorResponse error = new ErrorResponse(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Missing path");
        tokens.put("errorCode", BAD_REQUEST);
        ErrorResponse error = new ErrorResponse(BAD_REQUEST.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Bad Request failed");
        tokens.put("errorCode", BAD_REQUEST);
        ErrorResponse error = new ErrorResponse(BAD_REQUEST.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Request failed");
        tokens.put("errorCode", BAD_REQUEST);
        ErrorResponse error = new ErrorResponse(BAD_REQUEST.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Conversion Not Supported");
        tokens.put("errorCode", UNSUPPORTED_MEDIA_TYPE);
        ErrorResponse error = new ErrorResponse(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Type Mismatch");
        tokens.put("errorCode", BAD_REQUEST);
        ErrorResponse error = new ErrorResponse(BAD_REQUEST.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Missing body");
        tokens.put("errorCode", BAD_REQUEST);
        ErrorResponse error = new ErrorResponse(BAD_REQUEST.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Not posible to write");
        tokens.put("errorCode", BAD_REQUEST);
        ErrorResponse error = new ErrorResponse(BAD_REQUEST.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Argument Not valid");
        tokens.put("errorCode", NOT_ACCEPTABLE);
        ErrorResponse error = new ErrorResponse(NOT_ACCEPTABLE.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Request Not valid");
        tokens.put("errorCode", BAD_REQUEST);
        ErrorResponse error = new ErrorResponse(BAD_REQUEST.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", " server error Internal");
        tokens.put("errorCode", BAD_GATEWAY);
        ErrorResponse error = new ErrorResponse(BAD_GATEWAY.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Internal server error");
        tokens.put("errorCode", BAD_GATEWAY);
        ErrorResponse error = new ErrorResponse(BAD_GATEWAY.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

        Map<String, Object> tokens = new HashMap<>();
        tokens.put("errorMessage", "Timeout Async");
        tokens.put("errorCode", REQUEST_TIMEOUT);
        ErrorResponse error = new ErrorResponse(BAD_GATEWAY.getReasonPhrase(), tokens);

        return this.handleExceptionInternal(ex, error, headers, status, webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

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
        var token2 = ex.getFieldErrors().stream().map(vale -> {
            Map<String, Object> tokens2 = new HashMap<>();
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

    @ExceptionHandler(NotAuthorizedException.class)
    public final ResponseEntity<ErrorResponse> handleNotAuthorizedException(NotAuthorizedException ex, WebRequest webRequest) {

        return errorResponseResponseEntity(UNAUTHORIZED.getReasonPhrase(), ex, UNAUTHORIZED.value(), UNAUTHORIZED);
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
