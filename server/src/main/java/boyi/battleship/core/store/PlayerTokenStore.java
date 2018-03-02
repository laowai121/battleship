package boyi.battleship.core.store;

import boyi.battleship.core.player.Player;
import boyi.battleship.core.player.PlayerToken;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository("playerTokenStore")
public class PlayerTokenStore extends Store<PlayerToken> {
    public static final int PLAYER_TOKEN_LENGTH = 32;

    @NotNull
    public synchronized PlayerToken createFor(@NotNull Player player) {
        return register(new PlayerToken(player), PLAYER_TOKEN_LENGTH);
    }
}
