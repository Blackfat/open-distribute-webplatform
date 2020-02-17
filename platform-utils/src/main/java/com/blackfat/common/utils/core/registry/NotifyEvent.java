package com.blackfat.common.utils.core.registry;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-01-08 16:36
 * @since 1.0-SNAPSHOT
 */
public class NotifyEvent {

    private NotifyEvent.NotifyType type;
    private String path;
    private String data;

    public NotifyEvent(NotifyEvent.NotifyType type, String path, String data) {
        this.type = type;
        this.path = path;
        this.data = data;
    }

    public NotifyEvent.NotifyType getType() {
        return this.type;
    }

    public void setType(NotifyEvent.NotifyType type) {
        this.type = type;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static enum NotifyType {
        NODE_ADDED,
        NODE_REMOVED,
        NODE_UPDATED;

        private NotifyType() {
        }
    }
}
