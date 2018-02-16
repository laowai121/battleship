package boyi.battleship.server.shipparser;

import boyi.battleship.core.battlefield.BattleFieldCoordinate;
import boyi.battleship.core.exceptions.InvalidBattleFieldCoordinateException;
import boyi.battleship.core.ship.Ship;
import boyi.battleship.server.coordinateparser.CoordinateParser;
import boyi.battleship.server.coordinateparser.ParsedCoordinate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("shipParser")
public class ShipParser {
    private static final String DASH = "-";
    private static final String UNDERSCORE = "_";
    private static final String SPACE = " ";

    @Autowired
    private CoordinateParser coordinateParser;

    @NotNull
    public ParsedShipData parse(@NotNull List<String> shipStrings) {
        ParsedShipData result = new ParsedShipData(false, null);

        List<Ship> ships = new ArrayList<>();

        try {
            boolean success = true;

            int i = 1;

            for (String shipString : shipStrings) {
                Ship ship = parse(shipString, i);

                if (ship != null) {
                    ships.add(ship);
                } else {
                    success = false;
                    break;
                }

                i++;
            }

            if (success) {
                result.setSuccess(true);
                result.setShips(ships);
            }
        } catch (Exception ignored) {}

        return result;
    }

    @Nullable
    private Ship parse(@NotNull String shipString, int shipIndex) {
        Ship ship = null;

        String[] coordinatesString = null;

        if (shipString.contains(DASH)) {
            coordinatesString = shipString.split(DASH);
        } else if (shipString.contains(UNDERSCORE)) {
            coordinatesString = shipString.split(UNDERSCORE);
        } else if (shipString.contains(SPACE)) {
            coordinatesString = shipString.split(SPACE);
        }

        if (coordinatesString != null && coordinatesString.length == 2) {
            BattleFieldCoordinate start = null;
            BattleFieldCoordinate end = null;

            ParsedCoordinate result = coordinateParser.parse(coordinatesString[0]);
            if (result.isSuccess()) {
                start = result.getCoordinate();

                result = coordinateParser.parse(coordinatesString[1]);
                if (result.isSuccess()) {
                    end = result.getCoordinate();
                    try {
                        ship = new Ship(start, end, shipIndex);
                    } catch (InvalidBattleFieldCoordinateException ignored) {}
                }
            }
        }

        return ship;
    }
}
