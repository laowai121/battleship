package boyi.battleship.core.store.impl;

import boyi.battleship.core.chat.ChatMessage;
import boyi.battleship.core.store.ChatMessageStore;
import org.springframework.stereotype.Repository;

@Repository("chatMessageStore")
public class ChatMessageStoreImpl
        extends GenericStoreImpl<ChatMessage>
        implements ChatMessageStore {

}