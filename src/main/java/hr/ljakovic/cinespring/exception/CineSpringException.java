package hr.ljakovic.cinespring.exception;

public class CineSpringException extends RuntimeException{

    public CineSpringException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public CineSpringException(String message, Throwable cause) {
        super(message, cause);
    }
}
