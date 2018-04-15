package boyi.battleship.server.validators;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.player.Player;
import boyi.battleship.server.validators.impl.PlayerNameValidatorImpl;
import boyi.battleship.server.validators.impl.RequestValidatorImpl;
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
public class RequestValidatorVlidateCreateGameRequestTest {
    private static final String VALID = ValidationResult.VALID;

    private static ValidationResult testEmptyPlayerName;
    private static ValidationResult testInvalidPlayerName;
    private static ValidationResult testInvalidMaxSpectators1;
    private static ValidationResult testInvalidMaxSpectators2;
    private static ValidationResult testCreateAsSpectatorWithSpectatorsNotAllowed;
    private static ValidationResult testValid1, testValid2, testValid3, testValid4;
    private static ValidationResult testValid5, testValid6, testValid7, testValid8;

    @Autowired
    private RequestValidator requestValidator;

    private boolean setupComplete = false;

    @Before
    public void setup() {
        if (!setupComplete) {
            String maxLongPlayerName = generateLongString(Player.MAX_NAME_LENGTH);
            testEmptyPlayerName = requestValidator.validateCreateGameRequest("", 0, false);
            testInvalidPlayerName = requestValidator.validateCreateGameRequest(maxLongPlayerName + "z", 0, false);
            testInvalidMaxSpectators1 = requestValidator.validateCreateGameRequest(maxLongPlayerName, -10, false);
            testInvalidMaxSpectators2 = requestValidator.validateCreateGameRequest(maxLongPlayerName, Game.SPECTATORS_MAX + 1, false);
            testCreateAsSpectatorWithSpectatorsNotAllowed = requestValidator.validateCreateGameRequest(maxLongPlayerName, 0, true);
            testValid1 = requestValidator.validateCreateGameRequest("Test", Game.UNLIMITED_SPECTATORS, true);
            testValid2 = requestValidator.validateCreateGameRequest(maxLongPlayerName, Game.UNLIMITED_SPECTATORS, true);
            testValid3 = requestValidator.validateCreateGameRequest(maxLongPlayerName, Game.UNLIMITED_SPECTATORS, false);
            testValid4 = requestValidator.validateCreateGameRequest(maxLongPlayerName, 0, false);
            testValid5 = requestValidator.validateCreateGameRequest(maxLongPlayerName, 1, false);
            testValid6 = requestValidator.validateCreateGameRequest(maxLongPlayerName, 1, true);
            testValid7 = requestValidator.validateCreateGameRequest(maxLongPlayerName, Game.SPECTATORS_MAX, true);
            testValid8 = requestValidator.validateCreateGameRequest(maxLongPlayerName, Game.SPECTATORS_MAX, false);

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
    public void testInvalidMaxSpectators1() {
        assertThat(testInvalidMaxSpectators1.isValid()).isEqualTo(false);
        assertThat(testInvalidMaxSpectators1.getMessage()).isEqualTo(RequestValidatorImpl.INVALID_MAX_SPECTATORS);
    }

    @Test
    public void testInvalidMaxSpectators2() {
        assertThat(testInvalidMaxSpectators2.isValid()).isEqualTo(false);
        assertThat(testInvalidMaxSpectators2.getMessage()).isEqualTo(RequestValidatorImpl.INVALID_MAX_SPECTATORS);
    }

    @Test
    public void testCreateAsSpectatorWithSpectatorsNotAllowed() {
        assertThat(testCreateAsSpectatorWithSpectatorsNotAllowed.isValid()).isEqualTo(false);
        assertThat(testCreateAsSpectatorWithSpectatorsNotAllowed.getMessage())
                .isEqualTo(RequestValidatorImpl.CANT_CREATE_GAME_WITHOUT_SPECTATORS_AS_SPECTATOR);
    }

    @Test
    public void testValid1() {
        assertThat(testValid1.isValid()).isEqualTo(true);
        assertThat(testValid1.getMessage()).isEqualTo(VALID);
    }

    @Test
    public void testValid2() {
        assertThat(testValid2.isValid()).isEqualTo(true);
        assertThat(testValid2.getMessage()).isEqualTo(VALID);
    }

    @Test
    public void testValid3() {
        assertThat(testValid3.isValid()).isEqualTo(true);
        assertThat(testValid3.getMessage()).isEqualTo(VALID);
    }

    @Test
    public void testValid4() {
        assertThat(testValid4.isValid()).isEqualTo(true);
        assertThat(testValid4.getMessage()).isEqualTo(VALID);
    }

    @Test
    public void testValid5() {
        assertThat(testValid5.isValid()).isEqualTo(true);
        assertThat(testValid5.getMessage()).isEqualTo(VALID);
    }

    @Test
    public void testValid6() {
        assertThat(testValid6.isValid()).isEqualTo(true);
        assertThat(testValid6.getMessage()).isEqualTo(VALID);
    }

    @Test
    public void testValid7() {
        assertThat(testValid7.isValid()).isEqualTo(true);
        assertThat(testValid7.getMessage()).isEqualTo(VALID);
    }

    @Test
    public void testValid8() {
        assertThat(testValid7.isValid()).isEqualTo(true);
        assertThat(testValid7.getMessage()).isEqualTo(VALID);
    }
}
