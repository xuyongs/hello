package com.agent.czb.client;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author linkai
 * @since 16/8/10
 */
public class ChatClientTest {
    @Test
    public void test() throws Exception {
        ChatClient client = new ChatClient();
        client.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while (true) {
            line = br.readLine();
            if (line.startsWith("exit")) {
                break;
            }
            if (line.contains(" ")) {
                String[] split = line.split(" ", 3);
                System.out.println("msg : " + Arrays.toString(split));
                if (split[0].equals("1")) {
                    client.sendUserMsg(split[1].trim(), split[2].trim());
                } else {
                    if (split[1].trim().equals("n")) {
                        client.sendRoomMsg(split[2].trim());
                    } else {
                        client.sendRoomMsg(split[0].trim());
                    }
                }
            } else {
                client.sendRoomMsg(line);
            }
        }
        client.disconnection();
        System.out.println("end!!");
    }

    public static void main(String[] args) throws Exception {
        new ChatClientTest().test();
    }
}