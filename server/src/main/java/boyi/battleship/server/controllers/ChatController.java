package boyi.battleship.server.controllers;

import boyi.battleship.core.gamemanager.GameManager;
import boyi.battleship.core.player.Player;
import boyi.battleship.core.playerspecific.chat.PlayerSpecificChatMessage;
import boyi.battleship.server.requests.SendChatMessageRequest;
import boyi.battleship.server.response.BattleshipResponse;
import boyi.battleship.server.response.ResponseBuilder;
import boyi.battleship.server.validators.ChatMessageValidator;
import boyi.battleship.server.validators.ValidationResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final GameManager gameManager;
    private final ResponseBuilder responseBuilder;
    private final ChatMessageValidator chatMessageValidator;

    @Autowired
    public ChatController(@NotNull GameManager gameManager, @NotNull ResponseBuilder responseBuilder,
                          @NotNull ChatMessageValidator chatMessageValidator) {
        this.gameManager = gameManager;
        this.responseBuilder = responseBuilder;
        this.chatMessageValidator = chatMessageValidator;
    }

    @RequestMapping(value = "/history", method = RequestMethod.GET)
    private BattleshipResponse getChatHistory(@RequestParam("playerToken") String playerToken) {
        Optional<Player> authorizedPlayer = gameManager.authorize(playerToken);
        if (!authorizedPlayer.isPresent()) {
            return responseBuilder.buildErrorResponse("Invalid player token");
        }

        Player player = authorizedPlayer.get();

        List<PlayerSpecificChatMessage> chatHistory = gameManager.getChatHistoryFor(player);

        return responseBuilder.buildChatHistoryResponse(chatHistory);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    private BattleshipResponse sendMessage(@RequestBody SendChatMessageRequest request) {
        Optional<Player> authorizedPlayer = gameManager.authorize(request.getPlayerToken());
        if (!authorizedPlayer.isPresent()) {
            return responseBuilder.buildErrorResponse("Invalid player token");
        }

        String message = request.getMessage();

        ValidationResult validationResult = chatMessageValidator.validate(message);
        if (!validationResult.isValid()) {
            return responseBuilder.buildErrorResponse("Unable to send the message: " + validationResult.getMessage());
        }

        String messageId = gameManager.sendMessage(authorizedPlayer.get(), message);

        return responseBuilder.buildSendChatMessageResponse(messageId);
    }
}
