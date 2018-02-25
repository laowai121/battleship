package boyi.battleship.core.playerspecific.gameevent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PlayerSpecificGameEvent {
    public static final String PLAYER = "player";

    @NotNull
    private EventType type;

    @NotNull
    private Map<String, Object> properties;

    public PlayerSpecificGameEvent(@NotNull EventType type) {
        this.type = type;
        properties = new HashMap<>();
    }

    public EventType getType() {
        return type;
    }

    @NotNull
    public PlayerSpecificGameEvent setType(EventType type) {
        this.type = type;
        return this;
    }

    @NotNull PlayerSpecificGameEvent addProperty(String name, Object value) {
        properties.put(name, value);
        return this;
    }

    @Nullable
    public Object getProperty(String name) {
        return properties.get(name);
    }
}
