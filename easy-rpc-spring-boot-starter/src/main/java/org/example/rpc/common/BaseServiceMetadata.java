package org.example.rpc.common;

/**
 * @Author Roc
 * @Date 2024/11/14 15:01
 */
public class BaseServiceMetadata {

    public static String buildServiceKey(String path, String group, String version) {
        StringBuilder buf = new StringBuilder();
        if (group != null && group.length() > 0) {
            buf.append(group).append("/");
        }
        buf.append(path);
        if (version != null && version.length() > 0) {
            buf.append(":").append(version);
        }
        return buf.toString();
    }
}
