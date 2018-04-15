package boyi.battleship.server.validators;

import org.jetbrains.annotations.NotNull;

public class ValidationResult {
    public static final String VALID = "valid";

    private boolean valid;

    @NotNull
    private String message;

    public ValidationResult(boolean valid, @NotNull String message) {
        this.valid = valid;
        this.message = message;
    }

    public ValidationResult(boolean valid) {
        this(valid, VALID);
    }

    public boolean isValid() {
        return valid;
    }

    @NotNull
    public String getMessage() {
        return message;
    }
}
