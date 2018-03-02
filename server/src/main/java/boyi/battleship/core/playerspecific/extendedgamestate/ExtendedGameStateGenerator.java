package boyi.battleship.core.playerspecific.extendedgamestate;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.player.Player;
import boyi.battleship.core.simplified.SimplifiedPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service("extendedGameStateGenerator")
public class ExtendedGameStateGenerator {
    @NotNull
    public ExtendedGameState generateFor(@NotNull Player player) {
        Game game = player.getGame();

        return new ExtendedGameState(
                player.getGameState(),
                game.getPlayerA().map(SimplifiedPlayer::new).orElse(null),
                game.getPlayerB().map(SimplifiedPlayer::new).orElse(null),
                game.getSpectators().stream().map(SimplifiedPlayer::new).collect(Collectors.toList())
        );
    }
}
