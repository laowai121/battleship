package boyi.battleship.core.gameevent;

import boyi.battleship.core.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameEvent {
    public static final String PLAYER = "player";

    private long timestamp;

    @NotNull
    private Player initiator;

    @NotNull
    private EventType type;

    @NotNull
    private Map<String, Object> properties;

    public GameEvent(@NotNull EventType type, @NotNull Player initiator) {
        this.type = type;
        this.initiator = initiator;
        timestamp = System.currentTimeMillis();
        properties = new HashMap<>();
    }

    @NotNull
    public EventType getType() {
        return type;
    }

    @NotNull
    public GameEvent addProperty(String name, Object value) {
        properties.put(name, value);
        return this;
    }

    @Nullable
    public Optional<Object> getProperty(String name) {
        return Optional.ofNullable(properties.get(name));
    }

    @NotNull
    public Player getInitiator() {
        return initiator;
    }
}
