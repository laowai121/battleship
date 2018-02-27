package boyi.battleship.core.playerspecific.gameevent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PlayerSpecificGameEvent {
    public static final String PLAYER_NAME = "playerName";

    @NotNull
    private EventType type;

    @NotNull
    private Map<String, Object> properties;

    public PlayerSpecificGameEvent(@NotNull EventType type) {
        this.type = type;
        properties = new HashMap<>();
    }

    @NotNull
    public EventType getType() {
        return type;
    }

    @NotNull
    public PlayerSpecificGameEvent setType(@NotNull EventType type) {
        this.type = type;
        return this;
    }

    @NotNull PlayerSpecificGameEvent addProperty(@NotNull String name, @NotNull Object value) {
        properties.put(name, value);
        return this;
    }

    @Nullable
    public Object getProperty(@NotNull String name) {
        return properties.get(name);
    }

    @NotNull
    public Map<String, Object> getProperties() {
        return properties;
    }
}
