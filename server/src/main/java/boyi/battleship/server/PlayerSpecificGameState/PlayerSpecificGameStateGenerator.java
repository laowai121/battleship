package boyi.battleship.server.PlayerSpecificGameState;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.game.GameState;
import boyi.battleship.core.player.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("playerSpecificGameStateGenerator")
public class PlayerSpecificGameStateGenerator {
    @NotNull
    public PlayerSpecificGameState generate(@NotNull GameState gameState, @NotNull Player player) {
        Game game = player.getGame();

        boolean isPlayerA = game.getPlayerA() == player;
        boolean isPlayerB = !isPlayerA && game.getPlayerB() == player;

        if (isPlayerA || isPlayerB) {
            return getSubjectiveGameState(gameState, isPlayerA);
        } else { // spectator
            switch (gameState) {
                case AWAITING_A:
                    return PlayerSpecificGameState.AWAITING_A;
                case AWAITING_B:
                    return PlayerSpecificGameState.AWAITING_B;
                case AWAITING_SHIPS:
                    return PlayerSpecificGameState.AWAITING_SHIPS;
                case AWAITING_A_SHIPS:
                    return PlayerSpecificGameState.AWAITING_A_SHIPS;
                case AWAITING_B_SHIPS:
                    return PlayerSpecificGameState.AWAITING_B_SHIPS;
                case AWAITING_A_ATTACK:
                    return PlayerSpecificGameState.AWAITING_A_ATTACK;
                case AWAITING_B_ATTACK:
                    return PlayerSpecificGameState.AWAITING_B_ATTACK;
                case A_WON:
                    return PlayerSpecificGameState.A_WON;
                case B_WON:
                    return PlayerSpecificGameState.B_WON;
                default:
                    return PlayerSpecificGameState.UNKNOWN;
            }
        }
    }

    @NotNull
    private PlayerSpecificGameState getSubjectiveGameState(@NotNull GameState gameState, boolean isPlayerA) {
        switch (gameState) {
            case AWAITING_A:
                return isPlayerA ? PlayerSpecificGameState.AWAITING_ME : PlayerSpecificGameState.AWAITING_OPPONENT;
            case AWAITING_B:
                return isPlayerA ? PlayerSpecificGameState.AWAITING_OPPONENT : PlayerSpecificGameState.AWAITING_ME;
            case AWAITING_SHIPS:
                return PlayerSpecificGameState.AWAITING_MY_SHIPS;
            case AWAITING_A_SHIPS:
                return isPlayerA ? PlayerSpecificGameState.AWAITING_MY_SHIPS : PlayerSpecificGameState.AWAITING_OPPONENT_SHIPS;
            case AWAITING_B_SHIPS:
                return isPlayerA ? PlayerSpecificGameState.AWAITING_OPPONENT_SHIPS : PlayerSpecificGameState.AWAITING_MY_SHIPS;
            case AWAITING_A_ATTACK:
                return isPlayerA ? PlayerSpecificGameState.AWAITING_MY_ATTACK : PlayerSpecificGameState.AWAITING_OPPONENT_ATTACK;
            case AWAITING_B_ATTACK:
                return isPlayerA ? PlayerSpecificGameState.AWAITING_OPPONENT_ATTACK : PlayerSpecificGameState.AWAITING_MY_ATTACK;
            case A_WON:
                return isPlayerA ? PlayerSpecificGameState.I_WON : PlayerSpecificGameState.I_LOST;
            case B_WON:
                return isPlayerA ? PlayerSpecificGameState.I_LOST : PlayerSpecificGameState.I_WON;
            default:
                return PlayerSpecificGameState.UNKNOWN;
        }
    }
}
