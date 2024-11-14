package org.example.rpc.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author Roc
 * @Date 2024/11/14 14:53
 */
public abstract class NetUtils {

    private NetUtils() {

    }

    /**
     * @param hostName
     * @return ip address or hostName if UnknownHostException
     */
    public static String getIpByHost(String hostName) {
        try {
            return InetAddress.getByName(hostName).getHostAddress();
        } catch (UnknownHostException e) {
            return hostName;
        }
    }
}
