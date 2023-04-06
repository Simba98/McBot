package cn.evolvefield.mods.botapi.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Project: MultiLoader
 * Author: cnlimiter
 * Date: 2023/4/6 12:53
 * Description:
 */
public class FileUtils {
    public FileUtils() {
    }

    public static void checkFolder(Path folder) {
        if (!folder.toFile().isDirectory()) {
            try {
                Files.createDirectories(folder);
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }
}
