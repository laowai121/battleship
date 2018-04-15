package boyi.battleship.server.validators;

import boyi.battleship.server.validators.impl.PlayerNameValidatorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static boyi.battleship.TestUtils.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerNameValidatorTest {
    private static final String VALID_NAME = PlayerNameValidatorImpl.PLAYER_NAME_VALID;

    @Autowired
    private PlayerNameValidator playerNameValidator;

    private static ValidationResult testEmpty, testWhitespace;
    private static ValidationResult testTooLong, testChineseCharacters;
    private static ValidationResult testValid1, testValid2, testValid3, testValid4;

    private boolean setupComplete = false;

    @Before
    public void setup() {
        if (!setupComplete) {
            testEmpty = playerNameValidator.validate("");
            testWhitespace = playerNameValidator.validate("        ");
            testTooLong = playerNameValidator.validate(generateLongString(21));
            testChineseCharacters = playerNameValidator.validate("哇哈哈哈哈");
            testValid1 = playerNameValidator.validate(generateLongString(18) + "阿拉");
            testValid2 = playerNameValidator.validate("Abcdef Ghijklm Nopqr");
            testValid3 = playerNameValidator.validate("Heitian Boyi");
            testValid4 = playerNameValidator.validate("Петренко Василь");

            setupComplete = true;
        }
    }

    @Test
    public void testEmpty() {
        assertThat(testEmpty.isValid()).isEqualTo(false);
        assertThat(testEmpty.getMessage()).isEqualTo(PlayerNameValidatorImpl.ENTER_PLAYER_NAME);
    }

    @Test
    public void testWhitespace() {
        assertThat(testWhitespace.isValid()).isEqualTo(true);
        assertThat(testWhitespace.getMessage()).isEqualTo(VALID_NAME);
    }

    @Test
    public void testTooLong() {
        assertThat(testTooLong.isValid()).isEqualTo(false);
        assertThat(testTooLong.getMessage()).isEqualTo(PlayerNameValidatorImpl.PLAYER_NAME_TOO_LONG);
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

    @Test
    public void testValid4() {
        assertThat(testValid4.isValid()).isEqualTo(true);
        assertThat(testValid4.getMessage()).isEqualTo(VALID_NAME);
    }
}
