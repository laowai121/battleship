package boyi.battleship.core.store;

import boyi.battleship.core.chat.Chat;
import boyi.battleship.core.game.Game;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository("chatStore")
public class ChatStore extends Store<Chat> {
    @NotNull
    public Chat getOrCreateFor(@NotNull Game game) {
        String id = game.getId();
        return get(id).orElseGet(() -> register(id, new Chat()));
    }
}