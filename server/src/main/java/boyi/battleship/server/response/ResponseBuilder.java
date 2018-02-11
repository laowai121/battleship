package boyi.battleship.server.response;

import org.springframework.stereotype.Service;

@Service("responseBuilder")
public class ResponseBuilder {
    private static final String ERROR_MESSAGE = "errorMessage";

    private static final String PLAYER_TOKEN = "playerToken";
    private static final String GAME_KEY = "gameKey";

    public BattleshipResponse build(boolean success) {
        return new BattleshipResponse(success);
    }

    public BattleshipResponse buildErrorResponse(String errorMessage) {
        return build(false).setProperty(ERROR_MESSAGE, errorMessage);
    }

    public BattleshipResponse buildJoinGameResponse(String playerToken, String gameKey) {
        return build(true)
                .setProperty(PLAYER_TOKEN, playerToken)
                .setProperty(GAME_KEY, gameKey);
    }
}
