package boyi.battleship.core.chat;

import boyi.battleship.core.battleshipobject.BattleshipObject;
import boyi.battleship.core.player.Player;
import org.jetbrains.annotations.NotNull;

public class ChatMessage extends BattleshipObject {
    public static final int MAX_LENGTH = 10000;

    @NotNull
    private Player sender;

    private long messageTimestamp;

    @NotNull
    private String message;

    public ChatMessage(@NotNull Player sender, @NotNull String message) {
        this.sender = sender;
        this.messageTimestamp = System.currentTimeMillis();
        this.message = message;
    }

    @NotNull
    public Player getSender() {
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
