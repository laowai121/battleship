package boyi.battleship.server.validators;

import boyi.battleship.core.keygenerator.KeyGenerator;
import boyi.battleship.core.player.Player;
import boyi.battleship.server.validators.impl.GameKeyValidatorImpl;
import boyi.battleship.server.validators.impl.PlayerNameValidatorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static boyi.battleship.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RequestValidatorVlidateJoinGameRequestTest {
    private static ValidationResult testEmptyPlayerName;
    private static ValidationResult testInvalidPlayerName;
    private static ValidationResult testEmptyGameKey;
    private static ValidationResult testInvalidGameKey1;
    private static ValidationResult testInvalidGameKey2;
    private static ValidationResult testValid1, testValid2, testValid3;

    @Autowired
    private RequestValidator requestValidator;

    private boolean setupComplete = false;

    @Before
    public void setup() {
        if (!setupComplete) {
            String maxLongPlayerName = generateLongString(Player.MAX_NAME_LENGTH);

            testEmptyPlayerName = requestValidator.validateJoinGameRequest("", "");
            testInvalidPlayerName =
                    requestValidator.validateJoinGameRequest(maxLongPlayerName + 'z', "");
            testEmptyGameKey = requestValidator.validateJoinGameRequest(maxLongPlayerName, "");
            testInvalidGameKey1 = requestValidator.validateJoinGameRequest(maxLongPlayerName, "*(#UIHFDJH");
            testInvalidGameKey2 =
                    requestValidator.validateJoinGameRequest(
                            maxLongPlayerName,
                            generateLongString(KeyGenerator.DEFAULT_KEY_LENGTH) + 1
                    );
            testValid1 =
                    requestValidator.validateJoinGameRequest(
                            maxLongPlayerName,
                            generateLongString(KeyGenerator.DEFAULT_KEY_LENGTH, '8')
                    );
            testValid2 = requestValidator.validateJoinGameRequest("Test", "1234567890abcdef");
            testValid3 = requestValidator.validateJoinGameRequest("T", "ffffffffffffffff");

            setupComplete = true;
        }
    }

    @Test
    public void testEmptyPlayerName() {
        assertThat(testEmptyPlayerName.isValid()).isEqualTo(false);
        assertThat(testEmptyPlayerName.getMessage()).isEqualTo(PlayerNameValidatorImpl.ENTER_PLAYER_NAME);
    }

    @Test
    public void testInvalidPlayerName() {
        assertThat(testInvalidPlayerName.isValid()).isEqualTo(false);
        assertThat(testInvalidPlayerName.getMessage()).isEqualTo(PlayerNameValidatorImpl.PLAYER_NAME_TOO_LONG);
    }

    @Test
    public void testEmptyGameKey() {
        assertThat(testEmptyGameKey.isValid()).isEqualTo(false);
        assertThat(testEmptyGameKey.getMessage()).isEqualTo(GameKeyValidatorImpl.ENTER_GAME_KEY);
    }

    @Test
    public void testInvalidGameKey1() {
        assertThat(testInvalidGameKey1.isValid()).isEqualTo(false);
        assertThat(testInvalidGameKey1.getMessage()).isEqualTo(GameKeyValidatorImpl.INVALID_GAME_KEY);
    }

    @Test
    public void testInvalidGameKey2() {
        assertThat(testInvalidGameKey2.isValid()).isEqualTo(false);
        assertThat(testInvalidGameKey2.getMessage()).isEqualTo(GameKeyValidatorImpl.INVALID_GAME_KEY);
    }

    @Test
    public void testValid1() {
        assertThat(testValid1.isValid()).isEqualTo(true);
        assertThat(testValid1.getMessage()).isEqualTo(ValidationResult.VALID);
    }

    @Test
    public void testValid2() {
        assertThat(testValid2.isValid()).isEqualTo(true);
        assertThat(testValid2.getMessage()).isEqualTo(ValidationResult.VALID);
    }

    @Test
    public void testValid3() {
        assertThat(testValid3.isValid()).isEqualTo(true);
        assertThat(testValid3.getMessage()).isEqualTo(ValidationResult.VALID);
    }
}
