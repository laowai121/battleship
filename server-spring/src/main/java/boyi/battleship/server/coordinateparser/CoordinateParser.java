package boyi.battleship.server.coordinateparser;

import boyi.battleship.core.battlefield.BattleFieldCoordinate;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("coordinateParser")
public class CoordinateParser {
    @NotNull
    public ParsedCoordinate parse(@NotNull String coordinateString) {
        ParsedCoordinate result = new ParsedCoordinate(false, null);

        try {
            int row = -1;
            int col = -1;

            try {
                row = Integer.parseInt(coordinateString.replaceAll("[^0-9]", ""));
            } catch (NumberFormatException ignored) {}

            if (row >= 1 && row <= 10) {
                row--;

                String colString = coordinateString.replaceAll("[^A-Ja-j]", "");
                if (colString.length() == 1) {
                    char colChar = Character.toUpperCase(colString.charAt(0));
                    col = colChar - 'A';

                    if (col >= 0 && col <= 9) {
                        result = new ParsedCoordinate(true, new BattleFieldCoordinate(row, col));
                    }
                }
            }
        } catch (Exception ignored) {}

        return result;
    }
}
