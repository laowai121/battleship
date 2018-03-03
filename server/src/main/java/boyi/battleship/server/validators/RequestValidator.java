package boyi.battleship.server.validators;

import boyi.battleship.core.game.Game;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("requestValidator")
public class RequestValidator {
    public static final String INVALID_MAX_SPECTATORS = "max spectators should be between "
            + 0 + " and " + Game.SPECTATORS_MAX + " (inclusive)";
    public static final String CANT_CREATE_GAME_WITHOUT_SPECTATORS_AS_SPECTATOR =
            "you can't create a game that doesn't accept spectators "
            + "and join as spectator at the same time";

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
                return new ValidationResult(false, INVALID_MAX_SPECTATORS);
            } else if (maxSpectators == 0 && joinAsSpectator) {
                return new ValidationResult(false, CANT_CREATE_GAME_WITHOUT_SPECTATORS_AS_SPECTATOR);
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
