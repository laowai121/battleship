package boyi.battleship.server.validators;

import org.jetbrains.annotations.NotNull;

public interface GameKeyValidator {
    @NotNull
    ValidationResult validate(@NotNull String gameKey);
}
