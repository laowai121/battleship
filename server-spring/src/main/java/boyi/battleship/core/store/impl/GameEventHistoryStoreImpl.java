package boyi.battleship.core.store.impl;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.game.GameEventHistory;
import boyi.battleship.core.store.GameEventHistoryStore;
import org.springframework.stereotype.Repository;

@Repository("gameEventHistoryStore")
public class GameEventHistoryStoreImpl
        extends GenericDependentStoreImpl<GameEventHistory, Game>
        implements GameEventHistoryStore {

}