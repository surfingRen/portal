package ren.maichu.portal.api.dingcan.websocket;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@ServerEndpoint("/websocket.ws/{channel}/{userCode}")
public class WebsocketEndPoint {

	private static Log log = LogFactory.getLog(WebsocketEndPoint.class);

	@OnOpen
	public void onOpen(@PathParam("channel") String channel, @PathParam("userCode") String userCode,
			Session session) {
		log.info("Websocket Start Connecting:" + SessionUtils.getKey(channel, userCode));
		SessionUtils.put(channel, userCode, session);
	}

	@OnMessage
	public String onMessage(@PathParam("channel") String channel, @PathParam("userCode") String userCode,
			String message, Session session) {
		return userCode + ": " + message;
	}

	@OnError
	public void onError(@PathParam("channel") String channel, @PathParam("userCode") String userCode,
			Throwable throwable, Session session) {
		log.info("Websocket Connection Exception:" + SessionUtils.getKey(channel, userCode));
		log.info(throwable.getMessage(), throwable);
		SessionUtils.remove(channel, userCode);
	}

	@OnClose
	public void onClose(@PathParam("channel") String channel, @PathParam("userCode") String userCode,
			Session session) {
		log.info("Websocket Close Connection:" + SessionUtils.getKey(channel, userCode));
		SessionUtils.remove(channel, userCode);
	}

}