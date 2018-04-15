package boyi.battleship.core.gameeventservice.impl;

import boyi.battleship.core.chat.Chat;
import boyi.battleship.core.chat.ChatMessage;
import boyi.battleship.core.game.Game;
import boyi.battleship.core.game.GameEventHistory;
import boyi.battleship.core.gameevent.GameEvent;
import boyi.battleship.core.gameeventservice.GameEventService;
import boyi.battleship.core.player.Player;
import boyi.battleship.core.playerspecific.chat.PlayerSpecificChatMessageGenerator;
import boyi.battleship.core.playerspecific.gameevent.PlayerSpecificGameEvent;
import boyi.battleship.core.playerspecific.gameevent.PlayerSpecificGameEventGenerator;
import boyi.battleship.core.store.ChatMessageStore;
import boyi.battleship.core.store.ChatStore;
import boyi.battleship.core.store.GameEventHistoryStore;
import boyi.battleship.server.config.WebSocketConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("gameEventService")
public class GameEventServiceImpl implements GameEventService {
    private final SimpMessagingTemplate template;
    private final GameEventHistoryStore gameEventHistoryStore;
    private final ChatStore chatStore;
    private final ChatMessageStore chatMessageStore;
    private final PlayerSpecificGameEventGenerator playerSpecificGameEventGenerator;
    private final PlayerSpecificChatMessageGenerator playerSpecificChatMessageGenerator;

    @Autowired
    public GameEventServiceImpl(@NotNull SimpMessagingTemplate template,
                                @NotNull GameEventHistoryStore gameEventHistoryStore,
                                @NotNull ChatStore chatStore,
                                @NotNull ChatMessageStore chatMessageStore,
                                @NotNull PlayerSpecificGameEventGenerator playerSpecificGameEventGenerator,
                                @NotNull PlayerSpecificChatMessageGenerator playerSpecificChatMessageGenerator) {
        this.template = template;
        this.gameEventHistoryStore = gameEventHistoryStore;
        this.chatStore = chatStore;
        this.chatMessageStore = chatMessageStore;
        this.playerSpecificGameEventGenerator = playerSpecificGameEventGenerator;
        this.playerSpecificChatMessageGenerator = playerSpecificChatMessageGenerator;
    }

    @Override
    public void saveGameEvent(@NotNull GameEvent gameEvent) {
        Player initiator = gameEvent.getInitiator();
        Game game = initiator.getGame();
        gameEventHistoryStore.getOrCreateFor(game, new GameEventHistory()).add(gameEvent);
        getAllPlayersAndSpectatorsWithoutInitiatorFor(game, initiator).forEach((player) -> template.convertAndSend(
                WebSocketConfig.MAIN_WEBSOCKET_URL + "/" + player.getToken(),
                playerSpecificGameEventGenerator.generate(gameEvent, player)
        ));
    }

    @NotNull
    @Override
    public ChatMessage sendChatMessage(@NotNull Player sender, @NotNull String message, @NotNull Game game) {
        ChatMessage chatMessage = chatMessageStore.register(new ChatMessage(sender, message));

        chatStore.getOrCreateFor(game, new Chat()).addMessage(chatMessage);
        getAllPlayersAndSpectatorsWithoutInitiatorFor(game, sender).forEach(
                player -> playerSpecificChatMessageGenerator.generate(chatMessage, player).ifPresent(
                        playerSpecificChatMessage -> template.convertAndSend(
                                WebSocketConfig.CHAT_WEBSOCKET_URL + "/" + player.getToken(),
                                playerSpecificChatMessage
                        )
                )
        );

        return chatMessage;
    }

    @NotNull
    @Override
    public List<PlayerSpecificGameEvent> getHistoryFor(@NotNull Player player) {
        return gameEventHistoryStore
                .getOrCreateFor(player.getGame(), new GameEventHistory())
                .getGameEvents()
                .stream()
                .map(gameEvent -> playerSpecificGameEventGenerator.generate(gameEvent, player))
                .collect(Collectors.toList());
    }

    private Stream<Player> getAllPlayersAndSpectatorsWithoutInitiatorFor(@NotNull Game game,
                                                                         @NotNull Player initiator) {
        return game.getAllPlayersAndSpectators()
                .stream()
                .filter(player -> player != initiator);
    }
}
