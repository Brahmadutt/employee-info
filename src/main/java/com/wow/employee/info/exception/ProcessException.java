package com.wow.employee.info.exception;

public class ProcessException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -4016927749080276497L;
    private final String messageCode;

    public ProcessException(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }
}