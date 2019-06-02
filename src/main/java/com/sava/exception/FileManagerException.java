package com.sava.exception;

public class FileManagerException extends Exception {
    public FileManagerException(String message) {
        super(message);
    }

    public FileManagerException(Exception exception) {
        super(exception);
    }
}
