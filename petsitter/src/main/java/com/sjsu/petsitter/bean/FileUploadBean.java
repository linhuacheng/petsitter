package com.sjsu.petsitter.bean;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * File upload bean
 * User: ckempaiah
 * Date: 5/1/12
 * Time: 11:57 PM
 * To change this template use File | Settings | File Templates.
 */
@RooToString
@RooJavaBean
public class FileUploadBean {

    @Transient
    @NotNull
    private CommonsMultipartFile file;

    @NotNull
    private String requestId;

    private String fileName;

    private String comment;

    private String originalFileName;

}
