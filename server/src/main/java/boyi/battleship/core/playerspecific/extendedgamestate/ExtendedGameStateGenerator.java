package boyi.battleship.core.playerspecific.extendedgamestate;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.player.Player;
import boyi.battleship.core.simplified.SimplifiedBattleField;
import boyi.battleship.core.simplified.SimplifiedPlayer;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service("extendedGameStateGenerator")
public class ExtendedGameStateGenerator {
    @NotNull
    public ExtendedGameState generateFor(@NotNull Player player) {
        Game game = player.getGame();

        SimplifiedBattleField battleFieldA = player.isSpectator() || player.isPlayerA()
                ? game.getBattleFieldA().map(SimplifiedBattleField::new).orElse(null)
                : null;

        SimplifiedBattleField battleFieldB = player.isSpectator() || player.isPlayerB()
                ? game.getBattleFieldB().map(SimplifiedBattleField::new).orElse(null)
                : null;

        return new ExtendedGameState(
                player.getGameState(),
                game.getPlayerA().map(SimplifiedPlayer::new).orElse(null),
                game.getPlayerB().map(SimplifiedPlayer::new).orElse(null),
                battleFieldA,
                battleFieldB,
                game.getSpectators().stream().map(SimplifiedPlayer::new).collect(Collectors.toList())
        );
    }
}
