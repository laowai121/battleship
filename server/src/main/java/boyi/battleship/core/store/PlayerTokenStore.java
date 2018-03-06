package boyi.battleship.core.store;

import boyi.battleship.core.player.Player;
import boyi.battleship.core.player.PlayerToken;
import org.jetbrains.annotations.NotNull;

public interface PlayerTokenStore extends Store<PlayerToken> {
    int PLAYER_TOKEN_LENGTH = 32;

    @NotNull PlayerToken createFor(@NotNull Player player);
}