/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.handler;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * For Test websocket
 *
 * @author Robert Li
 */
public class MyHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        List<String> cookieList = session.getHandshakeHeaders().get("Cookie");
        if (cookieList == null) {
            System.out.println("cookieList is null");
        } else {
            for (String cookie : cookieList) {
                System.out.println("cookie:" + cookie);
            }
        }
        System.out.println("start:" + session.getId());
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

class SendThread extends Thread {

    private final WebSocketSession session;
    private final int pid;
    private static int n = 0;

    public SendThread(WebSocketSession session) {
        this.session = session;
        pid = n++;
        System.out.println("new sendThread:" + pid);
    }

    @Override
    public void run() {
        String msg = "message " + pid + " ";
        try {
            for (int i = 0; i < 1000; i++) {
                synchronized (session) {
                    if (session.isOpen() == false) {
                        break;
                    }
                    TextMessage textMsg = new TextMessage(msg + (new Date()));
                    session.sendMessage(textMsg);
                }
                Thread.sleep(1000);
            }
        } catch (IOException ex) {
            Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SendThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

class DtoMessage implements WebSocketMessage<String> {

    private final String payload = "hello, payload";

    @Override
    public String getPayload() {
        return payload;
    }

    @Override
    public int getPayloadLength() {
        return payload.length();
    }

    @Override
    public boolean isLast() {
        return false;
    }

}
