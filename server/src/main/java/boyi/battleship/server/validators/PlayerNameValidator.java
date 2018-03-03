package boyi.battleship.server.validators;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("playerNameValidator")
public class PlayerNameValidator {
    public static final int MAX_LENGTH = 20;

    public static final String ENTER_PLAYER_NAME = "Please, enter Player Name";
    public static final String PLAYER_NAME_TOO_LONG = "Player Name is too long (maximum length: " + MAX_LENGTH + ")";
    public static final String PLAYER_NAME_VALID = "Player Name is valid";

    @NotNull
    public ValidationResult validate(@NotNull String playerName) {
        if (playerName.isEmpty()) {
            return new ValidationResult(false, ENTER_PLAYER_NAME);
        }

        if (playerName.length() > MAX_LENGTH) {
            return new ValidationResult(false, PLAYER_NAME_TOO_LONG);
        }

        return new ValidationResult(true, PLAYER_NAME_VALID);
    }
}
