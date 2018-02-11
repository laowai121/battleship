package boyi.battleship.server.store;

import boyi.battleship.server.game.Game;
import org.springframework.stereotype.Repository;

@Repository("gameStore")
public class GameStore extends Store<Game> {

}
