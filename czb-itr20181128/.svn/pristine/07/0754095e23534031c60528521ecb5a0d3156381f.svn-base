package com.agent.czb.client;

import com.agent.czb.client.dll.ParkDllUtils;
import com.agent.czb.common.chat.ChatType;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import io.socket.engineio.client.transports.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author linkai
 * @since 2016/5/25
 */
public class ChatClient {
    private final static String token = "b6e523d7-0471-434d-95b8-81758d9e30a3";
    private static Logger log = LoggerFactory.getLogger(ChatClient.class);
    private Socket socket;
    private String uri = "http://localhost:9092";

    public ChatClient() {
    }

    public ChatClient(String uri) {
        this.uri = uri;
    }

    public void connect(String uri) throws URISyntaxException {
        this.uri = uri;
        connect();
    }

    public void connect() throws URISyntaxException {
        IO.Options options = new IO.Options();
        options.transports = new String[]{WebSocket.NAME};
        options.forceNew = true;
        socket = IO.socket(uri, options);
        log.info("client uri : {}", uri);

        socket.io().on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Transport transport = (Transport) args[0];

                transport.on(Transport.EVENT_REQUEST_HEADERS, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        // 设置请求头
                        Map<String, List<String>> headers = (Map<String, List<String>>) args[0];
                        headers.put("Authorization", Collections.singletonList("TOKEN " + token));
                    }
                });

                transport.on(Transport.EVENT_RESPONSE_HEADERS, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                    }
                });
            }
        });
        // 连接事件
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length > 0) {
                    JSONObject obj = (JSONObject) args[0];
                    log.info("connect args {}", obj.toString());
                }
                log.info("connect......");
                // 登录
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("id", Constants.PARK_ID);
                    obj.put("parkCode", Constants.PARK_CODE);
                    socket.emit(ChatType.PARK_LOGIN, obj);
                    log.info("login message {}", obj.toString());
                } catch (JSONException ignored) {
                }
            }
        });
        // 断开链接事件
        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args.length > 0) {
                    Object obj = args[0];
                    log.info("disconnect : {}", obj.toString());
                }
                log.info("disconnect......");

            }
        });
        // 监听 停车场消息
        socket.on(ChatType.PARK_CHAT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject obj = (JSONObject) args[0];
                log.info("{} message {}", ChatType.PARK_CHAT, obj.toString());
                try {
                    String type = obj.get("type").toString();
                    // 正常
                    if (type.equals("100")) {
                        // 消息id
                        callPark(obj);
                    } else {
                        log.error("{} request failed ! {}", obj.toString());
                    }
                } catch (JSONException ignored) {
                }
            }
        });
        socket.connect();
        log.info("statr complate!");
    }

    private void callPark(JSONObject obj) throws JSONException {
//        String id = obj.get("id").toString();
//        String parkCode = obj.get("parkCode").toString();
        String message = obj.get("message").toString();
        String call = ParkDllUtils.call(message);
        obj.put("message", call);
        socket.emit(ChatType.PARK_CHAT, obj);
    }

    public void sendUserMsg(String to, String message) throws JSONException {
        // Sending an object  发送一个对象
        JSONObject obj = new JSONObject();
        obj.put("from", "");
        obj.put("to", to);
        obj.put("message", message);
        socket.emit(ChatType.USER_CHAT, obj);
    }

    public void sendRoomMsg(String message) throws JSONException {
        // Sending an object  发送一个对象
        JSONObject obj = new JSONObject();
        obj.put("message", message);
        socket.emit(ChatType.SYS_CHAT, obj);
    }

    public void disconnection() {
        socket.disconnect();
    }

    public void disconnect() {
        this.socket.disconnect();
    }
}
