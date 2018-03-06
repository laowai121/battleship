package boyi.battleship.core.gameevent;

import boyi.battleship.core.chat.ChatMessage;
import boyi.battleship.core.game.Game;
import boyi.battleship.core.player.Player;
import boyi.battleship.core.playerspecific.gameevent.PlayerSpecificGameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface GameEventService {
    void saveGameEvent(@NotNull GameEvent gameEvent);
    @NotNull ChatMessage sendChatMessage(@NotNull Player sender, @NotNull String message, @NotNull Game game);
    @NotNull List<PlayerSpecificGameEvent> getHistoryFor(@NotNull Player player);
}
