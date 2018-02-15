package boyi.battleship.server.game;

import boyi.battleship.server.player.Player;
import boyi.battleship.server.store.GameStore;
import boyi.battleship.server.store.PlayerStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("gameManager")
public class GameManager {
    @Autowired
    private PlayerStore playerStore;

    @Autowired
    private GameStore gameStore;

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
    public JoinGameResult createAndJoinGame(@NotNull String playerName, int maxSpectators, @NotNull boolean joinAsSpectator) {
        Game game = new Game();

        game.setMaxSpectators(maxSpectators);

        synchronized (game) {
            gameStore.register(game);
            String playerToken = joinGame(game, playerName, joinAsSpectator);

            return new JoinGameResult(true, "", playerToken, game.getKey());
        }
    }

    @NotNull
    private String joinGame(@NotNull Game game, @NotNull String playerName, boolean joinAsSpectator) {
        Player player = playerStore.register(new Player(playerName, game));

        if (joinAsSpectator) {
            game.addSpectator(player);
        } else {
            game.addPlayer(player);
        }

        return player.getKey();
    }
}
