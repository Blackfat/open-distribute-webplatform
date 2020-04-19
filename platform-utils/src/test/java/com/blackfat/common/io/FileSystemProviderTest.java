package com.blackfat.common.io;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.spi.FileSystemProvider;

/**
 * @author wangfeiyang
 * @Description
 * @create 2020-04-13 16:58
 * @since 1.0-SNAPSHOT
 */
public class FileSystemProviderTest {

    public static void main(String[] args) {
        FileSystem fileSystem = FileSystems.getDefault();
        FileSystemProvider provider = fileSystem.provider();
        System.out.println("Provider: " + provider.toString());
    }
}
