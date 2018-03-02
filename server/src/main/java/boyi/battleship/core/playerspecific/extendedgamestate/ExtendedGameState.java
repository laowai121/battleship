package boyi.battleship.core.playerspecific.extendedgamestate;

import boyi.battleship.core.gamestate.GameState;
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

    @NotNull
    private List<SimplifiedPlayer> spectators;

    public ExtendedGameState(@NotNull GameState gameState, @Nullable SimplifiedPlayer playerA,
                             @Nullable SimplifiedPlayer playerB, @NotNull List<SimplifiedPlayer> spectators) {
        this.gameState = gameState;
        this.playerA = playerA;
        this.playerB = playerB;
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

    @NotNull
    public List<SimplifiedPlayer> getSpectators() {
        return spectators;
    }
}
