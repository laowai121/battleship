package boyi.battleship.server.validators.impl;

import boyi.battleship.core.keygenerator.KeyGenerator;
import boyi.battleship.server.validators.GameKeyValidator;
import boyi.battleship.server.validators.ValidationResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("gameKeyValidator")
public class GameKeyValidatorImpl implements GameKeyValidator {
    public static final String ENTER_GAME_KEY = "Please, enter Game Key";
    public static final String INVALID_GAME_KEY = "Invalid Game Key. "
            + "Game Key should be a sequence of hexadecimal digits "
            + KeyGenerator.DEFAULT_KEY_LENGTH;
    public static final String GAME_KEY_VALID = "Game Key is valid";

    @NotNull
    @Override
    public ValidationResult validate(@NotNull String gameKey) {
        if (gameKey.isEmpty()) {
            return new ValidationResult(false, ENTER_GAME_KEY);
        }

        if (gameKey.length() != KeyGenerator.DEFAULT_KEY_LENGTH
                || gameKey.chars()
                          .mapToObj((n) -> (char) n)
                          .anyMatch((n) -> KeyGenerator.KEY_CHARS.indexOf(n) < 0)) {
            return new ValidationResult(false, INVALID_GAME_KEY);
        }

        return new ValidationResult(true, GAME_KEY_VALID);
    }
}