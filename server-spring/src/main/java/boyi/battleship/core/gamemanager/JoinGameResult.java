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

    private boolean isPlayerA;

    public JoinGameResult(boolean success,
                          @NotNull String playerToken,
                          @NotNull String playerId,
                          @NotNull String gameKey,
                          boolean isPlayerA) {
        this.success = success;
        this.playerToken = playerToken;
        this.playerId = playerId;
        this.gameKey = gameKey;
        this.isPlayerA = isPlayerA;
        this.errorMessage = "";
    }

    public JoinGameResult(boolean success,
                          @NotNull String errorMessage) {
        this(success, "", "", "", false);
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

    public boolean isPlayerA() {
        return isPlayerA;
    }
}
