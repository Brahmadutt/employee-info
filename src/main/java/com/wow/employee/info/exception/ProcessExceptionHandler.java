package com.wow.employee.info.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wow.employee.info.models.Response;


@RestControllerAdvice
public class ProcessExceptionHandler {

    private final MessageSource messageSource;

    public ProcessExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ProcessException.class)
    public ResponseEntity<Response> handleMongoDuplicateKeyException(ProcessException ex) {
        String responseCode = messageSource.getMessage(ex.getMessageCode()+".code", null, null);
        String responseMessage = messageSource.getMessage(ex.getMessageCode()+".message", null, null);

        Response errorResponse = new Response(responseCode, responseMessage);
        return ResponseEntity.status(HttpStatus.valueOf(Integer.parseInt(responseCode))).body(errorResponse);
    }
}