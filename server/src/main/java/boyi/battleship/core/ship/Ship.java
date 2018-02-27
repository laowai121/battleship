package boyi.battleship.core.ship;

import boyi.battleship.core.battlefield.BattleFieldCoordinate;
import boyi.battleship.core.exceptions.InvalidBattleFieldCoordinateException;
import org.jetbrains.annotations.NotNull;

public class Ship {
    @NotNull
    private ShipSegment[] segments;

    private int shipIndex;

    public Ship(@NotNull BattleFieldCoordinate start,
                @NotNull BattleFieldCoordinate end,
                int shipIndex) throws InvalidBattleFieldCoordinateException {
        int startRow = start.getRow();
        int endRow = end.getRow();
        int startCol = start.getCol();
        int endCol = end.getCol();

        int size;
        int tmp;

        if (startRow == endRow) {
            if (startCol > endCol) {
                tmp = startCol;
                startCol = endCol;
                endCol = tmp;
            }
            size = endCol - startCol + 1;
        } else if (startCol == endCol) {
            if (startRow > endRow) {
                tmp = startRow;
                startRow = endRow;
                endRow = tmp;
            }
            size = endRow - startRow + 1;
        } else {
            throw new InvalidBattleFieldCoordinateException("Invalid ship coordinates");
        }

        this.segments = new ShipSegment[size];

        int row = startRow;
        int col = startCol;

        for (int i = 0; i < size; i++) {
            BattleFieldCoordinate coordinate = new BattleFieldCoordinate(row, col);

            if (row < endRow) {
                row++;
            } else {
                col++;
            }

            this.segments[i] = new ShipSegment(this, coordinate);
        }

        this.shipIndex = shipIndex;
    }

    public boolean isAlive() {
        boolean alive = false;

        for (ShipSegment segment : segments) {
            if (segment.isAlive()) {
                alive = true;
                break;
            }
        }

        return alive;
    }

    @NotNull
    public ShipSegment[] getSegments() {
        return segments;
    }

    public int getShipIndex() {
        return shipIndex;
    }
}
