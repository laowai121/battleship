package boyi.battleship;

import java.util.stream.IntStream;

public class TestUtils {
    public static String generateLongString(int length, char fillWith) {
        StringBuilder sb = new StringBuilder();
        IntStream.rangeClosed(1, length)
                .forEach((n) -> sb.append(fillWith));
        return sb.toString();
    }

    public static String generateLongString(int length) {
        return generateLongString(length, 'a');
    }
}
