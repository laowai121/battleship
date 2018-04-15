package boyi.battleship.core.battlefield;

import org.jetbrains.annotations.Nullable;

public class BuildBattleFieldResult {
    private boolean success;

    private BattleField battleField;

    BuildBattleFieldResult(boolean success, @Nullable BattleField battleField) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public BattleField getBattleField() {
        return battleField;
    }
}
