package boyi.battleship.server.requests;

import org.jetbrains.annotations.NotNull;

public class RequestWithPlayerToken {
    @NotNull
    private String playerToken;

    public RequestWithPlayerToken() {
        this.playerToken = "";
    }

    public RequestWithPlayerToken(@NotNull String playerToken) {
        this.playerToken = playerToken;
    }

    @NotNull
    public String getPlayerToken() {
        return playerToken;
    }

    public void setPlayerToken(@NotNull String playerToken) {
        this.playerToken = playerToken;
    }
}
