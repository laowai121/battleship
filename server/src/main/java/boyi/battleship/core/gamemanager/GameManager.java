package boyi.battleship.core.gamemanager;

import boyi.battleship.core.battlefield.BattleFieldBuilder;
import boyi.battleship.core.battlefield.BuildBattleFieldResult;
import boyi.battleship.core.player.PlayerToken;
import boyi.battleship.core.playerspecific.chat.PlayerSpecificChatMessage;
import boyi.battleship.core.playerspecific.chat.PlayerSpecificChatMessageGenerator;
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
import boyi.battleship.core.store.PlayerTokenStore;
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
    private final PlayerTokenStore playerTokenStore;
    private final PlayerSpecificChatMessageGenerator playerSpecificChatMessageGenerator;

    @Autowired
    public GameManager(@NotNull PlayerStore playerStore,
                       @NotNull GameStore gameStore,
                       @NotNull ChatStore chatStore,
                       @NotNull BattleFieldBuilder battleFieldBuilder,
                       @NotNull PlayerSpecificGameStateGenerator playerSpecificGameStateGenerator,
                       @NotNull GameEventBuilder gameEventBuilder,
                       @NotNull GameEventService gameEventService,
                       @NotNull PlayerTokenStore playerTokenStore,
                       @NotNull PlayerSpecificChatMessageGenerator playerSpecificChatMessageGenerator) {
        this.playerStore = playerStore;
        this.gameStore = gameStore;
        this.chatStore = chatStore;
        this.battleFieldBuilder = battleFieldBuilder;
        this.playerSpecificGameStateGenerator = playerSpecificGameStateGenerator;
        this.gameEventBuilder = gameEventBuilder;
        this.gameEventService = gameEventService;
        this.playerTokenStore = playerTokenStore;
        this.playerSpecificChatMessageGenerator = playerSpecificChatMessageGenerator;
    }

    @NotNull
    public synchronized JoinGameResult tryJoinPlayer(@NotNull Game game, @NotNull String playerName,
                                                     boolean joinAsSpectator) {
        GameState gameState = game.getState();

        if (joinAsSpectator) {
            if (!game.canAcceptMoreSpectators()) {
                return new JoinGameResult(false, "This game doesn't accept any more spectators");
            }
        } else if (!gameState.isAwaitingPlayers()) {
            return new JoinGameResult(false, "The game has already started");
        }

        return joinGame(game, playerName, joinAsSpectator);
    }

    @NotNull
    public synchronized JoinGameResult createAndJoinGame(@NotNull String playerName,
                                                         int maxSpectators,
                                                         boolean joinAsSpectator) {
        Game game = new Game();
        game.setMaxSpectators(maxSpectators);
        gameStore.register(game);
        return joinGame(game, playerName, joinAsSpectator);
    }

    @NotNull
    public synchronized Optional<Game> getGame(@NotNull String gameKey) {
        return gameStore.get(gameKey);
    }

    @NotNull
    public synchronized SubmitShipDataResult submitShipData(@NotNull Player player, @NotNull List<Ship> shipData) {
        if (player.isSpectator()) {
            return new SubmitShipDataResult(false, "Spectators can't submit ships");
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
    public synchronized Optional<Player> authorize(@NotNull String playerToken) {
        return playerTokenStore.get(playerToken).map(PlayerToken::getPlayer);
    }

    @NotNull
    private synchronized JoinGameResult joinGame(@NotNull Game game, @NotNull String playerName, boolean joinAsSpectator) {
        Player player = playerStore.register(
                new Player(playerName, joinAsSpectator ? PlayerType.SPECTATOR : PlayerType.PLAYER, game)
        );

        PlayerToken playerToken = playerTokenStore.createFor(player);
        player.setToken(playerToken.getId());

        game.addPlayer(player);

        if (joinAsSpectator) {
            gameEventService.saveGameEvent(gameEventBuilder.buildSpectatorJoinedGameEvent(player));
        } else {
            gameEventService.saveGameEvent(gameEventBuilder.buildPlayerJoinedGameEvent(player));
        }

        return new JoinGameResult(true, playerToken.getId(), player.getId(), game.getId());
    }

    @NotNull
    public synchronized List<PlayerSpecificChatMessage> getChatHistoryFor(@NotNull Player player) {
        return playerSpecificChatMessageGenerator.generate(
                chatStore.getOrCreateFor(player.getGame()).getMessages(),
                player
        );
    }

    @NotNull
    public List<PlayerSpecificGameEvent> getEventHistoryFor(@NotNull Player player) {
        return gameEventService.getHistoryFor(player);
    }

    // returns message id
    public synchronized String sendMessage(@NotNull Player player, @NotNull String message) {
        return gameEventService.sendChatMessage(player, message, player.getGame());
    }
}
