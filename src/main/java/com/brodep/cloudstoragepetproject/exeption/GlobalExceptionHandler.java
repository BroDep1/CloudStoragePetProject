package com.brodep.cloudstoragepetproject.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    public record AppError(int statusCode, String message){}

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public AppError handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppError handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public AppError handleUnauthorizedException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return new AppError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler(UnknownException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public AppError handleUnknownException(Exception e) {
        log.error(e.getMessage(), e);
        return new AppError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(UsernameIsUnavailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public AppError handleUsernameIsUnavailableException(UsernameIsUnavailableException e) {
        log.error(e.getMessage(), e);
        return new AppError(HttpStatus.CONFLICT.value(), e.getMessage());
    }

}
