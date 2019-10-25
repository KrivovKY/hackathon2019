package five.bits.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import five.bits.data.dto.Car;
import five.bits.data.dto.Point;
import five.bits.data.dto.Route;
import five.bits.data.dto.Traffic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class DataProvider {

    private static final Logger LOGGER = LogManager.getLogger(DataProvider.class);

    private String url;
    private String team;
    private String token = null;
    private List<Car> cars = new LinkedList<>();
    private List<Route> routes = new LinkedList<>();
    private List<Point> points = new LinkedList<>();
    private List<Traffic> traffic = new LinkedList<>();

    private CountDownLatch latch;

    private WebSocketSession webSocketSession;

    public DataProvider(String url) throws ExecutionException, InterruptedException {
        this.url = url;
        connect();
    }

    private void connect() throws ExecutionException, InterruptedException {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        webSocketSession = webSocketClient.doHandshake(new TextWebSocketHandler() {
            @Override
            public void handleTextMessage(WebSocketSession session, TextMessage message) {
                //LOGGER.debug("received message - " + message.getPayload());
                String[] split = message.getPayload().replaceAll("}\\n\\{", "}#!#{").split("#!#");
                for (String s : split) {
                    parseResponse(s);
                }
            }

            @Override
            public void afterConnectionEstablished(WebSocketSession session) {
                LOGGER.info("established connection - " + session);
            }
        }, new WebSocketHttpHeaders(), URI.create(url)).get();
        webSocketSession.setTextMessageSizeLimit(1024 * 1024 * 10);
    }

    private void reconnect() throws ExecutionException, InterruptedException, IOException {
        LOGGER.info("reconnect()");
        connect();
        send(new TeamReconnectMessage(this.team));
        send(new ReconnectMessage(this.token));
    }

    public void close() throws IOException {
        if (null != webSocketSession) {
            webSocketSession.close();
        }
    }

    public void send(Object o) throws IOException, InterruptedException, ExecutionException {
        if (o instanceof TeamMessage) {
            this.team = ((TeamMessage) o).getTeam();
            latch = new CountDownLatch(4);
        } else if (o instanceof TeamReconnectMessage) {
            latch = new CountDownLatch(1);
        } else if (o instanceof GotoMessage) {
            latch = new CountDownLatch(2);
        } else if (o instanceof ReconnectMessage) {
            latch = new CountDownLatch(0);
        } else {
            throw new IllegalArgumentException(o.getClass().getName());
        }
        ObjectMapper mapper = new ObjectMapper();
        String msg = mapper.writeValueAsString(o);
        LOGGER.info("send message: {}", msg);
        try {
            webSocketSession.sendMessage(new TextMessage(msg));
        } catch (IllegalStateException | IOException e) {
            reconnect();
            LOGGER.info("resend message: {}", msg);
            webSocketSession.sendMessage(new TextMessage(msg));
        }
        latch.await();
    }

    @SuppressWarnings("unchecked")
    private void parseResponse(String payload) {
        LOGGER.debug("parseResponse: {}", payload);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> resp = mapper.readValue(payload, new TypeReference<Map<String, Object>>() {
            });
            resp.keySet().forEach(k -> {
                LOGGER.debug("key {}; type: {};  value {}", k, resp.get(k).getClass(), resp.get(k));
                try {
                    switch (k) {
                        case "token":
                            if (null == this.token) {
                                this.token = resp.get(k).toString();
                            }
                            latch.countDown();
                            break;
                        case "cars":
                            cars = new LinkedList<>();
                            ((List<String>) resp.get(k)).forEach(c -> cars.add(new Car(c)));
                            break;
                        case "level":
                            LOGGER.warn("skip `level`");
                            break;
                        case "routes":
                            routes = mapper.readValue(payload, new TypeReference<Map<String, List<Route>>>() {
                            }).get(k);
                            latch.countDown();
                            break;
                        case "points":
                            points = mapper.readValue(payload, new TypeReference<Map<String, List<Point>>>() {
                            }).get(k);
                            latch.countDown();
                            break;
                        case "traffic":
                            traffic = mapper.readValue(payload, new TypeReference<Map<String, List<Traffic>>>() {
                            }).get(k);
                            latch.countDown();
                            break;
                        case "carsum":
                            LOGGER.debug("goto response");
                            latch.countDown();
                        case "point":
                        case "car":
                            break;
                        default:
                            LOGGER.error("unknown key " + k);
                    }
                } catch (IOException e) {
                    LOGGER.error(e.toString(), e);
                }
            });
        } catch (IOException e) {
            LOGGER.error(e.toString(), e);
        }

    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Traffic> getTraffic() {
        return traffic;
    }
}
