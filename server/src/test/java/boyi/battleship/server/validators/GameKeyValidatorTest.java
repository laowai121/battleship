package boyi.battleship.server.validators;

import boyi.battleship.core.keygenerator.KeyGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameKeyValidatorTest {
    private static final String ERROR_INVALID_KEY = "Invalid Game Key. "
            + "Game Key should be a sequence of hexadecimal digits " + KeyGenerator.DEFAULT_KEY_LENGTH;
    private static final String VALID_KEY = "Game Key is valid";

    @Autowired
    private GameKeyValidator gameKeyValidator;

    private static ValidationResult testEmpty, testWhitespace, testInvalidCharacters, testTooShort;
    private static ValidationResult testTooLong, testUppercaseCharacter, testExtraSpace, testChineseCharacters;
    private static ValidationResult testRussianLetter, testNonHexDigit, testValid1, testValid2, testValid3;

    private boolean setupComplete = false;

    @Before
    public void setup() {
        if (!setupComplete) {
            testEmpty = gameKeyValidator.validate("");
            testWhitespace = gameKeyValidator.validate("        ");
            testInvalidCharacters = gameKeyValidator.validate("67AS_(&%");
            testTooShort = gameKeyValidator.validate("az5cd7bdnu66e4f");
            testTooLong = gameKeyValidator.validate("az5cd7bdnu66e4fvb");
            testUppercaseCharacter = gameKeyValidator.validate("az5cd7bdnu66E4fv");
            testExtraSpace = gameKeyValidator.validate("az5cd7bdnu66e4fv  ");
            testRussianLetter = gameKeyValidator.validate("az5cd7bdnu66Ы4fv");
            testChineseCharacters = gameKeyValidator.validate("哇哈哈哈哈");
            testNonHexDigit = gameKeyValidator.validate("aa5cb7bd4u66e4f5");
            testValid1 = gameKeyValidator.validate("aa5cb7bd4d66e4f5");
            testValid2 = gameKeyValidator.validate("1111111111111111");
            testValid3 = gameKeyValidator.validate("ffffffffffffffff");

            setupComplete = true;
        }
    }

    @Test
    public void testEmpty() {
        assertThat(testEmpty.isValid()).isEqualTo(false);
        assertThat(testEmpty.getMessage()).isEqualTo("Please, enter Game Key");
    }

    @Test
    public void testWhitespace() {
        assertThat(testWhitespace.isValid()).isEqualTo(false);
        assertThat(testWhitespace.getMessage()).isEqualTo(ERROR_INVALID_KEY);
    }

    @Test
    public void testInvalidCharacters() {
        assertThat(testInvalidCharacters.isValid()).isEqualTo(false);
        assertThat(testInvalidCharacters.getMessage()).isEqualTo(ERROR_INVALID_KEY);
    }

    @Test
    public void testTooShort() {
        assertThat(testTooShort.isValid()).isEqualTo(false);
        assertThat(testTooShort.getMessage()).isEqualTo(ERROR_INVALID_KEY);
    }

    @Test
    public void testTooLong() {
        assertThat(testTooLong.isValid()).isEqualTo(false);
        assertThat(testTooLong.getMessage()).isEqualTo(ERROR_INVALID_KEY);
    }

    @Test
    public void testUppercaseCharacter() {
        assertThat(testUppercaseCharacter.isValid()).isEqualTo(false);
        assertThat(testUppercaseCharacter.getMessage()).isEqualTo(ERROR_INVALID_KEY);
    }

    @Test
    public void testExtraSpace() {
        assertThat(testExtraSpace.isValid()).isEqualTo(false);
        assertThat(testExtraSpace.getMessage()).isEqualTo(ERROR_INVALID_KEY);
    }

    @Test
    public void testRussianLetter() {
        assertThat(testRussianLetter.isValid()).isEqualTo(false);
        assertThat(testRussianLetter.getMessage()).isEqualTo(ERROR_INVALID_KEY);
    }

    @Test
    public void testChineseCharacters() {
        assertThat(testChineseCharacters.isValid()).isEqualTo(false);
        assertThat(testChineseCharacters.getMessage()).isEqualTo(ERROR_INVALID_KEY);
    }

    @Test
    public void testNonHexDigit() {
        assertThat(testNonHexDigit.isValid()).isEqualTo(false);
        assertThat(testNonHexDigit.getMessage()).isEqualTo(ERROR_INVALID_KEY);
    }

    @Test
    public void testValid1() {
        assertThat(testValid1.isValid()).isEqualTo(true);
        assertThat(testValid1.getMessage()).isEqualTo(VALID_KEY);
    }

    @Test
    public void testValid2() {
        assertThat(testValid2.isValid()).isEqualTo(true);
        assertThat(testValid2.getMessage()).isEqualTo(VALID_KEY);
    }

    @Test
    public void testValid3() {
        assertThat(testValid3.isValid()).isEqualTo(true);
        assertThat(testValid3.getMessage()).isEqualTo(VALID_KEY);
    }
}
