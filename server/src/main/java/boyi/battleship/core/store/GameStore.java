package boyi.battleship.core.store;

import boyi.battleship.core.game.Game;
import org.springframework.stereotype.Repository;

@Repository("gameStore")
public class GameStore extends Store<Game> {

}
