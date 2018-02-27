package boyi.battleship.server.response;

import boyi.battleship.core.playerspecific.chat.PlayerSpecificChatMessage;
import boyi.battleship.core.playerspecific.gameevent.PlayerSpecificGameEvent;
import boyi.battleship.core.playerspecific.gamestate.PlayerSpecificGameState;
import boyi.battleship.core.chat.ChatMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("responseBuilder")
public class ResponseBuilder {
    private static final String ERROR_MESSAGE = "errorMessage";

    private static final String PLAYER_TOKEN = "playerToken";
    private static final String PLAYER_ID = "playerId";
    private static final String GAME_KEY = "gameKey";
    private static final String GAME_STATE = "gameState";
    private static final String CHAT_HISTORY = "chatHistory";
    private static final String EVENT_HISTORY = "eventHistory";
    private static final String MESSAGE_ID = "messageId";

    @NotNull
    public BattleshipResponse build(boolean success) {
        return new BattleshipResponse(success);
    }

    @NotNull
    public BattleshipResponse buildErrorResponse(@NotNull String errorMessage) {
        return build(false).setProperty(ERROR_MESSAGE, errorMessage);
    }

    @NotNull
    public BattleshipResponse buildJoinGameResponse(@NotNull String playerToken,
                                                    @NotNull String playerId,
                                                    @NotNull String gameKey) {
        return build(true)
                .setProperty(PLAYER_TOKEN, playerToken)
                .setProperty(PLAYER_ID, playerId)
                .setProperty(GAME_KEY, gameKey);
    }

    @NotNull
    public BattleshipResponse buildGameStateResponse(@NotNull PlayerSpecificGameState gameState) {
        return build(true).setProperty(GAME_STATE, gameState);
    }

    @NotNull
    public BattleshipResponse buildChatHistoryResponse(@NotNull List<PlayerSpecificChatMessage> chatHistory) {
        return build(true).setProperty(CHAT_HISTORY, chatHistory);
    }

    @NotNull
    public BattleshipResponse buildEventHistoryResponse(@NotNull List<PlayerSpecificGameEvent> gameEvents) {
        return build(true).setProperty(EVENT_HISTORY, gameEvents);
    }

    @NotNull
    public BattleshipResponse buildSendChatMessageResponse(@NotNull String messageId) {
        return build(true).setProperty(MESSAGE_ID, messageId);
    }
}
