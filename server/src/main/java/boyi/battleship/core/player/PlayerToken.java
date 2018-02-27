package boyi.battleship.core.player;

import boyi.battleship.core.battleshipobject.BattleshipObject;
import org.jetbrains.annotations.NotNull;

public class PlayerToken extends BattleshipObject {
    @NotNull
    private Player player;

    public PlayerToken(@NotNull Player player) {
        this.player = player;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }
}
