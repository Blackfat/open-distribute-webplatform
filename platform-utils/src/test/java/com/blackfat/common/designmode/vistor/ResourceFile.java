package com.blackfat.common.designmode.vistor;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-12 19:59
 * @since 1.0-SNAPSHOT
 */
public abstract class ResourceFile {

    protected String filePath;

    public ResourceFile(String filePath) {
        this.filePath = filePath;
    }

    abstract public void accept(Vistor vistor);
}
