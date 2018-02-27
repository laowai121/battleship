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


    Store() {
        store = new HashMap<>();
    }

    public synchronized Optional<T> get(@NotNull String id) {
        return Optional.ofNullable(store.get(id.toLowerCase()));
    }

    @NotNull
    public synchronized T register(String id, @NotNull T o) {
        o.setId(id);
        store.put(id, o);
        return o;
    }

    @NotNull
    public synchronized T register(@NotNull T o) {
        String id = keyGenerator.generateKey((n) -> get(n).isPresent());
        return register(id, o);
    }
}
