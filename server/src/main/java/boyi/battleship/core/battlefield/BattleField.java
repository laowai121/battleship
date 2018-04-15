package boyi.battleship.core.battlefield;

import boyi.battleship.core.exceptions.InvalidShipsException;
import boyi.battleship.core.ship.Ship;
import boyi.battleship.core.ship.ShipMap;
import boyi.battleship.core.ship.ShipSegment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BattleField {
    @NotNull
    private BattleFieldCell[][] battleField;

    @NotNull
    private ShipMap shipMap;

    public BattleField()  {
        battleField = new BattleFieldCell[10][10];

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                battleField[row][col] = new BattleFieldCell();
            }
        }

        shipMap = new ShipMap();
    }

    public void addShips(@NotNull List<Ship> ships) throws InvalidShipsException {
        if (!checkShipsValid(ships)) {
            throw new InvalidShipsException("Invalid ships provided");
        }

        for (Ship ship : ships) {
            addShip(ship);
        }

        if (!checkSpacesBetweenShips()) {
            throw new InvalidShipsException("Ships should not touch each other");
        }
    }

//    public AttackResult attacked(BattleFieldCoordinate coord) throws InvalidBattleFieldCoordinateException {
//        if (coord == null || !coord.isValid()) {
//            throw new InvalidBattleFieldCoordinateException("Invalid coordinate: " + coord);
//        }
//
//        AttackResult result = AttackResult.MISS;
//
//        BattleFieldCell cell = get(coord);
//        if (!cell.isEmpty()) {
//            ShipSegment segment = cell.getShipSegment();
//            if (segment.isAlive()) {
//                segment.setAlive(false);
//                Ship ship = segment.getShip();
//
//                result = ship.isAlive() ? AttackResult.DAMAGE : AttackResult.KILL;
//            }
//        }
//
//        return result;
//    }

    public boolean hasAliveShips() {
        boolean result = false;

        for (Ship ship : shipMap.values()) {
            if (ship.isAlive()) {
                result = true;
                break;
            }
        }

        return result;
    }

    @NotNull
    public ShipMap getShips() {
        return shipMap;
    }

    @NotNull
    public BattleFieldCell get(@NotNull BattleFieldCoordinate coord) {
        return battleField[coord.getRow()][coord.getCol()];
    }

    private boolean checkShipsValid(@NotNull List<Ship> ships) {
        boolean valid = ships.size() == 10;

        if (valid) {
            List<BattleFieldCoordinate> occupiedPositions = new ArrayList<BattleFieldCoordinate>();

            int oneSegmentShipsCount = 0;
            int twoSegmentsShipsCount = 0;
            int threeSegmentsShipsCount = 0;
            int fourSegmentsShipsCount = 0;

            int i = 0;
            while (valid && i < ships.size()) {
                Ship ship = ships.get(i);

                ShipSegment[] segments = ship.getSegments();
                int segmentsCount = segments.length;

                if (segmentsCount >= 1 && segmentsCount <= 4) {
                    switch (segmentsCount) {
                        case 1:
                            oneSegmentShipsCount++;
                            break;
                        case 2:
                            twoSegmentsShipsCount++;
                            break;
                        case 3:
                            threeSegmentsShipsCount++;
                            break;
                        case 4:
                            fourSegmentsShipsCount++;
                            break;
                    }

                    for (ShipSegment segment : segments) {
                        BattleFieldCoordinate position = segment.getPosition();

                        if (!occupiedPositions.contains(position)) {
                            occupiedPositions.add(position);
                        } else {
                            valid = false;
                            break;
                        }
                    }
                } else {
                    valid = false;
                }

                i++;
            }

            if (valid) {
                valid = oneSegmentShipsCount == 4 && twoSegmentsShipsCount == 3 && threeSegmentsShipsCount == 2 && fourSegmentsShipsCount == 1;
            }
        }

        return valid;
    }

    private boolean checkSpacesBetweenShips() {
        boolean valid = true;

        int shipIndex = 0;
        while (valid && shipIndex < shipMap.size()) {
            Ship ship = shipMap.get(shipIndex);

            ShipSegment[] segments = ship.getSegments();

            int segmentIndex = 0;
            while (valid && segmentIndex < segments.length) {
                ShipSegment segment = segments[segmentIndex];

                List<BattleFieldCell> surroundingCells = getSurroundingCells(segment.getPosition());

                for (BattleFieldCell surroundingCell : surroundingCells) {
                    if (!surroundingCell.isEmpty() && !surroundingCell.getShipSegment().getShip().equals(ship)) {
                        valid = false;
                        break;
                    }
                }

                segmentIndex++;
            }

            shipIndex++;
        }

        return valid;
    }

    @NotNull
    private List<BattleFieldCell> getSurroundingCells(@NotNull BattleFieldCoordinate position) {
        List<BattleFieldCell> surroundingCells = new ArrayList<BattleFieldCell>();

        int row = position.getRow();
        int col = position.getCol();

        BattleFieldCoordinate coord = new BattleFieldCoordinate(row - 1, col - 1);
        if (coord.isValid()) {
            surroundingCells.add(get(coord));
        }

        coord = new BattleFieldCoordinate(row - 1, col);
        if (coord.isValid()) {
            surroundingCells.add(get(coord));
        }

        coord = new BattleFieldCoordinate(row - 1, col + 1);
        if (coord.isValid()) {
            surroundingCells.add(get(coord));
        }

        coord = new BattleFieldCoordinate(row, col - 1);
        if (coord.isValid()) {
            surroundingCells.add(get(coord));
        }

        coord = new BattleFieldCoordinate(row, col + 1);
        if (coord.isValid()) {
            surroundingCells.add(get(coord));
        }

        coord = new BattleFieldCoordinate(row + 1, col - 1);
        if (coord.isValid()) {
            surroundingCells.add(get(coord));
        }

        coord = new BattleFieldCoordinate(row + 1, col);
        if (coord.isValid()) {
            surroundingCells.add(get(coord));
        }

        coord = new BattleFieldCoordinate(row + 1, col + 1);
        if (coord.isValid()) {
            surroundingCells.add(get(coord));
        }

        return surroundingCells;
    }

    private void set(@NotNull BattleFieldCoordinate coord, @NotNull BattleFieldCell cell) {
        battleField[coord.getRow()][coord.getCol()] = cell;
    }

    private void addShip(@NotNull Ship ship) {
        for (ShipSegment segment : ship.getSegments()) {
            set(segment.getPosition(), new BattleFieldCell(segment));
        }

        shipMap.put(ship.getShipIndex(), ship);
    }
}