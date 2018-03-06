package boyi.battleship.core.store;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.game.GameEventHistory;

public interface GameEventHistoryStore extends DependentStore<GameEventHistory, Game> {

}