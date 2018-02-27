package boyi.battleship.core.player;

import boyi.battleship.core.battleshipobject.BattleshipObject;
import boyi.battleship.core.game.Game;
import org.jetbrains.annotations.NotNull;

public class Player extends BattleshipObject {
    @NotNull
    private String name;

    @NotNull
    private Game game;

    @NotNull
    private PlayerType playerType;

    private String token;

    public Player(@NotNull String name, @NotNull PlayerType playerType, @NotNull Game game) {
        this.name = name;
        this.playerType = playerType;
        this.game = game;
    }

    public boolean isSpectator() {
        return playerType == PlayerType.SPECTATOR;
    }

    @NotNull
    public Game getGame() {
        return game;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getToken() {
        return token;
    }

    public void setToken(@NotNull String token) {
        this.token = token;
    }
}
