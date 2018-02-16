package boyi.battleship.server.response;

import boyi.battleship.server.PlayerSpecificGameState.PlayerSpecificGameState;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("responseBuilder")
public class ResponseBuilder {
    private static final String ERROR_MESSAGE = "errorMessage";

    private static final String PLAYER_TOKEN = "playerToken";
    private static final String GAME_KEY = "gameKey";
    private static final String GAME_STATE = "gameState";

    @NotNull
    public BattleshipResponse build(boolean success) {
        return new BattleshipResponse(success);
    }

    @NotNull
    public BattleshipResponse buildErrorResponse(String errorMessage) {
        return build(false).setProperty(ERROR_MESSAGE, errorMessage);
    }

    @NotNull
    public BattleshipResponse buildJoinGameResponse(@NotNull String playerToken, @NotNull String gameKey) {
        return build(true)
                .setProperty(PLAYER_TOKEN, playerToken)
                .setProperty(GAME_KEY, gameKey);
    }

    @NotNull
    public BattleshipResponse buildGameStateResponse(@NotNull PlayerSpecificGameState gameState) {
        return build(true).setProperty(GAME_STATE, gameState);
    }
}
