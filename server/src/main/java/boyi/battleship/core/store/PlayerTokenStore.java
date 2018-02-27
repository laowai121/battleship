package boyi.battleship.core.store;

import boyi.battleship.core.player.Player;
import boyi.battleship.core.player.PlayerToken;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository("playerTokenStore")
public class PlayerTokenStore extends Store<PlayerToken> {
    @NotNull
    public PlayerToken createFor(@NotNull Player player) {
        return register(player.getId(), new PlayerToken(player));
    }
}
