package boyi.battleship.core.gamemanager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JoinGameResult {
    private boolean success;

    private String playerToken;

    @NotNull
    private String gameKey;

    @NotNull
    private String errorMessage;

    public JoinGameResult(boolean success, @NotNull String errorMessage,
                          String playerToken, @NotNull String gameKey) {
        this.success = success;
        this.playerToken = playerToken;
        this.errorMessage = errorMessage;
        this.gameKey = gameKey;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getPlayerToken() {
        return playerToken;
    }

    @NotNull
    public String getGameKey() {
        return gameKey;
    }

    @NotNull
    public String getErrorMessage() {
        return errorMessage;
    }
}
