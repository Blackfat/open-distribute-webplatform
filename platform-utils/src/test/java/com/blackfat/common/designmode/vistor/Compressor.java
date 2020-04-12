package com.blackfat.common.designmode.vistor;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-12 20:08
 * @since 1.0-SNAPSHOT
 */
public class Compressor implements  Vistor {
    @Override
    public void visit(PdfFile pdfFile) {
        System.out.println("Compress PDF.");
    }

    @Override
    public void visit(PPTFile pdfFile) {
        System.out.println("Compress PPT.");
    }

    @Override
    public void visit(WordFile pdfFile) {
        System.out.println("Compress WORD.");
    }
}
