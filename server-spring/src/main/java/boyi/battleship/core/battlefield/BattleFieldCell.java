package boyi.battleship.core.battlefield;

import boyi.battleship.core.ship.ShipSegment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BattleFieldCell {
    @NotNull
    private BattleFieldCellStatus status;

    @Nullable
    private ShipSegment shipSegment;

    public BattleFieldCell() {
        this.status = BattleFieldCellStatus.EMPTY;
        this.shipSegment = null;
    }

    public BattleFieldCell(@NotNull ShipSegment segment) {
        this.status = BattleFieldCellStatus.HAS_SHIP;
        this.shipSegment = segment;
    }

    public boolean isEmpty() {
        return status == BattleFieldCellStatus.EMPTY;
    }

    @Nullable
    public ShipSegment getShipSegment() {
        return this.shipSegment;
    }
}
