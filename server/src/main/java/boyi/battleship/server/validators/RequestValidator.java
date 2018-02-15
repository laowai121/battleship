package boyi.battleship.server.validators;

import boyi.battleship.server.game.Game;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("requestValidator")
public class RequestValidator {
    @Autowired
    private PlayerNameValidator playerNameValidator;

    @Autowired
    private GameKeyValidator gameKeyValidator;

    @NotNull
    public ValidationResult validateCreateGameRequest(@NotNull String playerName, int maxSpectators,
                                                      boolean joinAsSpectator) {
        ValidationResult validationResult = playerNameValidator.validate(playerName);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        if (maxSpectators != Game.UNLIMITED_SPECTATORS) {
            if (maxSpectators < 0 || maxSpectators > Game.SPECTATORS_MAX) {
                return new ValidationResult(false, "max spectators should be between "
                        + 0 + " and " + Game.SPECTATORS_MAX + " (inclusive)");
            } else if (maxSpectators == 0 && joinAsSpectator) {
                return new ValidationResult(false, "you can't create a game that doesn't accept spectators "
                        + "and join as spectator at the same time");
            }
        }

        return new ValidationResult(true);
    }

    @NotNull
    public ValidationResult validateJoinGameRequest(@NotNull String playerName, @NotNull String gameKey) {
        ValidationResult validationResult = playerNameValidator.validate(playerName);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        validationResult = gameKeyValidator.validate(gameKey);
        if (!validationResult.isValid()) {
            return validationResult;
        }

        return new ValidationResult(true);
    }
}
