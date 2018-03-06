package boyi.battleship.core.keygenerator;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.stream.IntStream;

@Service("keyGenerator")
public class KeyGeneratorImpl implements KeyGenerator {
    private static final int KEY_CHARS_LENGTH = KEY_CHARS.length();

    @NotNull
    @Override
    public String generateKey(int keyLength) {
        StringBuilder sb = new StringBuilder();
        IntStream.rangeClosed(1, keyLength)
                .forEach((n) -> sb.append(KEY_CHARS.charAt((int) (Math.random() * KEY_CHARS_LENGTH))));
        return sb.toString();
    }

    @NotNull
    @Override
    public String generateKey() {
        return generateKey(DEFAULT_KEY_LENGTH);
    }

    @NotNull
    @Override
    public String generateKey(@NotNull Predicate<String> checkDuplicateKey) {
        return generateKey(checkDuplicateKey, DEFAULT_KEY_LENGTH);
    }

    @NotNull
    @Override
    public String generateKey(@NotNull Predicate<String> checkDuplicateKey, int keyLength) {
        String key;

        do {
            key = generateKey(keyLength);
        } while (checkDuplicateKey.test(key));

        return key;
    }
}
