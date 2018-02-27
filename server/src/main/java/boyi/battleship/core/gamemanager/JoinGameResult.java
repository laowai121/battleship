package boyi.battleship.core.gamemanager;

import org.jetbrains.annotations.NotNull;

public class JoinGameResult {
    private boolean success;

    @NotNull
    private String playerToken;

    @NotNull
    private String playerId;

    @NotNull
    private String gameKey;

    @NotNull
    private String errorMessage;

    public JoinGameResult(boolean success,
                          @NotNull String playerToken,
                          @NotNull String playerId,
                          @NotNull String gameKey) {
        this.success = success;
        this.playerToken = playerToken;
        this.playerId = playerId;
        this.gameKey = gameKey;
        this.errorMessage = "";
    }

    public JoinGameResult(boolean success,
                          @NotNull String errorMessage) {
        this(success, "", "", "");
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    @NotNull
    public String getPlayerToken() {
        return playerToken;
    }

    @NotNull
    public String getPlayerId() {
        return playerId;
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
