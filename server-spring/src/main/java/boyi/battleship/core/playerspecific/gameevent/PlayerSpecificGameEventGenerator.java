package boyi.battleship.core.playerspecific.gameevent;

import boyi.battleship.core.gameevent.GameEvent;
import boyi.battleship.core.player.Player;
import boyi.battleship.core.simplified.SimplifiedPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("playerSpecificGameEventGenerator")
public class PlayerSpecificGameEventGenerator {
    @NotNull
    public PlayerSpecificGameEvent generate(@NotNull GameEvent gameEvent, @NotNull Player player) {
        PlayerSpecificGameEvent result = new PlayerSpecificGameEvent(EventType.UNKNOWN);

        switch (gameEvent.getType()) {
            case PLAYER_JOINED:
                return result
                        .setType(EventType.PLAYER_JOINED)
                        .addProperty(
                                PlayerSpecificGameEvent.PLAYER,
                                gameEvent.getProperty(GameEvent.PLAYER).orElse("Unknown")
                        );
            case SPECTATOR_JOINED:
                return result
                        .setType(EventType.SPECTATOR_JOINED)
                        .addProperty(
                                PlayerSpecificGameEvent.SPECTATOR,
                                gameEvent.getProperty(GameEvent.SPECTATOR).orElse("Unknown")
                        );
        }

        return result;
    }
}
