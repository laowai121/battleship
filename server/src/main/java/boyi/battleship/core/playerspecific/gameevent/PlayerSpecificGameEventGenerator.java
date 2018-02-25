package boyi.battleship.core.playerspecific.gameevent;

import boyi.battleship.core.gameevent.GameEvent;
import boyi.battleship.core.player.Player;
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
                                ((Player) gameEvent.getProperty(GameEvent.PLAYER).get()).getName()
                        );
            case SPECTATOR_JOINED:
                return result
                        .setType(EventType.SPECTATOR_JOINED)
                        .addProperty(
                                PlayerSpecificGameEvent.PLAYER,
                                ((Player) gameEvent.getProperty(GameEvent.PLAYER).get()).getName()
                        );
        }

        return result;
    }
}
