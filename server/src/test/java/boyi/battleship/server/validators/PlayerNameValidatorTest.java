package boyi.battleship.server.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerNameValidatorTest {
    private static final String ERROR_NAME_IS_TOO_LONG = "Player Name is too long (maximum length: 20)";
    private static final String VALID_NAME = "Player Name is valid";

    @Autowired
    private PlayerNameValidator playerNameValidator;

    private static ValidationResult testEmpty, testWhitespace;
    private static ValidationResult testTooLong, testChineseCharacters;
    private static ValidationResult testValid1, testValid2, testValid3;

    private boolean setupComplete = false;

    @Before
    public void setup() {
        if (!setupComplete) {
            testEmpty = playerNameValidator.validate("");
            testWhitespace = playerNameValidator.validate("        ");
            testTooLong = playerNameValidator.validate("Abcdef Ghijklm Nopqru");
            testChineseCharacters = playerNameValidator.validate("哇哈哈哈哈");
            testValid1 = playerNameValidator.validate("Abcdef Ghijklm Nopqr");
            testValid2 = playerNameValidator.validate("Heitian Boyi");
            testValid3 = playerNameValidator.validate("Петренко Василь");

            setupComplete = true;
        }
    }

    @Test
    public void testEmpty() {
        assertThat(testEmpty.isValid()).isEqualTo(false);
        assertThat(testEmpty.getMessage()).isEqualTo("Please, enter Player Name");
    }

    @Test
    public void testWhitespace() {
        assertThat(testWhitespace.isValid()).isEqualTo(true);
        assertThat(testWhitespace.getMessage()).isEqualTo(VALID_NAME);
    }

    @Test
    public void testTooLong() {
        assertThat(testTooLong.isValid()).isEqualTo(false);
        assertThat(testTooLong.getMessage()).isEqualTo(ERROR_NAME_IS_TOO_LONG);
    }

    @Test
    public void testChineseCharacters() {
        assertThat(testChineseCharacters.isValid()).isEqualTo(true);
        assertThat(testChineseCharacters.getMessage()).isEqualTo(VALID_NAME);
    }

    @Test
    public void testValid1() {
        assertThat(testValid1.isValid()).isEqualTo(true);
        assertThat(testValid1.getMessage()).isEqualTo(VALID_NAME);
    }

    @Test
    public void testValid2() {
        assertThat(testValid2.isValid()).isEqualTo(true);
        assertThat(testValid2.getMessage()).isEqualTo(VALID_NAME);
    }

    @Test
    public void testValid3() {
        assertThat(testValid3.isValid()).isEqualTo(true);
        assertThat(testValid3.getMessage()).isEqualTo(VALID_NAME);
    }
}
