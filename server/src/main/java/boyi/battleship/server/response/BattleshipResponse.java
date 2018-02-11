package boyi.battleship.server.response;

import org.jetbrains.annotations.NotNull;

public class BattleshipResponse {
    private boolean success;

    @NotNull
    private String message;

    public BattleshipResponse(boolean success, @NotNull String message) {
        this.success = success;
        this.message = message;
    }

    public BattleshipResponse(boolean success) {
        this(success, success ? "OK" : "Unknown Error");
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NotNull String message) {
        this.message = message;
    }
}
