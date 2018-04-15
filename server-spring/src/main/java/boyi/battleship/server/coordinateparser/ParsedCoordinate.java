package boyi.battleship.server.coordinateparser;

import boyi.battleship.core.battlefield.BattleFieldCoordinate;
import org.jetbrains.annotations.Nullable;

public class ParsedCoordinate {
    private boolean success;

    @Nullable
    private BattleFieldCoordinate coordinate;

    public ParsedCoordinate(boolean success, @Nullable BattleFieldCoordinate coordinate) {
        this.success = success;
        this.coordinate = coordinate;
    }

    public boolean isSuccess() {
        return this.success;
    }

    @Nullable
    public BattleFieldCoordinate getCoordinate() {
        return coordinate;
    }
}