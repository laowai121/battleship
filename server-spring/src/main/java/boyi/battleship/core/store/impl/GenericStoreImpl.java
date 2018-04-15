package boyi.battleship.core.store.impl;

import boyi.battleship.core.battleshipobject.BattleshipObject;
import boyi.battleship.core.keygenerator.KeyGenerator;
import boyi.battleship.core.store.Store;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class GenericStoreImpl<T extends BattleshipObject> implements Store<T> {
    @Autowired
    private KeyGenerator keyGenerator;

    @NotNull
    private Map<String, T> store;

    public GenericStoreImpl() {
        store = new HashMap<>();
    }

    @NotNull
    @Override
    public synchronized Optional<T> get(@NotNull String id) {
        return Optional.ofNullable(store.get(id.toLowerCase()));
    }

    @NotNull
    @Override
    public synchronized T register(@NotNull String id, @NotNull T o) {
        o.setId(id);
        store.put(id, o);
        return o;
    }

    @NotNull
    @Override
    public synchronized T register(@NotNull T o) {
        String id = keyGenerator.generateKey((n) -> get(n).isPresent());
        return register(id, o);
    }

    @NotNull
    @Override
    public synchronized T register(@NotNull T o, int keyLength) {
        String id = keyGenerator.generateKey((n) -> get(n).isPresent(), keyLength);
        return register(id, o);
    }
}
