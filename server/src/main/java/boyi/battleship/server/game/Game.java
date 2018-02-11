package boyi.battleship.server.game;

import boyi.battleship.server.battleshipobject.BattleshipObject;
import boyi.battleship.server.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Game extends BattleshipObject {
    private Player playerA;
    private Player playerB;

    @NotNull
    private List<Player> spectators;

    @NotNull
    private GameState state;

    public Game() {
        spectators = new ArrayList<>();
        state = GameState.AWAITING_A;
    }

    public void addSpectator(@NotNull Player player) {
        spectators.add(player);
    }

    public void addPlayer(@NotNull Player player) {
        if (playerA == null) {
            playerA = player;
        } else if (playerB == null) {
            playerB = player;
        }

        updateGameState();
    }

    @NotNull
    public GameState getState() {
        return state;
    }

    private void updateGameState() {
        if (playerA == null) {
            state = GameState.AWAITING_A;
        } else if (playerB == null) {
            state = GameState.AWAITING_B;
        } else {
            // TODO: ...
        }
    }
}
