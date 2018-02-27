package boyi.battleship.core.gameevent;

import boyi.battleship.core.player.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("gameEventBuilder")
public class GameEventBuilder {
    @NotNull
    public GameEvent buildPlayerJoinedGameEvent(@NotNull Player player) {
        return new GameEvent(EventType.PLAYER_JOINED, player)
                .addProperty(GameEvent.PLAYER, player);
    }

    @NotNull
    public GameEvent buildSpectatorJoinedGameEvent(@NotNull Player player) {
        return new GameEvent(EventType.SPECTATOR_JOINED, player)
                .addProperty(GameEvent.PLAYER, player);
    }
}
