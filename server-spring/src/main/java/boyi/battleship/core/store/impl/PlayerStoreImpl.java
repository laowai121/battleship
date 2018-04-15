package boyi.battleship.core.store.impl;

import boyi.battleship.core.player.Player;
import boyi.battleship.core.store.PlayerStore;
import org.springframework.stereotype.Repository;

@Repository("playerStore")
public class PlayerStoreImpl extends GenericStoreImpl<Player> implements PlayerStore {

}
