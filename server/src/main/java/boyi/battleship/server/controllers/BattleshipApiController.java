package boyi.battleship.server.controllers;

import boyi.battleship.server.game.Game;
import boyi.battleship.server.game.GameManager;
import boyi.battleship.server.game.JoinGameResult;
import boyi.battleship.server.response.BattleshipResponse;
import boyi.battleship.server.response.ResponseBuilder;
import boyi.battleship.server.store.GameStore;
import boyi.battleship.server.validators.GameKeyValidator;
import boyi.battleship.server.validators.PlayerNameValidator;
import boyi.battleship.server.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/battleship")
public class BattleshipApiController {
    @Autowired
    private PlayerNameValidator playerNameValidator;

    @Autowired
    private GameKeyValidator gameKeyValidator;

    @Autowired
    private GameStore gameStore;

    @Autowired
    private GameManager gameManager;

    @Autowired
    private ResponseBuilder responseBuilder;

    @RequestMapping("/createAndJoin")
    private BattleshipResponse createAndJoin(
            @RequestParam(name = "playerName") String playerName,
            @RequestParam(name = "joinAsSpectator", required = false, defaultValue = "false") boolean joinAsSpectator) {
        ValidationResult validationResult = playerNameValidator.validate(playerName);

        if (!validationResult.isValid()) {
            return responseBuilder.buildErrorResponse("Unable to create the game. Invalid player name: " + validationResult.getMessage());
        }

        JoinGameResult joinGameResult = gameManager.createAndJoinGame(playerName, joinAsSpectator);
        Optional<String> playerToken = joinGameResult.getPlayerToken();

        if (!joinGameResult.isSuccess() || !playerToken.isPresent()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: " + joinGameResult.getErrorMessage());
        }

        return responseBuilder.buildJoinGameResponse(playerToken.get(), joinGameResult.getGameKey());
    }

    @RequestMapping("/join")
    private BattleshipResponse join(
            @RequestParam(name = "playerName") String playerName,
            @RequestParam(name = "gameKey") String gameKey,
            @RequestParam(name = "joinAsSpectator", required = false, defaultValue = "false") boolean joinAsSpectator) {
        ValidationResult validationResult = playerNameValidator.validate(playerName);
        if (!validationResult.isValid()) {
            return responseBuilder.buildErrorResponse("Unable to join the game. Invalid player name: " + validationResult.getMessage());
        }

        gameKey = gameKey.toLowerCase();

        validationResult = gameKeyValidator.validate(gameKey);
        if (!validationResult.isValid()) {
            return responseBuilder.buildErrorResponse("Unable to join the game. Invalid game key: " + validationResult.getMessage());
        }

        Optional<Game> game = gameStore.get(gameKey);
        if (!game.isPresent()) {
            return responseBuilder.buildErrorResponse("Unable to join the game. Game associated with the provided key doesn't exist");
        }

        JoinGameResult joinGameResult = gameManager.tryJoinPlayer(game.get(), playerName, joinAsSpectator);
        Optional<String> playerToken = joinGameResult.getPlayerToken();
        if (!joinGameResult.isSuccess() || !playerToken.isPresent()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: " + joinGameResult.getErrorMessage());
        }

        return responseBuilder.buildJoinGameResponse(playerToken.get(), joinGameResult.getGameKey());
    }
}
