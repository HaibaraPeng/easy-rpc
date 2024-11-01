package org.example.rpc.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ip工具类
 *
 * @Author Roc
 * @Date 2024/10/30 17:31
 */
public class IpUtil {

    public static String getLocalIp() {
        String ip = "127.0.0.1";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ignored) {
        }
        return ip;
    }
}
