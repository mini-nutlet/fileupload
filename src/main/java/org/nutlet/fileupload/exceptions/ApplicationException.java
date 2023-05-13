package org.nutlet.fileupload.exceptions;

/**
 * 自定义应用异常
 */
public class ApplicationException extends Exception{
    public ApplicationException() {
        this("unknown exception");
    }

    public ApplicationException(String message) {
        super(message);
    }
}
