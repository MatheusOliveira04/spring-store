package git.matheusoliveira04.api.store.services.validations;

public interface ValidationRule<T> {
    void validate(T object);
}
