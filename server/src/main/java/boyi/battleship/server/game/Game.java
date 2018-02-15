package boyi.battleship.server.game;

import boyi.battleship.server.battleshipobject.BattleshipObject;
import boyi.battleship.server.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Game extends BattleshipObject {
    public static final int UNLIMITED_SPECTATORS = -1;
    public static final int SPECTATORS_MAX = 16;

    private Player playerA;
    private Player playerB;

    private int maxSpectators;

    @NotNull
    private List<Player> spectators;

    @NotNull
    private GameState state;

    public Game() {
        maxSpectators = UNLIMITED_SPECTATORS;
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

    public boolean canAcceptMoreSpectators() {
        return maxSpectators == UNLIMITED_SPECTATORS || spectators.size() < maxSpectators;
    }

    @NotNull
    public GameState getState() {
        return state;
    }

    public int getMaxSpectators() {
        return maxSpectators;
    }

    public void setMaxSpectators(int maxSpectators) {
        this.maxSpectators = maxSpectators;
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
