package webserver.http;

/**
 * Tarayıcıdan gelen istek HTTP kurallarına uymuyorsa fırlatılır.
 */
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }
}
