package org.nutlet.fileupload.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nutlet.fileupload.exceptions.ApplicationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 文件操作工具类，实现对文件的移动和删除
 */
public class FileUtil {

    private static final Logger LOGGER = LogManager.getLogger(FileUtil.class);

    /**
     * 移动源文件至目标文件夹
     *
     * @param src 文件源路径
     * @param des 文件目标路径
     */
    public static void moveFileTo(String src, String des)  throws ApplicationException {
        try {
            Files.move(Paths.get(src), Paths.get(des));
        } catch (IOException e) {
            LOGGER.error("move file error");
            throw new ApplicationException("move file error");
        }
    }

    /**
     * 删除指定文件
     *
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) throws ApplicationException{
        try {
            Files.delete(Paths.get(filePath));
        } catch (IOException e) {
            LOGGER.error("move file error");
            throw new ApplicationException("move file error");
        }
    }

    /**
     * 生成随机的文件名
     */
    public static String generateTempFileName() {
        String milliSeconds = String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        String tempFileName = RandomStringUtils.randomAlphanumeric(12) + milliSeconds;
        return tempFileName;
    }
}
