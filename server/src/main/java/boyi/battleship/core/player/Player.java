package boyi.battleship.core.player;

import boyi.battleship.core.battlefield.BattleField;
import boyi.battleship.core.battleshipobject.BattleshipObject;
import boyi.battleship.core.exceptions.BattleshipException;
import boyi.battleship.core.game.Game;
import boyi.battleship.core.gamestate.GameState;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Player extends BattleshipObject {
    public static final int MAX_NAME_LENGTH = 20;

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

    public boolean isPlayer() {
        return playerType == PlayerType.PLAYER && (isPlayerA() || isPlayerB());
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

    public boolean canSubmitShips() throws BattleshipException {
        GameState gameState = getGameState();

        if (isPlayer()) {
            if (gameState == GameState.AWAITING_SHIPS) {
                return true;
            } else if (isPlayerA()) {
                return gameState == GameState.AWAITING_A_SHIPS;
            } else {
                return gameState == GameState.AWAITING_B_SHIPS;
            }
        } else {
            return false;
        }
    }

    public void submitShips(@NotNull BattleField battleField) throws BattleshipException {
        if (!isPlayer()) {
            throw new BattleshipException("This player doesn't have rights to submit ships for this game");
        }

        if (isPlayerA()) {
            game.initBattleFieldA(battleField);
        } else {
            game.initBattleFieldB(battleField);
        }
    }

    // returns subjective game state for this player
    @NotNull
    public GameState getGameState() {
        GameState gameState = game.getState();

        try {
            if (isPlayer()) {
                if (hasSubmittedShips()) {
                    gameState = isPlayerA() ? GameState.AWAITING_A_SHIPS : GameState.AWAITING_B_SHIPS;
                }
            } else if (isSpectator()) {
                return gameState;
            } else {
                gameState = GameState.UNKNOWN;
            }
        } catch (BattleshipException e) {
            gameState = GameState.UNKNOWN;
        }

        return gameState;
    }

    public boolean isPlayerA() {
        return game.getPlayerA().equals(Optional.of(this));
    }

    public boolean isPlayerB() {
        return game.getPlayerB().equals(Optional.of(this));
    }

    public boolean hasSubmittedShips() throws BattleshipException {
        if (isPlayer()) {
            return isPlayerA() ? game.playerAShipsSubmitted() : game.playerBShipsSubmitted();
        } else {
            throw new BattleshipException("hasSubmittedShips() can not be called on this type of player");
        }
    }
}
