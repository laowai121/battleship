package boyi.battleship.core.exceptions;

import org.jetbrains.annotations.NotNull;

public class BattleshipException extends Exception {
    public BattleshipException(@NotNull String e) {
        super(e);
    }
}
