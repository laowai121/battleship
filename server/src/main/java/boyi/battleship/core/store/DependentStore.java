package boyi.battleship.core.store;

import boyi.battleship.core.battleshipobject.BattleshipObject;
import org.jetbrains.annotations.NotNull;

public interface DependentStore<T extends BattleshipObject, D extends BattleshipObject>
        extends Store<T> {
    @NotNull T getOrCreateFor(@NotNull D ownerObject, @NotNull T newInstance);
}
