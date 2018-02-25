package boyi.battleship.server.validators;

import boyi.battleship.core.chat.ChatMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("chatMessageValidator")
public class ChatMessageValidator {
    @NotNull
    public ValidationResult validate(@NotNull String message) {
        if (message.isEmpty()) {
            return new ValidationResult(false, "Message can't be empty");
        }

        if (message.length() > ChatMessage.MAX_LENGTH) {
            return new ValidationResult(false,
                    "Message is too long (max length: " + ChatMessage.MAX_LENGTH + ")");
        }

        return new ValidationResult(true, "Message is valid");
    }
}