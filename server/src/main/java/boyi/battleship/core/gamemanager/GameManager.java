package boyi.battleship.core.gamemanager;

import boyi.battleship.core.battlefield.BattleFieldBuilder;
import boyi.battleship.core.battlefield.BuildBattleFieldResult;
import boyi.battleship.core.game.Game;
import boyi.battleship.core.game.GameState;
import boyi.battleship.core.player.Player;
import boyi.battleship.core.player.PlayerType;
import boyi.battleship.core.ship.Ship;
import boyi.battleship.server.requests.RequestWithPlayerToken;
import boyi.battleship.core.store.GameStore;
import boyi.battleship.core.store.PlayerStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("gameManager")
public class GameManager {
    @Autowired
    private PlayerStore playerStore;

    @Autowired
    private GameStore gameStore;

    @Autowired
    private BattleFieldBuilder battleFieldBuilder;

    @NotNull
    public JoinGameResult tryJoinPlayer(@NotNull Game game, @NotNull String playerName, boolean joinAsSpectator) {
        synchronized (game) {
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
    }

    @NotNull
    public JoinGameResult createAndJoinGame(@NotNull String playerName, int maxSpectators, boolean joinAsSpectator) {
        Game game = new Game();

        game.setMaxSpectators(maxSpectators);

        synchronized (game) {
            gameStore.register(game);
            String playerToken = joinGame(game, playerName, joinAsSpectator);

            return new JoinGameResult(true, "", playerToken, game.getKey());
        }
    }

    @NotNull
    public SubmitShipDataResult submitShipData(@NotNull Player player, @NotNull List<Ship> shipData) {
        if (player.isSpectator()) {
            return new SubmitShipDataResult(false, "Spectators can't submit ship");
        }

        BuildBattleFieldResult buildBattleFieldResult = battleFieldBuilder.build(shipData);
        if (!buildBattleFieldResult.isSuccess()) {
            return new SubmitShipDataResult(false, "Invalid ship data");
        }

        Game game = player.getGame();

        synchronized (game) {
            if (game.canSubmitShips(player)) {
                game.initBattleField(player, buildBattleFieldResult.getBattleField());
                return new SubmitShipDataResult(true, "");
            } else {
                return new SubmitShipDataResult(false, "You have already submitted ship data");
            }
        }
    }

    @NotNull
    public GameState getGameState(@NotNull Player player) {
        Game game = player.getGame();

        synchronized (game) {
            return game.getState();
        }
    }

    public Optional<Player> authorize(@NotNull RequestWithPlayerToken request) {
        return playerStore.get(request.getPlayerToken());
    }

    @NotNull
    private String joinGame(@NotNull Game game, @NotNull String playerName, boolean joinAsSpectator) {
        Player player = playerStore.register(
                new Player(playerName, joinAsSpectator ? PlayerType.SPECTATOR : PlayerType.PLAYER, game)
        );

        game.addPlayer(player);

        return player.getKey();
    }
}
