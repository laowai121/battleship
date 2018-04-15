package boyi.battleship.core.store.impl;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.store.GameStore;
import boyi.battleship.core.store.Store;
import org.springframework.stereotype.Repository;

@Repository("gameStore")
public class GameStoreImpl extends GenericStoreImpl<Game> implements GameStore {

}
