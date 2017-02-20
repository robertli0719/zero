/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author Robert Li
 */
public class ChatHandler extends TextWebSocketHandler {
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        
        System.out.println("start:" + session.getId() + "\t uid:" + session.getAttributes().get("uid"));
        new SendThread(session).start();
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1) throws Exception {
        System.out.println("close:" + session.getId());
    }
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("handleTextMessage: " + message.getPayload());
    }
    
}
