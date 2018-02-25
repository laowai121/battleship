package boyi.battleship.core.gamemanager;

import boyi.battleship.core.battlefield.BattleFieldBuilder;
import boyi.battleship.core.battlefield.BuildBattleFieldResult;
import boyi.battleship.core.chat.ChatMessage;
import boyi.battleship.core.playerspecific.gameevent.PlayerSpecificGameEvent;
import boyi.battleship.core.store.ChatStore;
import boyi.battleship.core.game.Game;
import boyi.battleship.core.game.GameState;
import boyi.battleship.core.gameevent.GameEventBuilder;
import boyi.battleship.core.gameevent.GameEventService;
import boyi.battleship.core.player.Player;
import boyi.battleship.core.player.PlayerType;
import boyi.battleship.core.ship.Ship;
import boyi.battleship.core.playerspecific.gamestate.PlayerSpecificGameState;
import boyi.battleship.core.playerspecific.gamestate.PlayerSpecificGameStateGenerator;
import boyi.battleship.core.store.GameStore;
import boyi.battleship.core.store.PlayerStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("gameManager")
public class GameManager {
    private final PlayerStore playerStore;
    private final GameStore gameStore;
    private final ChatStore chatStore;
    private final BattleFieldBuilder battleFieldBuilder;
    private final PlayerSpecificGameStateGenerator playerSpecificGameStateGenerator;
    private final GameEventBuilder gameEventBuilder;
    private final GameEventService gameEventService;

    @Autowired
    public GameManager(@NotNull PlayerStore playerStore, @NotNull GameStore gameStore,
                       @NotNull ChatStore chatStore, @NotNull BattleFieldBuilder battleFieldBuilder,
                       @NotNull PlayerSpecificGameStateGenerator playerSpecificGameStateGenerator,
                       @NotNull GameEventBuilder gameEventBuilder,
                       @NotNull GameEventService gameEventService) {
        this.playerStore = playerStore;
        this.gameStore = gameStore;
        this.chatStore = chatStore;
        this.battleFieldBuilder = battleFieldBuilder;
        this.playerSpecificGameStateGenerator = playerSpecificGameStateGenerator;
        this.gameEventBuilder = gameEventBuilder;
        this.gameEventService = gameEventService;
    }

    @NotNull
    public synchronized JoinGameResult tryJoinPlayer(@NotNull Game game, @NotNull String playerName, boolean joinAsSpectator) {
        GameState gameState = game.getState();

        if (joinAsSpectator) {
            if (!game.canAcceptMoreSpectators()) {
                return new JoinGameResult(false, "This game doesn't accept any more spectators", null, game.getKey());
            }
        } else if (!gameState.isAwaitingPlayers()) {
            return new JoinGameResult(false, "The game has already started", null, game.getKey());
        }

        String playerToken = joinGame(game, playerName, joinAsSpectator);

        return new JoinGameResult(true, "", playerToken, game.getKey());
    }

    @NotNull
    public synchronized JoinGameResult createAndJoinGame(@NotNull String playerName, int maxSpectators, boolean joinAsSpectator) {
        Game game = new Game();

        game.setMaxSpectators(maxSpectators);

        gameStore.register(game);
        String playerToken = joinGame(game, playerName, joinAsSpectator);

        return new JoinGameResult(true, "", playerToken, game.getKey());
    }

    @NotNull
    public synchronized Optional<Game> getGame(@NotNull String gameKey) {
        return gameStore.get(gameKey);
    }

    @NotNull
    public synchronized SubmitShipDataResult submitShipData(@NotNull Player player, @NotNull List<Ship> shipData) {
        if (player.isSpectator()) {
            return new SubmitShipDataResult(false, "Spectators can't submit ship");
        }

        BuildBattleFieldResult buildBattleFieldResult = battleFieldBuilder.build(shipData);
        if (!buildBattleFieldResult.isSuccess()) {
            return new SubmitShipDataResult(false, "Invalid ship data");
        }

        Game game = player.getGame();

        if (game.canSubmitShips(player)) {
            game.initBattleField(player, buildBattleFieldResult.getBattleField());
            return new SubmitShipDataResult(true, "");
        } else {
            return new SubmitShipDataResult(false, "You have already submitted ship data");
        }
    }

    @NotNull
    public synchronized PlayerSpecificGameState getGameState(@NotNull Player player) {
        return playerSpecificGameStateGenerator.generate(player.getGame().getState(), player);
    }

    @NotNull
    public synchronized Optional<Player> authorize(@NotNull String playerKey) {
        return playerStore.get(playerKey);
    }

    @NotNull
    private synchronized String joinGame(@NotNull Game game, @NotNull String playerName, boolean joinAsSpectator) {
        Player player = playerStore.register(
                new Player(playerName, joinAsSpectator ? PlayerType.SPECTATOR : PlayerType.PLAYER, game)
        );

        game.addPlayer(player);

        if (joinAsSpectator) {
            gameEventService.saveGameEvent(gameEventBuilder.buildSpectatorJoinedGameEvent(player), game);
        } else {
            gameEventService.saveGameEvent(gameEventBuilder.buildPlayerJoinedGameEvent(player), game);
        }

        return player.getKey();
    }

    @NotNull
    public synchronized List<ChatMessage> getChatHistoryFor(@NotNull Player player) {
        return chatStore.getOrCreateFor(player.getGame()).getMessages();
    }

    @NotNull
    public List<PlayerSpecificGameEvent> getEventHistoryFor(@NotNull Player player) {
        return gameEventService.getHistoryFor(player);
    }

    public synchronized long sendMessage(@NotNull Player player, @NotNull String message) {
        return gameEventService.sendChatMessage(player.getName(), message, player.getGame());
    }
}
