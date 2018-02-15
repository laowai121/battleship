package boyi.battleship.server.controllers;

import boyi.battleship.server.game.Game;
import boyi.battleship.server.game.GameManager;
import boyi.battleship.server.game.JoinGameResult;
import boyi.battleship.server.response.BattleshipResponse;
import boyi.battleship.server.response.ResponseBuilder;
import boyi.battleship.server.store.GameStore;
import boyi.battleship.server.validators.RequestValidator;
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
    private RequestValidator requestValidator;

    @Autowired
    private GameStore gameStore;

    @Autowired
    private GameManager gameManager;

    @Autowired
    private ResponseBuilder responseBuilder;

    @RequestMapping("/create")
    private BattleshipResponse create(
            @RequestParam(name = "playerName") String playerName,
            @RequestParam(name = "maxSpectators") int maxSpectators,
            @RequestParam(name = "joinAsSpectator", required = false, defaultValue = "false") boolean joinAsSpectator) {
        ValidationResult validationResult = requestValidator.validateCreateGameRequest(
                playerName, maxSpectators, joinAsSpectator);

        if (!validationResult.isValid()) {
            return responseBuilder.buildErrorResponse("Unable to create the game: " + validationResult.getMessage());
        }

        JoinGameResult joinGameResult = gameManager.createAndJoinGame(playerName, maxSpectators, joinAsSpectator);
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
        gameKey = gameKey.toLowerCase();

        ValidationResult validationResult = requestValidator.validateJoinGameRequest(playerName, gameKey);

        if (!validationResult.isValid()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: " + validationResult.getMessage());
        }

        Optional<Game> game = gameStore.get(gameKey);
        if (!game.isPresent()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: game associated with the provided key doesn't exist");
        }

        JoinGameResult joinGameResult = gameManager.tryJoinPlayer(game.get(), playerName, joinAsSpectator);
        Optional<String> playerToken = joinGameResult.getPlayerToken();

        if (!joinGameResult.isSuccess() || !playerToken.isPresent()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: " + joinGameResult.getErrorMessage());
        }

        return responseBuilder.buildJoinGameResponse(playerToken.get(), joinGameResult.getGameKey());
    }
}
