package boyi.battleship.server.validators;

import org.jetbrains.annotations.NotNull;

public interface RequestValidator {
    @NotNull
    ValidationResult validateCreateGameRequest(
            @NotNull String playerName,
            int maxSpectators,
            boolean joinAsSpectator
    );

    @NotNull
    ValidationResult validateJoinGameRequest(
            @NotNull String playerName,
            @NotNull String gameKey
    );
}
