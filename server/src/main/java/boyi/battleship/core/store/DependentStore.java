package boyi.battleship.core.store;

import boyi.battleship.core.battleshipobject.BattleshipObject;
import org.jetbrains.annotations.NotNull;

public abstract class DependentStore<T extends BattleshipObject, D extends BattleshipObject>
        extends Store<T> {
    @NotNull
    public abstract T getOrCreateFor(@NotNull D owner);
}
