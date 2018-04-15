package boyi.battleship.core.playerspecific.chat;

import boyi.battleship.core.chat.ChatMessage;
import boyi.battleship.core.player.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("playerSpecificChatMessageGenerator")
public class PlayerSpecificChatMessageGenerator {
    @NotNull
    public Optional<PlayerSpecificChatMessage> generate(@NotNull ChatMessage message, @NotNull Player player) {
        PlayerSpecificChatMessage result = null;
        Player sender = message.getSender();

        boolean messageVisible = player.isSpectator() || !sender.isSpectator();
        if (messageVisible) {
            result = new PlayerSpecificChatMessage(
                    message.getId(),
                    sender.getName(),
                    sender.getId(),
                    message.getMessageTimestamp(),
                    message.getMessage()
            );
        }

        return Optional.ofNullable(result);
    }

    @NotNull
    public List<PlayerSpecificChatMessage> generate(@NotNull List<ChatMessage> messages, @NotNull Player player) {
        return messages.stream()
                .map((m) -> generate(m, player))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
