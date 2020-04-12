package com.blackfat.common.designmode.vistor;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-12 20:02
 * @since 1.0-SNAPSHOT
 */
public interface Vistor {

    void visit(PdfFile pdfFile);

    void visit(PPTFile pdfFile);

    void visit(WordFile pdfFile);
}
