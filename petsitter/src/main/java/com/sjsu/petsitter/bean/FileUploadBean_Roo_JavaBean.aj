// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.bean;

import com.sjsu.petsitter.bean.FileUploadBean;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

privileged aspect FileUploadBean_Roo_JavaBean {
    
    public CommonsMultipartFile FileUploadBean.getFile() {
        return this.file;
    }
    
    public void FileUploadBean.setFile(CommonsMultipartFile file) {
        this.file = file;
    }
    
    public String FileUploadBean.getRequestId() {
        return this.requestId;
    }
    
    public void FileUploadBean.setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public String FileUploadBean.getFileName() {
        return this.fileName;
    }
    
    public void FileUploadBean.setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String FileUploadBean.getComment() {
        return this.comment;
    }
    
    public void FileUploadBean.setComment(String comment) {
        this.comment = comment;
    }
    
    public String FileUploadBean.getOriginalFileName() {
        return this.originalFileName;
    }
    
    public void FileUploadBean.setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
    
}
