package boyi.battleship.core.game;

import boyi.battleship.core.battlefield.BattleField;
import boyi.battleship.core.battleshipobject.BattleshipObject;
import boyi.battleship.core.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Game extends BattleshipObject {
    public static final int UNLIMITED_SPECTATORS = -1;
    public static final int SPECTATORS_MAX = 16;

    @Nullable
    private Player playerA;

    @Nullable
    private Player playerB;

    private Player currentPlayer;

    @Nullable
    private BattleField playerABattleField;

    @Nullable
    private BattleField playerBBattleField;

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

    public boolean canSubmitShips(@NotNull Player player) {
        if (player == playerA || player == playerB) {
            if (state == GameState.AWAITING_SHIPS) {
                return true;
            } else if (state == GameState.AWAITING_A_SHIPS) {
                return player == playerA;
            } else if (state == GameState.AWAITING_B_SHIPS) {
                return player == playerB;
            }
        }

        return false;
    }

    public void initBattleField(@NotNull Player player, @NotNull BattleField battleField) {
        if (player == playerA) {
            playerABattleField = battleField;
        } else if (player == playerB) {
            playerBBattleField = battleField;
        }

        if (playerABattleField != null && playerBBattleField != null) {
            currentPlayer = Math.random() >= 0.5 ? playerA : playerB;
        }

        updateGameState();
    }

    public void addPlayer(@NotNull Player player) {
        if (player.isSpectator()) {
            spectators.add(player);
        } else {
            if (playerA == null) {
                playerA = player;
            } else if (playerB == null) {
                playerB = player;
            }
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

    @Nullable
    public Player getPlayerA() {
        return playerA;
    }

    @Nullable
    public Player getPlayerB() {
        return playerB;
    }

    public void setMaxSpectators(int maxSpectators) {
        this.maxSpectators = maxSpectators;
    }

    @NotNull
    public List<Player> getAllPlayersAndSpectators() {
        List<Player> result = new ArrayList<>();

        if (playerA != null) {
            result.add(playerA);
        }

        if (playerB != null) {
            result.add(playerB);
        }


        result.addAll(spectators);

        return result;
    }

    private void updateGameState() {
        if (playerA == null) {
            state = GameState.AWAITING_A;
        } else if (playerB == null) {
            state = GameState.AWAITING_B;
        } else if (playerABattleField == null && playerBBattleField == null) {
            state = GameState.AWAITING_SHIPS;
        } else if (playerABattleField == null) {
            state = GameState.AWAITING_A_SHIPS;
        } else if (playerBBattleField == null) {
            state = GameState.AWAITING_B_SHIPS;
        } else if (currentPlayer == playerA) {
            state = playerBBattleField.hasAliveShips() ? GameState.AWAITING_A_ATTACK : GameState.A_WON;
        } else if (currentPlayer == playerB) {
            state = playerABattleField.hasAliveShips() ? GameState.AWAITING_B_ATTACK : GameState.B_WON;
        }
    }
}
