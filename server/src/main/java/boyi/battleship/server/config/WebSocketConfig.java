package boyi.battleship.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    public static final String MAIN_WEBSOCKET_URL = "/battleship";
    public static final String CHAT_WEBSOCKET_URL = "/chat";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
        messageBrokerRegistry.enableSimpleBroker(MAIN_WEBSOCKET_URL, CHAT_WEBSOCKET_URL);
        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/socket").withSockJS();
    }
}
