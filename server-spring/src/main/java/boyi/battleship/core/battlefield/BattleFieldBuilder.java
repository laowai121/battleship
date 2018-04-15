package boyi.battleship.core.battlefield;

import boyi.battleship.core.exceptions.InvalidShipsException;
import boyi.battleship.core.ship.Ship;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("battleFieldBuilder")
public class BattleFieldBuilder {
    @NotNull
    public BuildBattleFieldResult build(@NotNull List<Ship> shipData) {
        BattleField battleField = new BattleField();

        try {
            battleField.addShips(shipData);
        } catch (InvalidShipsException e) {
            return new BuildBattleFieldResult(false, null);
        }

        return new BuildBattleFieldResult(true, battleField);
    }
}
