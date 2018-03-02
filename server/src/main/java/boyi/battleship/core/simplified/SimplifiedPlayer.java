package boyi.battleship.core.simplified;

import boyi.battleship.core.player.Player;
import org.jetbrains.annotations.NotNull;

public class SimplifiedPlayer {
    @NotNull
    private String id;

    @NotNull
    private String name;

    public SimplifiedPlayer(@NotNull Player player) {
        id = player.getId();
        name = player.getName();
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }
}
