package boyi.battleship.core.exceptions;

import org.jetbrains.annotations.NotNull;

public class InvalidBattleFieldCoordinateException extends BattleshipException {
    public InvalidBattleFieldCoordinateException(@NotNull String s) {
        super(s);
    }
}
