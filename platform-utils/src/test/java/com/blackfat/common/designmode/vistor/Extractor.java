package com.blackfat.common.designmode.vistor;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-12 20:06
 * @since 1.0-SNAPSHOT
 */
public class Extractor implements Vistor {
    @Override
    public void visit(PdfFile pdfFile) {
        System.out.println("Extract PDF.");
    }

    @Override
    public void visit(PPTFile pptFile) {
        System.out.println("Extract PPT.");
    }

    @Override
    public void visit(WordFile wordFile) {
        System.out.println("Extract WORD.");
    }
}
