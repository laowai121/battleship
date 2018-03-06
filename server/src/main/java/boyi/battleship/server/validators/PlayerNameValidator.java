package boyi.battleship.server.validators;

import org.jetbrains.annotations.NotNull;

public interface PlayerNameValidator {
    @NotNull
    ValidationResult validate(@NotNull String playerName);
}
