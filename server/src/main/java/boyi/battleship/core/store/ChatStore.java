package boyi.battleship.core.store;

import boyi.battleship.core.chat.Chat;
import boyi.battleship.core.game.Game;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository("chatStore")
public class ChatStore extends DependentStore<Chat, Game> {
    @Override
    @NotNull
    public Chat getOrCreateFor(@NotNull Game game) {
        String key = game.getKey();
        return get(key).orElse(register(key, new Chat()));
    }
}