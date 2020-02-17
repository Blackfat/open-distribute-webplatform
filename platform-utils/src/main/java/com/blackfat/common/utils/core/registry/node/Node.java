package com.blackfat.common.utils.core.registry.node;

import com.alibaba.fastjson.JSONObject;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-01-08 16:13
 * @since 1.0-SNAPSHOT
 */
public class Node {

    public static final String KEY_CODE = "code";
    public static final String KEY_TYPE = "type";
    public static final String KEY_VERSION = "version";


    private String code;
    private NodeType type;
    private String version;
    private String path;

    public Node() {
    }

    public Node(String code, String version, NodeType type, String path) {
        this.code = code;
        this.version = version;
        this.type = type;
        this.path = path;
    }

    public JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put(KEY_CODE, this.code);
        data.put(KEY_TYPE, this.type);
        data.put(KEY_VERSION, this.version);
        return data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
