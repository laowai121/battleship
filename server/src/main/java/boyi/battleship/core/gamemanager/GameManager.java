package boyi.battleship.core.gamemanager;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.gamestate.GameState;
import boyi.battleship.core.player.Player;
import boyi.battleship.core.playerspecific.chat.PlayerSpecificChatMessage;
import boyi.battleship.core.playerspecific.extendedgamestate.ExtendedGameState;
import boyi.battleship.core.playerspecific.gameevent.PlayerSpecificGameEvent;
import boyi.battleship.core.ship.Ship;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface GameManager {
    @NotNull JoinGameResult tryJoinPlayer(@NotNull Game game, @NotNull String playerName, boolean joinAsSpectator);
    @NotNull JoinGameResult createAndJoinGame(@NotNull String playerName, int maxSpectators, boolean joinAsSpectator);
    @NotNull Optional<Game> getGame(@NotNull String gameKey);
    @NotNull SubmitShipDataResult submitShipData(@NotNull Player player, @NotNull List<Ship> shipData);
    @NotNull GameState getGameStateFor(@NotNull Player player);
    @NotNull ExtendedGameState getExtendedGameStateFor(@NotNull Player player);
    @NotNull Optional<Player> authorize(@NotNull String playerToken);
    @NotNull List<PlayerSpecificChatMessage> getChatHistoryFor(@NotNull Player player);
    @NotNull List<PlayerSpecificGameEvent> getEventHistoryFor(@NotNull Player player);
    @NotNull String sendMessage(@NotNull Player player, @NotNull String message);
}
