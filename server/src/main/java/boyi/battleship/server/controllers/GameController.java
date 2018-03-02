package boyi.battleship.server.controllers;

import boyi.battleship.core.game.Game;
import boyi.battleship.core.gamemanager.GameManager;
import boyi.battleship.core.gamemanager.JoinGameResult;
import boyi.battleship.core.gamemanager.SubmitShipDataResult;
import boyi.battleship.core.player.Player;
import boyi.battleship.server.requests.SubmitShipsRequest;
import boyi.battleship.server.response.BattleshipResponse;
import boyi.battleship.server.response.ResponseBuilder;
import boyi.battleship.server.shipparser.ParsedShipData;
import boyi.battleship.server.shipparser.ShipParser;
import boyi.battleship.server.validators.RequestValidator;
import boyi.battleship.server.validators.ValidationResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


// TODO: probably shouldn't use Player and (especially) Game in this class
@RestController
@RequestMapping("/game")
public class GameController {
    private final RequestValidator requestValidator;
    private final GameManager gameManager;
    private final ResponseBuilder responseBuilder;
    private final ShipParser shipParser;

    @Autowired
    public GameController(@NotNull RequestValidator requestValidator, @NotNull GameManager gameManager,
                          @NotNull ResponseBuilder responseBuilder, @NotNull ShipParser shipParser) {
        this.requestValidator = requestValidator;
        this.gameManager = gameManager;
        this.responseBuilder = responseBuilder;
        this.shipParser = shipParser;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private BattleshipResponse createGame(
            @RequestParam(name = "playerName") String playerName,
            @RequestParam(name = "maxSpectators") int maxSpectators,
            @RequestParam(name = "joinAsSpectator", required = false, defaultValue = "false") boolean joinAsSpectator) {
        ValidationResult validationResult = requestValidator.validateCreateGameRequest(
                playerName, maxSpectators, joinAsSpectator);

        if (!validationResult.isValid()) {
            return responseBuilder.buildErrorResponse("Unable to create the game: " + validationResult.getMessage());
        }

        JoinGameResult joinGameResult = gameManager.createAndJoinGame(playerName, maxSpectators, joinAsSpectator);

        if (!joinGameResult.isSuccess()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: " + joinGameResult.getErrorMessage());
        }

        return responseBuilder.buildJoinGameResponse(
                joinGameResult.getPlayerToken(),
                joinGameResult.getPlayerId(),
                joinGameResult.getGameKey(),
                joinGameResult.isPlayerA()
        );
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    private BattleshipResponse joinGame(
            @RequestParam(name = "playerName") String playerName,
            @RequestParam(name = "gameKey") String gameKey,
            @RequestParam(name = "joinAsSpectator", required = false, defaultValue = "false") boolean joinAsSpectator) {
        gameKey = gameKey.toLowerCase();

        ValidationResult validationResult = requestValidator.validateJoinGameRequest(playerName, gameKey);

        if (!validationResult.isValid()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: " + validationResult.getMessage());
        }

        Optional<Game> game = gameManager.getGame(gameKey);
        if (!game.isPresent()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: game associated with the provided key doesn't exist");
        }

        JoinGameResult joinGameResult = gameManager.tryJoinPlayer(game.get(), playerName, joinAsSpectator);

        if (!joinGameResult.isSuccess()) {
            return responseBuilder.buildErrorResponse("Unable to join the game: " + joinGameResult.getErrorMessage());
        }

        return responseBuilder.buildJoinGameResponse(
                joinGameResult.getPlayerToken(),
                joinGameResult.getPlayerId(),
                joinGameResult.getGameKey(),
                joinGameResult.isPlayerA()
        );
    }

    @RequestMapping(value = "/submitShips", method = RequestMethod.POST)
    private BattleshipResponse submitShips(@RequestBody SubmitShipsRequest request) {
        Optional<Player> player = gameManager.authorize(request.getPlayerToken());
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
    private BattleshipResponse getState(@RequestParam(name = "playerToken") String playerToken) {
        return gameManager.authorize(playerToken)
                .map(player -> responseBuilder.buildGameStateResponse(gameManager.getGameStateFor(player)))
                .orElseGet(() -> responseBuilder.buildErrorResponse("Invalid player token"));
    }

    @RequestMapping(value = "/getExtendedState", method = RequestMethod.GET)
    private BattleshipResponse getExtendedGameState(@RequestParam(name = "playerToken") String playerToken) {
        Optional<Player> player = gameManager.authorize(playerToken);
        if (!player.isPresent()) {
            return responseBuilder.buildErrorResponse("Invalid player token");
        }

        return responseBuilder.buildExtendedGameStateResponse(gameManager.getExtendedGameStateFor(player.get()));
    }

    @RequestMapping(value = "/getEventHistory", method = RequestMethod.GET)
    private BattleshipResponse getEventHistory(@RequestParam("playerToken") String playerToken) {
        Optional<Player> authorizedPlayer = gameManager.authorize(playerToken);
        if (!authorizedPlayer.isPresent()) {
            return responseBuilder.buildErrorResponse("Invalid player token");
        }

        Player player = authorizedPlayer.get();

        return responseBuilder.buildEventHistoryResponse(gameManager.getEventHistoryFor(player));
    }
}
