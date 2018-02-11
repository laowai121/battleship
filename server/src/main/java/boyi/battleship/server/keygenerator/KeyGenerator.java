package boyi.battleship.server.keygenerator;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.stream.IntStream;

@Service("keyGenerator")
public class KeyGenerator {
    public static final int KEY_LENGTH = 16;
    public static final String KEY_CHARS = "0123456789abcdef";

    private static final int KEY_CHARS_LENGTH = KEY_CHARS.length();

    @NotNull
    public String generateKey() {
        StringBuilder sb = new StringBuilder();
        IntStream.rangeClosed(1, KEY_LENGTH)
                .forEach((n) -> sb.append(KEY_CHARS.charAt((int) (Math.random() * KEY_CHARS_LENGTH))));
        return sb.toString();
    }

    @NotNull
    public String generateKey(@NotNull Predicate<String> checkDuplicateKey) {
        String key;

        do {
            key = generateKey();
        } while (checkDuplicateKey.test(key));

        return key;
    }
}
