package org.nutlet.fileupload.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nutlet.fileupload.utils.FileUtil;
import org.nutlet.fileupload.enums.FileTypeEnum;
import org.nutlet.fileupload.exceptions.ApplicationException;
import org.nutlet.fileupload.vos.FileInfoVO;
import org.nutlet.fileupload.vos.FileResponseVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 单个文件上传
 */

@RestController
@RequestMapping("single/upload")
public class SingleFileUploadController {
    private static final Logger LOGGER = LogManager.getLogger(SingleFileUploadController.class);

    @Value("${file.destination.temp.path}")
    private String FILE_TEMP_PATH;

    @Value("${file.destination.save.path}")
    private String FILE_SAVE_PATH;
    @Value("${file.destination.file.maxsize}")
    private Long FILE_MAX_SIZE;

    /**
     * multipart/form-data文件上传
     * 直接抛出敏感异常
     */
    @PostMapping("form-data")
    public FileResponseVO formDataFileUpload(@RequestParam(value = "file", required = true) MultipartFile multipartFile) throws IOException, ApplicationException {
        FileResponseVO fileResponseVO = new FileResponseVO();
        List<FileInfoVO> fileInfoVOList = new ArrayList<>();

        LOGGER.info("file size: {}", multipartFile.getSize());
        if (multipartFile.getSize() > FILE_MAX_SIZE) {
            throw new ApplicationException("file size to big");
        }

        // 未更换名称
        String tempFileName = FileUtil.generateTempFileName();
        String fileSaveTempPath = FILE_TEMP_PATH + File.separator + tempFileName;
        File file = new File(fileSaveTempPath);
        multipartFile.transferTo(file.getAbsoluteFile());

        LOGGER.info("save file to path: {}", file.getAbsolutePath());

        // getSize获取字节
        DataSize dataSize = DataSize.ofBytes(multipartFile.getSize());
        FileInfoVO fileInfoVO = new FileInfoVO(multipartFile.getOriginalFilename(), String.valueOf(dataSize.toMegabytes()), multipartFile.getContentType());
        fileInfoVOList.add(fileInfoVO);
        fileResponseVO.setMsg("success");
        fileResponseVO.setFileInfoVOS(fileInfoVOList);
        return fileResponseVO;
    }

    /**
     * raw 格式文件上传：文本文件
     */
    @PostMapping("raw")
    public FileResponseVO rawFileUpload(HttpServletRequest servletRequest) throws IOException, ApplicationException {
        InputStream inputStream = null;
        FileResponseVO fileResponseVO = null;
        try {
            inputStream = servletRequest.getInputStream();
            String disposition = servletRequest.getHeader("Content-Disposition");
            String fileName = getFileName(disposition);
            // 写入临时文件同时记录大小 byte
            fileResponseVO = checkFileAndGetResponse(inputStream, fileName);
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new ApplicationException("close input stream error");
            }
        }
        return fileResponseVO;
    }

    /**
     * binary 文件上传类型
     *
     * @param servletRequest 请求提
     * @return 成功或者失败的响应体
     * @throws ApplicationException 应用异常
     */
    @PostMapping("binary")
    public FileResponseVO binaryFileUpload(HttpServletRequest servletRequest) throws ApplicationException {
        InputStream inputStream = null;
        FileResponseVO fileResponseVO = null;
        try {
            inputStream = servletRequest.getInputStream();
            String disposition = servletRequest.getHeader("Content-Disposition");
            String fileName = getFileName(disposition);

            // 写入临时文件同时记录大小 byte
            fileResponseVO = checkFileAndGetResponse(inputStream, fileName);
        } catch (IOException e) {
            LOGGER.error(e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new ApplicationException("close input stream error");
            }
        }

        return fileResponseVO;
    }

    /**
     * 分析输入的 http 文件流，保存至指定文件夹
     *
     * @param inputStream http 输入流
     * @param fileName    原始文件名
     * @return 上传成功/失败响应体
     * @throws ApplicationException 应用异常
     */
    private FileResponseVO checkFileAndGetResponse(InputStream inputStream, String fileName) throws ApplicationException {
        // 创建临时文件
        boolean shouldMove = false;
        String tempFileName = FileUtil.generateTempFileName();
        String fileSaveTempPath = FILE_TEMP_PATH + File.separator + tempFileName;
        FileOutputStream fileOutputStream = null;
        FileResponseVO fileResponseVO = new FileResponseVO();
        List<FileInfoVO> fileInfoVOList = new ArrayList<>();

        try {
            fileOutputStream = new FileOutputStream(new File(fileSaveTempPath));
            // 创建临时文件
            FileInfoVO fileInfoVO = createTempFileAndGetFileInfo(inputStream, fileOutputStream, fileName);
            fileInfoVOList.add(fileInfoVO);

            // 上传成功
            fileResponseVO.setFileInfoVOS(fileInfoVOList);
            fileResponseVO.setMsg("success");
            shouldMove = true;

        } catch (ApplicationException applicationException) {
            fileResponseVO.setMsg("failed");
            LOGGER.error(applicationException);
        } catch (Exception e) {
            fileResponseVO.setMsg("failed");
            LOGGER.error("unknown error");
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                throw new ApplicationException("close outputStream error");
            }
        }

        if (shouldMove) {
            // 保存至正式文件夹
            String fileSaveDesPath = FILE_SAVE_PATH + File.separator + tempFileName;
            FileUtil.moveFileTo(fileSaveTempPath, fileSaveDesPath);
        } else {
            FileUtil.deleteFile(fileSaveTempPath);
        }

        return fileResponseVO;
    }

    /**
     * 创建临时文件同时获取文件信息
     *
     * @param inputStream      文件输入流
     * @param fileOutputStream 文件输出流
     * @param fileName         原始文件名
     * @return 文件信息
     * @throws IOException
     */
    private FileInfoVO createTempFileAndGetFileInfo(InputStream inputStream, FileOutputStream fileOutputStream, String fileName) throws ApplicationException {
        FileInfoVO fileInfoVO = new FileInfoVO();

        try {
            long fileSize = 0;

            // 先行读取 fileType 字节码魔法数字
            byte[] bytes = readFileType(inputStream);
            if (bytes.length != 0) {
                fileOutputStream.write(bytes, 0, bytes.length);
                fileSize += bytes.length;
            }

            // 根据文件名判断文件类型
            FileTypeEnum fileTypeEnum = FileTypeEnum.getByMagicNumberCode(bytesToHexString(bytes));

            // 读取后续字节信息
            byte[] b = new byte[1024];
            int readLength;
            while ((readLength = inputStream.read(b)) > 0 && fileSize < 10485761) {
                fileOutputStream.write(b, 0, readLength);
                fileSize += readLength;
            }

            if (fileSize > FILE_MAX_SIZE) {
                // 抛出自定义异常类
                throw new ApplicationException("file size so big");
            }

            DataSize dataSize = DataSize.ofBytes(fileSize);
            fileInfoVO.setFileName(fileName);
            fileInfoVO.setFileType(fileTypeEnum.getFileTypeName());
            fileInfoVO.setFileSize(String.valueOf(dataSize.toMegabytes()));
            LOGGER.info("file input size is {} Byte", fileSize);
        } catch (Exception e) {
        }
        return fileInfoVO;
    }

    /**
     * 根据输入流，读取魔法值，判断文件类型
     * 1. 复制流读取魔法值
     * 2. 缓冲区读入值，后移位至开头（建议）
     *
     * @param inputStream 文件输入流
     * @return 包含文件类型的字节信息
     */
    private byte[] readFileType(InputStream inputStream) {
        byte[] filetypeByte = new byte[28];
        try {
            inputStream.read(filetypeByte, 0, 28);
        } catch (IOException e) {
            LOGGER.error("read the file magic number error: ", e);
            return new byte[0];
        }
        return filetypeByte;
    }

    /**
     * 将文件二进制流（即字节数组）转换成16进制字符串数据
     *
     * @param b 读取的字节字符串
     * @return fileHeaderHex - 文件头，即文件魔数
     */
    private final static String bytesToHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length == 0) {
            return null;
        }

        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }

        LOGGER.info("input file magic number {}", stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * 匹配获取文件名
     *
     * @param disposition 请求头参数
     * @return 获取的文件名
     */
    private String getFileName(String disposition) {
        String fileName = "untitled";
        if (disposition != null && disposition.length() > 0) {
            fileName = disposition.replaceFirst("(?i)^.*filename=\"?([^\"]+)\"?.*$", "$1");
            LOGGER.info("disposition input file name: {}", fileName);
        }
        return fileName;
    }
}
