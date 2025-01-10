package git.matheusoliveira04.api.store.services;

public interface ValidationRule<T> {
    void validate(T object);
}
