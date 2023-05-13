package org.nutlet.fileupload.vos;

import java.util.List;

/**
 * 文件上传返回体
 */
public class FileResponseVO {
    /**
     * 返回消息
     */
    private String msg;

    /**
     * 文件名称
     */
    private List<FileInfoVO> fileInfoVOS;

    public FileResponseVO() {
        this("null", null);
    }

    public FileResponseVO(String msg, List<FileInfoVO> fileInfoVOS) {
        this.msg = msg;
        this.fileInfoVOS = fileInfoVOS;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<FileInfoVO> getFileInfoVOS() {
        return fileInfoVOS;
    }

    public void setFileInfoVOS(List<FileInfoVO> fileInfoVOS) {
        this.fileInfoVOS = fileInfoVOS;
    }
}
