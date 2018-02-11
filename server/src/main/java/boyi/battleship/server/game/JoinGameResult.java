package boyi.battleship.server.game;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class JoinGameResult {
    private boolean success;

    @Nullable
    private String playerToken;

    @NotNull
    private String gameKey;

    @NotNull
    private String errorMessage;

    public JoinGameResult(boolean success, @NotNull String errorMessage,
                          @Nullable String playerToken, @NotNull String gameKey) {
        this.success = success;
        this.playerToken = playerToken;
        this.errorMessage = errorMessage;
        this.gameKey = gameKey;
    }

    public boolean isSuccess() {
        return success;
    }

    public Optional<String> getPlayerToken() {
        return Optional.ofNullable(playerToken);
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
