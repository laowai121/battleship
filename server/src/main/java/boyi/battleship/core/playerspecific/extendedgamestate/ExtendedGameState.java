package boyi.battleship.core.playerspecific.extendedgamestate;

import boyi.battleship.core.gamestate.GameState;
import boyi.battleship.core.simplified.SimplifiedBattleField;
import boyi.battleship.core.simplified.SimplifiedPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExtendedGameState {
    @NotNull
    private GameState gameState;

    @Nullable
    private SimplifiedPlayer playerA;

    @Nullable
    private SimplifiedPlayer playerB;

    @Nullable
    private SimplifiedBattleField battleFieldA;

    @Nullable
    private SimplifiedBattleField battleFieldB;

    @NotNull
    private List<SimplifiedPlayer> spectators;

    public ExtendedGameState(@NotNull GameState gameState,
                             @Nullable SimplifiedPlayer playerA,
                             @Nullable SimplifiedPlayer playerB,
                             @Nullable SimplifiedBattleField battleFieldA,
                             @Nullable SimplifiedBattleField battleFieldB,
                             @NotNull List<SimplifiedPlayer> spectators) {
        this.gameState = gameState;
        this.playerA = playerA;
        this.playerB = playerB;
        this.battleFieldA = battleFieldA;
        this.battleFieldB = battleFieldB;
        this.spectators = spectators;
    }

    @NotNull
    public GameState getGameState() {
        return gameState;
    }

    @Nullable
    public SimplifiedPlayer getPlayerA() {
        return playerA;
    }

    @Nullable
    public SimplifiedPlayer getPlayerB() {
        return playerB;
    }

    @Nullable
    public SimplifiedBattleField getBattleFieldA() {
        return battleFieldA;
    }

    @Nullable
    public SimplifiedBattleField getBattleFieldB() {
        return battleFieldB;
    }

    @NotNull
    public List<SimplifiedPlayer> getSpectators() {
        return spectators;
    }
}
