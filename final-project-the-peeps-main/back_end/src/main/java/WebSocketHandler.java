import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class WebSocketHandler {

    private static List<String> listings = new Vector<>();
    private static Map<Session,Session> sessionMap = new ConcurrentHashMap<>();

    public static void broadcast(String listing) {
        sessionMap.keySet().forEach(session -> {
            try {
                System.out.println("Broadcasting to client");
                session.getRemote().sendString(listing);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @OnWebSocketConnect
    public void connected(Session session) throws IOException {
        sessionMap.put(session, session); // remember all sessions
        System.out.println(("Client has connected!"));
        Gson gson = new Gson();
        // testing to see messages
        session.getRemote().sendString(gson.toJson(listings));
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason){
        sessionMap.remove(session);
        System.out.println("Client has been disconnected!");
    }

    @OnWebSocketMessage
    public void message(Session session, String listing){
        System.out.println("Client has sent: " + listing);
        listings.add(listing); // store messages
        // trigger a broadcast
        Gson gson = new Gson();
        broadcast(gson.toJson(listings));
    }

    @OnWebSocketError
    public void error(Session session, Throwable error){
        sessionMap.put(session, session);
        System.out.println(("Client has Error"));
        Gson gson = new Gson();
        // testing to see messages
        try {
            session.getRemote().sendString(gson.toJson(listings));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
