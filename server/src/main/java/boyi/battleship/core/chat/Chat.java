package boyi.battleship.core.chat;

import boyi.battleship.core.battleshipobject.BattleshipObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Chat extends BattleshipObject {
    @NotNull
    private List<ChatMessage> messages;

    public Chat() {
        messages = new ArrayList<>();
    }

    @NotNull
    public List<ChatMessage> getMessages() {
        return messages;
    }

    public ChatMessage addMessage(@NotNull String sender, @NotNull String message) {
        ChatMessage chatMessage = new ChatMessage(sender, message, messages.size() + 1);
        messages.add(chatMessage);
        return chatMessage;
    }
}