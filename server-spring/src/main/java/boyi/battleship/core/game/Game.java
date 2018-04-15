package boyi.battleship.core.game;

import boyi.battleship.core.battlefield.BattleField;
import boyi.battleship.core.battleshipobject.BattleshipObject;
import boyi.battleship.core.exceptions.BattleshipException;
import boyi.battleship.core.gamestate.GameState;
import boyi.battleship.core.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game extends BattleshipObject {
    public static final int UNLIMITED_SPECTATORS = -1;
    public static final int SPECTATORS_MAX = 16;

    @Nullable
    private Player playerA;

    @Nullable
    private Player playerB;

    @Nullable
    private Player currentPlayer;

    @Nullable
    private BattleField battleFieldA;

    @Nullable
    private BattleField battleFieldB;

    private int maxSpectators;

    private boolean finished;

    @NotNull
    private List<Player> spectators;

    public Game() {
        maxSpectators = UNLIMITED_SPECTATORS;
        spectators = new ArrayList<>();
        finished = false;
    }

    public boolean playerAShipsSubmitted() {
        return battleFieldA != null;
    }

    public boolean playerBShipsSubmitted() {
        return battleFieldB != null;
    }

    public void initBattleFieldA(@NotNull BattleField battleField) {
        battleFieldA = battleField;
    }

    public void initBattleFieldB(@NotNull BattleField battleField) {
        battleFieldB = battleField;
    }

    public void addPlayer(@NotNull Player player) {
        if (player.isSpectator()) {
            spectators.add(player);
        } else if (playerA == null) {
            playerA = player;
        } else if (playerB == null) {
            playerB = player;
        }
    }

    public boolean canAcceptMoreSpectators() {
        return maxSpectators == UNLIMITED_SPECTATORS || spectators.size() < maxSpectators;
    }

    @NotNull
    public Optional<Player> getPlayerA() {
        return Optional.ofNullable(playerA);
    }

    @NotNull
    public Optional<Player> getPlayerB() {
        return Optional.ofNullable(playerB);
    }

    @NotNull
    public Optional<BattleField> getBattleFieldA() {
        return Optional.ofNullable(battleFieldA);
    }

    @NotNull
    public Optional<BattleField> getBattleFieldB() {
        return Optional.ofNullable(battleFieldB);
    }

    @NotNull
    public List<Player> getSpectators() {
        return spectators;
    }

    public void setMaxSpectators(int maxSpectators) {
        this.maxSpectators = maxSpectators;
    }

    @NotNull
    public List<Player> getAllPlayersAndSpectators() {
        List<Player> result = new ArrayList<>();

        getPlayerA().ifPresent(result::add);
        getPlayerB().ifPresent(result::add);
        result.addAll(spectators);

        return result;
    }

    @NotNull
    public GameState getState() {
        if (playerA == null) {
            return GameState.AWAITING_A;
        }if (playerB == null) {
            return GameState.AWAITING_B;
        } else if (!playerAShipsSubmitted()) {
            return !playerBShipsSubmitted() ? GameState.AWAITING_SHIPS : GameState.AWAITING_A_SHIPS;
        } else if (!playerBShipsSubmitted()) {
            return GameState.AWAITING_B_SHIPS;
        } else if (currentPlayer == playerA) {
            return finished ? GameState.A_WON : GameState.AWAITING_A_ATTACK;
        } else if (currentPlayer == playerB) {
            return finished ? GameState.B_WON : GameState.AWAITING_B_ATTACK;
        } else {
            return GameState.UNKNOWN;
        }
    }

    @NotNull
    private Player getOrSetCurrentPlayer() throws BattleshipException {
        if (currentPlayer == null) {
            currentPlayer = Math.random() >= 0.5 ? playerA : playerB;
        }

        if (currentPlayer == null) {
            throw new BattleshipException(
                    "getOrSetCurrentPlayer() shouldn't be called until both player A and player B joined the game"
            );
        }

        return currentPlayer;
    }
}
