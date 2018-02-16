package boyi.battleship.server.controllers;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.gamemanager.GameManager;
import boyi.battleship.core.gamemanager.JoinGameResult;
import boyi.battleship.core.gamemanager.SubmitShipDataResult;
import boyi.battleship.core.player.Player;
import boyi.battleship.server.PlayerSpecificGameState.PlayerSpecificGameState;
import boyi.battleship.server.PlayerSpecificGameState.PlayerSpecificGameStateGenerator;
import boyi.battleship.server.requests.RequestWithPlayerToken;
import boyi.battleship.server.requests.SubmitShipsRequest;
import boyi.battleship.server.response.BattleshipResponse;
import boyi.battleship.server.response.ResponseBuilder;
import boyi.battleship.core.store.GameStore;
import boyi.battleship.server.shipparser.ParsedShipData;
import boyi.battleship.server.shipparser.ShipParser;
import boyi.battleship.server.validators.RequestValidator;
import boyi.battleship.server.validators.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ShipParser shipParser;

    @Autowired
    private PlayerSpecificGameStateGenerator playerSpecificGameStateGenerator;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
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

        if (joinGameResult.isSuccess()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: " + joinGameResult.getErrorMessage());
        }

        return responseBuilder.buildJoinGameResponse(joinGameResult.getPlayerToken(), joinGameResult.getGameKey());
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
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

        if (!joinGameResult.isSuccess()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: " + joinGameResult.getErrorMessage());
        }

        return responseBuilder.buildJoinGameResponse(joinGameResult.getPlayerToken(), joinGameResult.getGameKey());
    }

    @RequestMapping(value = "/submitShips", method = RequestMethod.POST)
    private BattleshipResponse submitShips(@RequestBody SubmitShipsRequest request) {
        Optional<Player> player = gameManager.authorize(request);
        if (!player.isPresent()) {
            return responseBuilder.buildErrorResponse("Invalid player token");
        }

        ParsedShipData parsedShipData = shipParser.parse(request.getShips());
        if (!parsedShipData.isSuccess()) {
            return responseBuilder.buildErrorResponse("Invalid ship data provided");
        }

        SubmitShipDataResult submitShipsResult = gameManager.submitShipData(player.get(), parsedShipData.getShips());
        if (!submitShipsResult.isSuccess()) {
            return responseBuilder.buildErrorResponse("Unable to submit ships: " + submitShipsResult.getErrorMessage());
        }

        return responseBuilder.build(true);
    }

    @RequestMapping(value = "/getState", method = RequestMethod.GET)
    private BattleshipResponse getState(@RequestBody RequestWithPlayerToken request) {
        Optional<Player> authorizedPlayer = gameManager.authorize(request);
        if (!authorizedPlayer.isPresent()) {
            return responseBuilder.buildErrorResponse("Invalid player token");
        }

        Player player = authorizedPlayer.get();

        PlayerSpecificGameState gameState = playerSpecificGameStateGenerator.generate(
                gameManager.getGameState(player), player
        );

        return responseBuilder.buildGameStateResponse(gameState);
    }

//    @RequestMapping("/waitMyTurn")
//    private BattleshipResponse waitForMyTurn(@RequestBody RequestWithPlayerToken request) {
//        Optional<Player> authorizedPlayer = gameManager.authorize(request);
//        if (!authorizedPlayer.isPresent()) {
//            return responseBuilder.buildErrorResponse("Invalid player token");
//        }
//
//        Player player = authorizedPlayer.get();
//
//        GameState gameState = gameManager.waitForPlayerTurn(player);
//
//        return responseBuilder.buildGameStateResponse(gameState);
//    }
}
