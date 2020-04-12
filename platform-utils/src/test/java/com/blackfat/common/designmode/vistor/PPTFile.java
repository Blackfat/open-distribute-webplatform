package com.blackfat.common.designmode.vistor;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-12 20:04
 * @since 1.0-SNAPSHOT
 */
public class PPTFile extends ResourceFile {

    public PPTFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Vistor vistor) {
        vistor.visit(this);
    }
}
