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

    public void addMessage(@NotNull ChatMessage chatMessage) {
        messages.add(chatMessage);
    }
}
