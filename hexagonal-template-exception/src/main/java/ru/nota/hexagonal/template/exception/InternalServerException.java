package ru.nota.hexagonal.template.exception;

public class InternalServerException extends RuntimeException {

    protected final int code;

    public InternalServerException(int code, String message) {
        super(message);
        this.code = code;
    }

    public InternalServerException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public InternalServerException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public InternalServerException(Throwable cause) {
        super(cause);
        this.code = 500;
    }

    public InternalServerException(String message) {
        super(message);
        this.code = 500;
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
    }

    public int getCode() {
        return code;
    }
}
