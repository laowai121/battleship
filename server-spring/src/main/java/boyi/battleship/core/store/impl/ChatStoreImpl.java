package boyi.battleship.core.store.impl;

import boyi.battleship.core.chat.Chat;
import boyi.battleship.core.game.Game;
import boyi.battleship.core.store.ChatStore;
import org.springframework.stereotype.Repository;

@Repository("chatStore")
public class ChatStoreImpl
        extends GenericDependentStoreImpl<Chat, Game>
        implements ChatStore {

}