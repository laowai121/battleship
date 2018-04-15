package boyi.battleship.server.response;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BattleshipResponse {
    private boolean success;

    @NotNull
    private Map<String, Object> data;

    public BattleshipResponse(boolean success) {
        this.success = success;
        this.data = new HashMap<>();
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Object getProperty(String property) {
        return data.get(property);
    }

    public BattleshipResponse setProperty(@NotNull String property, @NotNull Object value) {
        data.put(property, value);
        return this;
    }
}
