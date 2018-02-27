package boyi.battleship.core.store;

import boyi.battleship.core.chat.ChatMessage;
import org.springframework.stereotype.Repository;

@Repository("chatMessageStore")
public class ChatMessageStore extends Store<ChatMessage> {

}