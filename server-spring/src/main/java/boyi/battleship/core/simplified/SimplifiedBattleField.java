package boyi.battleship.core.simplified;

import boyi.battleship.core.battlefield.BattleField;
import boyi.battleship.core.battlefield.BattleFieldCoordinate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SimplifiedBattleField {
    @NotNull
    private List<List<SimplifiedBattleFieldCell>> cells;

    public SimplifiedBattleField(@NotNull BattleField battleField) {
        cells = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < 10; rowIndex++) {
            List<SimplifiedBattleFieldCell> row = new ArrayList<>();

            for (int colIndex = 0; colIndex < 10; colIndex++) {
                row.add(new SimplifiedBattleFieldCell(
                        battleField.get(new BattleFieldCoordinate(rowIndex, colIndex))
                ));
            }

            cells.add(row);
        }
    }

    @NotNull
    public List<List<SimplifiedBattleFieldCell>> getCells() {
        return cells;
    }
}
