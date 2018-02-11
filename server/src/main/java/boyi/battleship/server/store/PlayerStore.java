package boyi.battleship.server.store;

import boyi.battleship.server.player.Player;
import org.springframework.stereotype.Repository;

@Repository("playerStore")
public class PlayerStore extends Store<Player> {

}
