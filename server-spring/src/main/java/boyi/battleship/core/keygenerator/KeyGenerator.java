package boyi.battleship.core.keygenerator;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public interface KeyGenerator {
    int DEFAULT_KEY_LENGTH = 16;
    String KEY_CHARS = "0123456789abcdef";

    @NotNull String generateKey(int keyLength);
    @NotNull String generateKey();
    @NotNull String generateKey(@NotNull Predicate<String> checkDuplicateKey);
    @NotNull String generateKey(@NotNull Predicate<String> checkDuplicateKey, int keyLength);
}
