package boyi.battleship.core.playerspecific.chat;

import org.jetbrains.annotations.NotNull;

public class PlayerSpecificChatMessage {
    @NotNull
    private String id;

    @NotNull
    private String sender;

    @NotNull
    private String senderId;

    private long messageTimestamp;

    @NotNull
    private String message;

    public PlayerSpecificChatMessage(@NotNull String id, @NotNull String sender, @NotNull String senderId,
                                     long messageTimestamp, @NotNull String message) {
        this.id = id;
        this.sender = sender;
        this.senderId = senderId;
        this.messageTimestamp = messageTimestamp;
        this.message = message;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public String getSender() {
        return sender;
    }

    @NotNull
    public String getSenderId() {
        return senderId;
    }

    public long getMessageTimestamp() {
        return messageTimestamp;
    }

    @NotNull
    public String getMessage() {
        return message;
    }
}
