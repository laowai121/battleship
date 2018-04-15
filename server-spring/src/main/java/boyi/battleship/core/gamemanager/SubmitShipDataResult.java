package boyi.battleship.core.gamemanager;

import org.jetbrains.annotations.NotNull;

public class SubmitShipDataResult {
    private boolean success;

    @NotNull
    private String errorMessage;

    public SubmitShipDataResult(boolean success, @NotNull String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    @NotNull
    public String getErrorMessage() {
        return errorMessage;
    }
}
