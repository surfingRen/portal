package ren.maichu.portal.api.dingcan.websocket;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtils {

	public static Map<String, Session> clients = new ConcurrentHashMap<>();

	public static void put(String relationId, String userCode, Session session) {
		clients.put(getKey(relationId, userCode), session);
	}

	public static Session get(String relationId, String userCode) {
		return clients.get(getKey(relationId, userCode));
	}

	public static void remove(String relationId, String userCode) {
		clients.remove(getKey(relationId, userCode));
	}

	public static boolean hasConnection(String relationId, String userCode) {
		return clients.containsKey(getKey(relationId, userCode));
	}

	public static String getKey(String relationId, String userCode) {
		return relationId + "_" + userCode;
	}

}