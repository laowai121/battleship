package boyi.battleship.core.chat;

import org.jetbrains.annotations.NotNull;

public class ChatMessage {
    public static final int MAX_LENGTH = 10000;

    private long sequenceNumber;

    @NotNull
    private String sender;

    private long messageTimestamp;

    @NotNull
    private String message;

    public ChatMessage(@NotNull String sender, @NotNull String message, long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        this.sender = sender;
        this.messageTimestamp = System.currentTimeMillis();
        this.message = message;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    @NotNull
    public String getSender() {
        return sender;
    }

    public long getMessageTimestamp() {
        return messageTimestamp;
    }

    @NotNull
    public String getMessage() {
        return message;
    }
}
