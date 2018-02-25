package boyi.battleship.core.store;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.game.GameEventHistory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository("gameEventHistoryStore")
public class GameEventHistoryStore extends DependentStore<GameEventHistory, Game> {
    @Override
    @NotNull
    public GameEventHistory getOrCreateFor(@NotNull Game game) {
        String key = game.getKey();
        return get(key).orElse(register(key, new GameEventHistory()));
    }
}
