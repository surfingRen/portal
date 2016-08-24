package ren.maichu.portal.api.dingcan.websocket;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/hello")
public class MyEndpoint {

	@OnOpen
	public void onOpen() {
		System.out.println("123132123");
	}

	@OnMessage
	public String onMessage(String message) {
		return "Got your message (" + message + ").Thanks !";
	}
}
