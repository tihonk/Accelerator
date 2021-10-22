package com.accelerator.dto;

import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {

    private String filename;
    private String fileType;
    private byte[] fileData;
    private MultipartFile pdbFile;

    public UploadedFile() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public MultipartFile getPdbFile() {
        return pdbFile;
    }

    public void setPdbFile(MultipartFile pdbFile) {
        this.pdbFile = pdbFile;
    }
}
