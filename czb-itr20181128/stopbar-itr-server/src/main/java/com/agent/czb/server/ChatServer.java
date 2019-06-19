package com.agent.czb.server;

import com.agent.czb.common.chat.ChatType;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.protocol.JacksonJsonSupport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linkai
 * @since 2016/5/24
 */
public class ChatServer {
    private static Logger log = LoggerFactory.getLogger(ChatServer.class);

    private static Map<String, UserInfo> users = new ConcurrentHashMap<>();
    private static Map<String, ParkInfo> parks = new ConcurrentHashMap<>();

    private SocketIOServer server;
    private int port = 9092;
    private String home = "localhost";

    public ChatServer() {
    }

    public ChatServer(String home, int port) {
        this.home = home;
        this.port = port;
    }

    public void start() {
        final Configuration config = new Configuration();
        config.setHostname(home);
        config.setPort(port);
        config.setJsonSupport(new JacksonJsonSupport());
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        config.setSocketConfig(socketConfig);
        config.setAuthorizationListener(new AuthorizationListener() {
            @Override
            public boolean isAuthorized(HandshakeData data) {
                // 获取head
                String auth = data.getHttpHeaders().get("Authorization");
                log.info("auth, {}", auth);
                return true;
            }
        });

        server = new SocketIOServer(config);
        // 添加conection监听
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                log.info("connect...  session-id : {} , current connect num : {}", client.getSessionId().toString(), server.getAllClients().size());
                // 广播用户上线
//                server.getBroadcastOperations().sendEvent(ChatType.SYS_CHAT, new SysMsg("user on line"));
            }
        });
        // 添加disconntciton监听
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                String id = client.getSessionId().toString();
                log.info("disconntciton...  session-id : {} , current connect num : {}", id, server.getAllClients().size());
                if (users.containsKey(id)) {
                    // 清楚用户
                    UserInfo user = users.remove(id);
                    log.info("user disconntciton...  session-id : {} , user-name : {}", id, user.getUserName());
                } else if (parks.containsKey(id)) {
                    // 清理停车场
                    ParkInfo park = parks.remove(id);
                    log.info("park disconntciton...  session-id : {} , park-code : {}", id, park.getParkCode());
                }
            }
        });
        // 添加用户登录监听
        server.addEventListener(ChatType.USER_LOGIN, UserInfo.class, new DataListener<UserInfo>() {
            @Override
            public void onData(SocketIOClient client, UserInfo data, AckRequest ackRequest) {
                log.info("{} message : {}", ChatType.USER_CHAT, data.toString());
                if (getUserInfo(data.getId()) != null) {
                    // 用户已存在
                    client.sendEvent(ChatType.USER_CHAT, new ChatType.UserMsg().setMessage("用户已存在"));
                    client.disconnect();
                } else {
                    users.put(client.getSessionId().toString(), data.setClient(client));
                }
            }
        });
        // 添加停车场登录监听
        server.addEventListener(ChatType.PARK_LOGIN, ParkInfo.class, new DataListener<ParkInfo>() {
            @Override
            public void onData(SocketIOClient client, ParkInfo data, AckRequest ackRequest) {
                log.info("{} message : {}", ChatType.PARK_LOGIN, data.getParkCode());
                if (getParkInfo(data.getParkCode()) != null) {
                    // 停车场已存在
                    client.sendEvent(ChatType.PARK_CHAT, new ChatType.ParkMsg().setType("101").setMessage("停车场已存在"));
                    client.disconnect();
                } else {
                    parks.put(client.getSessionId().toString(), data.setClient(client));
                }
            }
        });
        // 添加用户消息监听
        server.addEventListener(ChatType.USER_CHAT, ChatType.UserMsg.class, new DataListener<ChatType.UserMsg>() {
            @Override
            public void onData(SocketIOClient client, ChatType.UserMsg data, AckRequest ackRequest) {
                log.info("{} message : {}", ChatType.USER_CHAT, data.toString());
                UserInfo userInfo = users.get(data.getTo());
                if (userInfo == null) {
                    // 用户不存在
                    client.sendEvent(ChatType.USER_CHAT, new ChatType.UserMsg().setMessage("用户不存在"));
                    return;
                }
                data.setFrom(client.getSessionId().toString());
                userInfo.getClient().sendEvent(ChatType.USER_CHAT, data);
            }
        });
        // 添加停车场消息监听
        server.addEventListener(ChatType.PARK_CHAT, ChatType.ParkMsg.class, new DataListener<ChatType.ParkMsg>() {
            @Override
            public void onData(SocketIOClient client, ChatType.ParkMsg data, AckRequest ackRequest) {
                log.info("{} message : {}", ChatType.PARK_CHAT, data.toString());
                ParkInfo parkInfo = parks.get(client.getSessionId().toString());
                if (parkInfo == null) {
                    // 停车场不存在
                    client.sendEvent(ChatType.PARK_CHAT, new ChatType.ParkMsg().setType("101").setMessage("停车场不存在"));
                } else {
                    ParkMsgHandle.setData(data.getId(), data.getMessage());
                }
            }
        });
        // 添加用户消息监听
        server.addEventListener(ChatType.SYS_CHAT, ChatType.SysMsg.class, new DataListener<ChatType.SysMsg>() {
            @Override
            public void onData(SocketIOClient client, ChatType.SysMsg data, AckRequest ackRequest) {
                log.info("{} message : {}", ChatType.SYS_CHAT, data.toString());
                server.getBroadcastOperations().sendEvent(ChatType.SYS_CHAT, new ChatType.SysMsg().setMessage(data.toString()));
            }
        });

        server.start();
    }

    public ParkMsgHandle.HandlerComplete<String> send(String parkCode, String message) {
        ParkInfo parkInfo = getParkInfo(parkCode);
        if (parkInfo != null) {
            String msgId = UUID.randomUUID().toString();
            ChatType.ParkMsg parkMsg = new ChatType.ParkMsg().setType("100").setParkCode(parkCode).setId(msgId).setMessage(message);
            parkInfo.getClient().sendEvent(ChatType.PARK_CHAT, parkMsg);
            return ParkMsgHandle.create(msgId);
        }
        return null;
    }

    private ParkInfo getParkInfo(String parkCode) {
        for (String key : parks.keySet()) {
            ParkInfo parkInfo = parks.get(key);
            if (parkInfo.getParkCode().equals(parkCode)) {
                return parkInfo;
            }
        }
        return null;
    }

    private UserInfo getUserInfo(String userId) {
        for (String key : users.keySet()) {
            UserInfo userInfo = users.get(key);
            if (userInfo.getId().equals(userId)) {
                return userInfo;
            }
        }
        return null;
    }

    public void stop() {
        server.stop();
    }

    //        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(10 * 1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println("current user num : " + users.size() + ",  client num : " + server.getAllClients().size());
//                    for (SocketIOClient client : server.getAllClients()) {
//                        client.sendEvent("sysMsg", new ChatType.SysMsg("current user num : " + users.size()));
//                    }
//                }
//            }
//        }).start();



    @Data
    @ToString
    @Accessors(chain = true)
    public static class UserInfo {
        private String id;
        private String userName;
        private SocketIOClient client;
    }

    @Data
    @ToString
    @Accessors(chain = true)
    public static class ParkInfo {
        private String id;
        private String parkCode;
        private SocketIOClient client;
    }

}
