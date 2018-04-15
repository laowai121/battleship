package boyi.battleship.core.store.impl;

import boyi.battleship.core.battleshipobject.BattleshipObject;
import boyi.battleship.core.store.DependentStore;
import org.jetbrains.annotations.NotNull;

public abstract class GenericDependentStoreImpl<T extends BattleshipObject, D extends BattleshipObject>
        extends GenericStoreImpl<T>
        implements DependentStore<T, D> {
    @NotNull
    @Override
    public T getOrCreateFor(@NotNull D ownerObject, @NotNull T newInstance) {
        String id = ownerObject.getId();
        return get(id).orElseGet(() -> register(id, newInstance));
    }
}
