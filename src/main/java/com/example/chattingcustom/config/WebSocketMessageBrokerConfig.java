package com.example.chattingcustom.config;


import com.example.chattingcustom.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    final StompHandler stompHandler;

    /**
     * websocket 연결 엔드 포인트 설정 메서드
     * */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS()
        ;
    }

    /**
     * 클라이언트에서 다른 클라이언트로 메세지 라우팅에 사용될 메시지 브로커
     * */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //해당 매개변수로 시작하는 요청을 구독한 모든 사용자들에게 메시지를 브로드캐스트
        registry.enableSimpleBroker("/sub");
        //해당 매개변수로 시작되는 메시지는 message-handling method 로 라우팅
        registry.setApplicationDestinationPrefixes("/pub");
    }

    /**
     * 클라이언트에서 들어오는 채널을 설정하는 메서드 추측
     * */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
