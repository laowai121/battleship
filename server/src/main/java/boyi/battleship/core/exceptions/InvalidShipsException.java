package boyi.battleship.core.exceptions;

import org.jetbrains.annotations.NotNull;

public class InvalidShipsException extends BattleshipException {
    public InvalidShipsException(@NotNull String s) {
        super(s);
    }
}
