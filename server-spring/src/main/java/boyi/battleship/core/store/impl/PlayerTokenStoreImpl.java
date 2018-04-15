package boyi.battleship.core.store.impl;

import boyi.battleship.core.player.Player;
import boyi.battleship.core.player.PlayerToken;
import boyi.battleship.core.store.PlayerTokenStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

@Repository("playerTokenStore")
public class PlayerTokenStoreImpl extends GenericStoreImpl<PlayerToken>
        implements PlayerTokenStore {

    @NotNull
    @Override
    public synchronized PlayerToken createFor(@NotNull Player player) {
        return register(new PlayerToken(player), PLAYER_TOKEN_LENGTH);
    }
}
