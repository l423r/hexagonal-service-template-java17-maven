package ru.nota.hexagonal.template.exception;

public class NotFoundException extends ClientException {
    public NotFoundException() {
        super(404, "exception.not-found");
    }

    public NotFoundException(String message) {
        super(404, message);
    }
}
