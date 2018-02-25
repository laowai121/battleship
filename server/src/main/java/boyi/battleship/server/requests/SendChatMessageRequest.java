package boyi.battleship.server.requests;

import org.jetbrains.annotations.NotNull;

public class SendChatMessageRequest extends RequestWithPlayerToken {
    private String message;

    public SendChatMessageRequest() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(@NotNull String message) {
        this.message = message;
    }
}
