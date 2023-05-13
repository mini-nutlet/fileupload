package org.nutlet.fileupload.vos;

public class FileInfoVO {
    /**
     * fileName
     */
    private String fileName;

    /**
     * fileSize
     */
    private String fileSize;

    /**
     * fileType
     */
    private String fileType;

    public FileInfoVO() {
        this(null, null, null);
    }

    public FileInfoVO(String fileName, String fileSize, String fileType) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
