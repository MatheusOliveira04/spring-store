package git.matheusoliveira04.api.store.services.validations;

import java.util.List;

public class ValidationChain<T> {

    private final List<ValidationRule<T>> validationRules;

    public ValidationChain(List<ValidationRule<T>> validationRules) {
        this.validationRules = validationRules;
    }

    public void execute(T object) {
        validationRules.forEach(rule -> rule.validate(object));
    }

}
