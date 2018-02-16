package boyi.battleship.core.ship;

import boyi.battleship.core.battlefield.BattleFieldCoordinate;
import org.jetbrains.annotations.NotNull;

public class ShipSegment {
    @NotNull
    private Ship ship;

    @NotNull
    private BattleFieldCoordinate position;

    private boolean alive;

    public ShipSegment(@NotNull Ship ship, @NotNull BattleFieldCoordinate position) {
        this.ship = ship;
        this.position = position;
        this.alive = true;
    }

    @NotNull
    public Ship getShip() {
        return ship;
    }

    @NotNull
    public BattleFieldCoordinate getPosition() {
        return position;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
