package boyi.battleship.core.game;

import boyi.battleship.core.battleshipobject.BattleshipObject;
import boyi.battleship.core.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameEventHistory extends BattleshipObject {
    @NotNull
    private List<GameEvent> gameEvents;

    public GameEventHistory() {
        gameEvents = new ArrayList<>();
    }

    public void add(@NotNull GameEvent gameEvent) {
        gameEvents.add(gameEvent);
    }

    @NotNull
    public List<GameEvent> getGameEvents() {
        return gameEvents;
    }
}
