package boyi.battleship.server.shipparser;

import boyi.battleship.core.ship.Ship;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ParsedShipData {
    private boolean success;

    private List<Ship> ships;

    ParsedShipData(boolean success, @Nullable List<Ship> ships) {
        this.success = success;
        this.ships = ships;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(@NotNull List<Ship> ships) {
        this.ships = ships;
    }
}
