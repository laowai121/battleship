package boyi.battleship.core.store;

import boyi.battleship.core.chat.Chat;
import boyi.battleship.core.game.Game;

public interface ChatStore extends DependentStore<Chat, Game> {

}