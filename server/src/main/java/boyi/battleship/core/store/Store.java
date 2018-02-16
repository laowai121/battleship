package boyi.battleship.core.store;

import boyi.battleship.core.battleshipobject.BattleshipObject;
import boyi.battleship.core.keygenerator.KeyGenerator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Store<T extends BattleshipObject> {
    @Autowired
    private KeyGenerator keyGenerator;

    @NotNull
    private Map<String, T> store;

    public Store() {
        store = new HashMap<>();
    }

    public Optional<T> get(@NotNull String key) {
        return Optional.ofNullable(store.get(key.toLowerCase()));
    }

    @NotNull
    public T register(@NotNull T o) {
        String key = keyGenerator.generateKey((n) -> get(n).isPresent());
        o.setKey(key);
        store.put(key, o);
        return o;
    }
}
