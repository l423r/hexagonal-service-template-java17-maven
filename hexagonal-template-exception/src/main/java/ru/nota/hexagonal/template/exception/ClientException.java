package ru.nota.hexagonal.template.exception;

public class ClientException extends RuntimeException {

    protected final int code;

    public ClientException(String message) {
        super(message);
        this.code = 400;
    }

    public ClientException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ClientException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ClientException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public ClientException(Throwable cause) {
        super(cause);
        this.code = 400;
    }

    public int getCode() {
        return code;
    }
}
