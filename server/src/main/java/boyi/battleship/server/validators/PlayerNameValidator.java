package boyi.battleship.server.validators;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("playerNameValidator")
public class PlayerNameValidator {
    private static final int MAX_LENGTH = 20;

    @NotNull
    public ValidationResult validate(@NotNull String playerName) {
        if (playerName.isEmpty()) {
            return new ValidationResult(false, "Please, enter Player Name");
        }

        if (playerName.length() > MAX_LENGTH) {
            return new ValidationResult(false, "Player Name is too long (maximum length: " + MAX_LENGTH + ")");
        }

        return new ValidationResult(true, "Player Name is valid");
    }
}
