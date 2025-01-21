package git.matheusoliveira04.api.store.models.enums;

public enum Position {
    MANAGER(1, "Manager"),
    ASSISTANT(2, "Assistant");

    private Integer id;
    private String description;

    Position(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
}
