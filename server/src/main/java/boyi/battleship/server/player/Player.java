package boyi.battleship.server.player;

import boyi.battleship.server.battleshipobject.BattleshipObject;
import boyi.battleship.server.game.Game;
import org.jetbrains.annotations.NotNull;

public class Player extends BattleshipObject {
    @NotNull
    private String name;

    @NotNull
    private Game game;

    public Player(@NotNull String name, @NotNull Game game) {
        this.name = name;
        this.game = game;
    }
}
