package boyi.battleship.server.game;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class JoinGameResult {
    private boolean success;

    @Nullable
    private String playerToken;

    @NotNull
    private String errorMessage;

    public JoinGameResult(boolean success, @NotNull String errorMessage, @Nullable String playerToken) {
        this.success = success;
        this.playerToken = playerToken;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public Optional<String> getPlayerToken() {
        return Optional.ofNullable(playerToken);
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
