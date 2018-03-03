package boyi.battleship.server.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RequestValidatorTest {
    // TODO: finish these tests
    @Autowired
    private RequestValidator requestValidator;

//    private static ValidationResult testEmpty;
//    private static ValidationResult testValid1, testValid2, testValid3;
//    private static ValidationResult testTooLong1, testTooLong2;

    private boolean setupComplete = false;

    @Before
    public void setup() {
        if (!setupComplete) {
//            testEmpty = chatMessageValidator.validate("");
//            testValid1 = chatMessageValidator.validate("Hello, how have you been doing?");
//            testValid2 = chatMessageValidator.validate("你好啊，在幹嘛呢？");
//            testValid3 = chatMessageValidator.validate(generateLongString(ChatMessage.MAX_LENGTH - 10) + "我們正在聽音樂呢ыы");
//            testTooLong1 = chatMessageValidator.validate(generateLongString(ChatMessage.MAX_LENGTH - 10) + "我們正在聽音樂呢ыыы");
//            testTooLong2 = chatMessageValidator.validate(generateLongString(ChatMessage.MAX_LENGTH + 1));

            setupComplete = true;
        }
    }

    @Test
    public void testEmpty() {
//        assertThat(testEmpty.isValid()).isEqualTo(false);
//        assertThat(testEmpty.getMessage()).isEqualTo(ChatMessageValidator.MESSAGE_CANT_BE_EMPTY);
    }
}
