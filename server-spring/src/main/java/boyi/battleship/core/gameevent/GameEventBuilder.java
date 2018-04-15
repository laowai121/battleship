package boyi.battleship.core.gameevent;

import boyi.battleship.core.player.Player;
import boyi.battleship.core.simplified.SimplifiedPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("gameEventBuilder")
public class GameEventBuilder {
    @NotNull
    public GameEvent buildPlayerJoinedGameEvent(@NotNull Player player) {
        return new GameEvent(EventType.PLAYER_JOINED, player)
                .addProperty(GameEvent.PLAYER, new SimplifiedPlayer(player));
    }

    @NotNull
    public GameEvent buildSpectatorJoinedGameEvent(@NotNull Player spectator) {
        return new GameEvent(EventType.SPECTATOR_JOINED, spectator)
                .addProperty(GameEvent.SPECTATOR, new SimplifiedPlayer(spectator));
    }
}
