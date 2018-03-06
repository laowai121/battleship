package boyi.battleship.core.store;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface Store<T> {
    @NotNull Optional<T> get(@NotNull String id);
    @NotNull T register(@NotNull String id, @NotNull T o);
    @NotNull T register(@NotNull T o);
    @NotNull T register(@NotNull T o, int keyLength);
}
