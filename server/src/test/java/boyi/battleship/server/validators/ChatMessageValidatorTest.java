package boyi.battleship.server.validators;

import boyi.battleship.core.chat.ChatMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatMessageValidatorTest {
    private static final String MESSAGE_VALID = ChatMessageValidator.MESSAGE_VALID;
    private static final String MESSAGE_TOO_LONG = ChatMessageValidator.MESSAGE_TOO_LONG;

    @Autowired
    private ChatMessageValidator chatMessageValidator;

    private static ValidationResult testEmpty;
    private static ValidationResult testValid1, testValid2, testValid3;
    private static ValidationResult testTooLong1, testTooLong2;

    private boolean setupComplete = false;

    @Before
    public void setup() {
        if (!setupComplete) {
            testEmpty = chatMessageValidator.validate("");
            testValid1 = chatMessageValidator.validate("Hello, how have you been doing?");
            testValid2 = chatMessageValidator.validate("你好啊，在幹嘛呢？");
            testValid3 = chatMessageValidator.validate(generateLongString(ChatMessage.MAX_LENGTH - 10) + "我們正在聽音樂呢ыы");
            testTooLong1 = chatMessageValidator.validate(generateLongString(ChatMessage.MAX_LENGTH - 10) + "我們正在聽音樂呢ыыы");
            testTooLong2 = chatMessageValidator.validate(generateLongString(ChatMessage.MAX_LENGTH + 1));

            setupComplete = true;
        }
    }

    @Test
    public void testEmpty() {
        assertThat(testEmpty.isValid()).isEqualTo(false);
        assertThat(testEmpty.getMessage()).isEqualTo(ChatMessageValidator.MESSAGE_CANT_BE_EMPTY);
    }

    @Test
    public void testValid1() {
        assertThat(testValid1.isValid()).isEqualTo(true);
        assertThat(testValid1.getMessage()).isEqualTo(MESSAGE_VALID);
    }

    @Test
    public void testValid2() {
        assertThat(testValid2.isValid()).isEqualTo(true);
        assertThat(testValid2.getMessage()).isEqualTo(MESSAGE_VALID);
    }

    @Test
    public void testValid3() {
        assertThat(testValid3.isValid()).isEqualTo(true);
        assertThat(testValid3.getMessage()).isEqualTo(MESSAGE_VALID);
    }

    @Test
    public void testTooLong1() {
        assertThat(testTooLong1.isValid()).isEqualTo(false);
        assertThat(testTooLong1.getMessage()).isEqualTo(MESSAGE_TOO_LONG);
    }

    @Test
    public void testTooLong2() {
        assertThat(testTooLong2.isValid()).isEqualTo(false);
        assertThat(testTooLong2.getMessage()).isEqualTo(MESSAGE_TOO_LONG);
    }

    private String generateLongString(int length) {
        StringBuilder sb = new StringBuilder();
        IntStream.rangeClosed(1, length)
                .forEach((n) -> sb.append("a"));
        return sb.toString();
    }
}
