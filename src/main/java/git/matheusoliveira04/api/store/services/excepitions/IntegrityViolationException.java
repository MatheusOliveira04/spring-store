package git.matheusoliveira04.api.store.services.excepitions;

public class IntegrityViolationException extends RuntimeException{

    public IntegrityViolationException(String message) {
        super(message);
    }
}
