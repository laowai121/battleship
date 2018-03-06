package boyi.battleship.server.validators.impl;

import boyi.battleship.core.game.Game;
import boyi.battleship.server.validators.GameKeyValidator;
import boyi.battleship.server.validators.PlayerNameValidator;
import boyi.battleship.server.validators.RequestValidator;
import boyi.battleship.server.validators.ValidationResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("requestValidator")
public class RequestValidatorImpl implements RequestValidator {
    public static final String INVALID_MAX_SPECTATORS = "max spectators should be between "
            + 0 + " and " + Game.SPECTATORS_MAX + " (inclusive)";
    public static final String CANT_CREATE_GAME_WITHOUT_SPECTATORS_AS_SPECTATOR =
            "you can't create a game that doesn't accept spectators "
            + "and join as spectator at the same time";

    private final PlayerNameValidator playerNameValidator;
    private final GameKeyValidator gameKeyValidator;

    @Autowired
    public RequestValidatorImpl(@NotNull PlayerNameValidator playerNameValidator,
                                @NotNull GameKeyValidator gameKeyValidator) {
        this.playerNameValidator = playerNameValidator;
        this.gameKeyValidator = gameKeyValidator;
    }

    @NotNull
    @Override
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
    @Override
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
