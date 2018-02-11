package boyi.battleship.server.validators;

import boyi.battleship.server.keygenerator.KeyGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("gameKeyValidator")
public class GameKeyValidator {
    @NotNull
    public ValidationResult validate(@NotNull String gameKey) {
        if (gameKey.isEmpty()) {
            return new ValidationResult(false, "Please, enter Game Key");
        }

        if (gameKey.length() != KeyGenerator.KEY_LENGTH
                || gameKey.chars().mapToObj((n) -> (char) n).anyMatch((n) -> KeyGenerator.KEY_CHARS.indexOf(n) < 0)) {
            return new ValidationResult(false,
                    "Invalid Game Key. Game Key should be a sequence of hexadecimal digits " + KeyGenerator.KEY_LENGTH);
        }

        return new ValidationResult(true, "Game Key is valid");
    }
}