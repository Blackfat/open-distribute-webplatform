package com.blackfat.common.designmode.vistor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-12 20:09
 * @since 1.0-SNAPSHOT
 */
public class Application {

    public static void main(String[] args) {
        Extractor extractor = new Extractor();
        List<ResourceFile> resourceFiles = listAllResourceFiles("");
        for (ResourceFile resourceFile : resourceFiles) {
            resourceFile.accept(extractor);
        }
        Compressor compressor = new Compressor();
        for (ResourceFile resourceFile : resourceFiles) {
            resourceFile.accept(compressor);
        }
    }

    private static List listAllResourceFiles(String resourceDirectory) {
        List resourceFiles = new ArrayList<>();
        resourceFiles.add(new PdfFile("a.pdf"));
        resourceFiles.add(new WordFile("b.word"));
        resourceFiles.add(new PPTFile("c.ppt"));
        return resourceFiles;
    }
}
