package boyi.battleship.server.response;

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
    private static final String GAME_KEY = "gameKey";
    private static final String GAME_STATE = "gameState";
    private static final String CHAT_HISTORY = "chatHistory";
    private static final String EVENT_HISTORY = "eventHistory";
    private static final String MESSAGE_SEQUENCE_NUMBER = "messageSequenceNumber";

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

    @NotNull
    public BattleshipResponse buildChatHistoryResponse(@NotNull List<ChatMessage> chatHistory) {
        return build(true).setProperty(CHAT_HISTORY, chatHistory);
    }

    @NotNull
    public BattleshipResponse buildEventHistoryResponse(List<PlayerSpecificGameEvent> gameEvents) {
        return build(true).setProperty(EVENT_HISTORY, gameEvents);
    }

    @NotNull
    public BattleshipResponse buildSendChatMessageResponse(long sequenceNumber) {
        return build(true).setProperty(MESSAGE_SEQUENCE_NUMBER, sequenceNumber);
    }
}
