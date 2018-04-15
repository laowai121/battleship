package boyi.battleship.server.validators;

import org.jetbrains.annotations.NotNull;

public interface ChatMessageValidator {
    @NotNull
    ValidationResult validate(@NotNull String message);
}
