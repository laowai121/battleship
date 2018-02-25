package boyi.battleship.core.gameevent;

import boyi.battleship.core.chat.ChatMessage;
import boyi.battleship.core.game.Game;
import boyi.battleship.core.player.Player;
import boyi.battleship.core.playerspecific.gameevent.PlayerSpecificGameEvent;
import boyi.battleship.core.playerspecific.gameevent.PlayerSpecificGameEventGenerator;
import boyi.battleship.core.store.ChatStore;
import boyi.battleship.core.store.GameEventHistoryStore;
import boyi.battleship.server.config.WebSocketConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("gameEventService")
public class GameEventService {
    private final SimpMessagingTemplate template;
    private final GameEventHistoryStore gameEventHistoryStore;
    private final ChatStore chatStore;
    private final PlayerSpecificGameEventGenerator playerSpecificGameEventGenerator;

    @Autowired
    public GameEventService(@NotNull SimpMessagingTemplate template,
                            @NotNull GameEventHistoryStore gameEventHistoryStore,
                            @NotNull ChatStore chatStore,
                            @NotNull PlayerSpecificGameEventGenerator playerSpecificGameEventGenerator) {
        this.template = template;
        this.gameEventHistoryStore = gameEventHistoryStore;
        this.chatStore = chatStore;
        this.playerSpecificGameEventGenerator = playerSpecificGameEventGenerator;
    }

    public void saveGameEvent(@NotNull GameEvent gameEvent, @NotNull Game game) {
        gameEventHistoryStore.getOrCreateFor(game).add(gameEvent);
        game.getAllPlayersAndSpectators().forEach((player) -> {
            template.convertAndSend(WebSocketConfig.MAIN_WEBSOCKET_URL + "/" + player.getKey(),
                    playerSpecificGameEventGenerator.generate(gameEvent, player)
            );
        });
    }

    public long sendChatMessage(@NotNull String sender, @NotNull String message, @NotNull Game game) {
        ChatMessage chatMessage = chatStore.getOrCreateFor(game).addMessage(sender, message);
        game.getAllPlayersAndSpectators().forEach((player) -> {
            template.convertAndSend(WebSocketConfig.CHAT_WEBSOCKET_URL + "/" + player.getKey(), chatMessage);
        });
        return chatMessage.getSequenceNumber();
    }

    @NotNull
    public List<PlayerSpecificGameEvent> getHistoryFor(@NotNull Player player) {
        return gameEventHistoryStore.getOrCreateFor(player.getGame()).getGameEvents().stream()
                .map((gameEvent -> playerSpecificGameEventGenerator.generate(gameEvent, player)))
                .collect(Collectors.toList());
    }
}
