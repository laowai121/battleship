package boyi.battleship.core.store;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.game.GameEventHistory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository("gameEventHistoryStore")
public class GameEventHistoryStore extends Store<GameEventHistory> {
    @NotNull
    public synchronized GameEventHistory getOrCreateFor(@NotNull Game game) {
        String id = game.getId();
        return get(id).orElseGet(() -> register(id, new GameEventHistory()));
    }
}
