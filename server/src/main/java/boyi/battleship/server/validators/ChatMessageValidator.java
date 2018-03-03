package boyi.battleship.server.validators;

import boyi.battleship.core.chat.ChatMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("chatMessageValidator")
public class ChatMessageValidator {
    public static final String MESSAGE_CANT_BE_EMPTY = "Message can't be empty";
    public static final String MESSAGE_TOO_LONG = "Message is too long (max length: " + ChatMessage.MAX_LENGTH + ")";
    public static final String MESSAGE_VALID = "Message is valid";

    @NotNull
    public ValidationResult validate(@NotNull String message) {
        if (message.isEmpty()) {
            return new ValidationResult(false, MESSAGE_CANT_BE_EMPTY);
        }

        if (message.length() > ChatMessage.MAX_LENGTH) {
            return new ValidationResult(false, MESSAGE_TOO_LONG);
        }

        return new ValidationResult(true, MESSAGE_VALID);
    }
}