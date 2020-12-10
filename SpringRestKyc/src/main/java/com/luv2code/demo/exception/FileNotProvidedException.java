package com.luv2code.demo.exception;

public class FileNotProvidedException extends RuntimeException {
    public FileNotProvidedException(String message) {
        super(message);
    }

    public FileNotProvidedException(String message, Throwable cause) {
        super(message, cause);
    }
}