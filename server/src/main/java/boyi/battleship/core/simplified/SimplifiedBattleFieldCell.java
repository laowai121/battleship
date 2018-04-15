package boyi.battleship.core.simplified;

import boyi.battleship.core.battlefield.BattleFieldCell;
import boyi.battleship.core.ship.ShipSegment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimplifiedBattleFieldCell {
    @Nullable
    private Integer shipIndex = null;

    private boolean hurt = false;

    public SimplifiedBattleFieldCell(@NotNull BattleFieldCell cell) {
        ShipSegment shipSegment = cell.getShipSegment();
        if (shipSegment != null) {
            shipIndex = shipSegment.getShip().getShipIndex();
            hurt = !shipSegment.isAlive();
        }
    }

    @Nullable
    public Integer getShipIndex() {
        return shipIndex;
    }

    public boolean isHurt() {
        return hurt;
    }
}
