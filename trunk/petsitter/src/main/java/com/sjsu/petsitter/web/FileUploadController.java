package com.sjsu.petsitter.web;

import com.sjsu.petsitter.bean.FileUploadBean;
import com.sjsu.petsitter.domain.Request;
import com.sjsu.petsitter.domain.Response;
import com.sjsu.petsitter.service.RequestService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: ckempaiah
 * Date: 5/2/12
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
 */
@RequestMapping("/uploadfile")
@Controller
@RooWebScaffold(path = "uploadfile", formBackingObject = FileUploadBean.class)
public class FileUploadController {

    @Value("#{storageproperties.filePath}")
    private String STORAGE_PATH;

    @Autowired
    RequestService requestService;

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new FileUploadBean());
        return "uploadfile/create";
    }

    void populateEditForm(Model uiModel, FileUploadBean fileUploadBean) {
        uiModel.addAttribute("fileUploadBean", fileUploadBean);
        addDateTimeFormatPatterns(uiModel);
    }

    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("fileUploadBean_createddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public void create(@Valid FileUploadBean fileUploadBean, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, fileUploadBean);
            throw new IllegalArgumentException("Invalid Arguments passed to controller");
            //return "uploadfile/create";
        }
        UUID id = UUID.randomUUID();
        CommonsMultipartFile multipartFile = fileUploadBean.getFile();
        String orgName = multipartFile.getOriginalFilename();
        uiModel.asMap().clear();
        System.out.println(orgName);
        String[] split = orgName.split("\\.");


        //petsitterMultiFile.saveMovie(movie);
        String uniqueFileName = id + FilenameUtils.EXTENSION_SEPARATOR_STR + FilenameUtils.getExtension(orgName);

        String filePath = STORAGE_PATH + uniqueFileName;
        System.out.println(filePath);
        System.out.println(uniqueFileName);
        File dest = new File(filePath);

        try {
            multipartFile.transferTo(dest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        fileUploadBean.setFileName(uniqueFileName);
        fileUploadBean.setFile(null);
        fileUploadBean.setOriginalFileName(orgName);
        uiModel.asMap().clear();
        //create response object
        createResponse(fileUploadBean);
        //return "redirect:/uploadfile/" + encodeUrlPathSegment(fileUploadBean.getFileName(), httpServletRequest);
    }

    @RequestMapping(params = "fileName", method=RequestMethod.GET)
    public void show(@Param("fileName") String fileName, Model uiModel,HttpServletResponse res) throws Exception{
        addDateTimeFormatPatterns(uiModel);
        File file = new File(STORAGE_PATH + fileName);
        ServletOutputStream ostream = res.getOutputStream();
        IOUtils.copy(new FileInputStream(file), ostream);
    }

//    @RequestMapping(value = "/image/{fileName}", method = RequestMethod.GET)
//    public void getImage(@PathVariable String fileName, HttpServletRequest req, HttpServletResponse res) throws Exception {
//        File file = new File(STORAGE_PATH + fileName);
//        res.setHeader("Cache-Control", "no-store");
//        res.setHeader("Pragma", "no-cache");
//        res.setDateHeader("Expires", 0);
//        res.setContentType("image/jpg");
//        ServletOutputStream ostream = res.getOutputStream();
//        IOUtils.copy(new FileInputStream(file), ostream);
//        ostream.flush();
//        ostream.close();
//    }

    private void createResponse(FileUploadBean fileUploadBean) {

        String strRequestId = fileUploadBean.getRequestId();
        Request request = requestService.findRequest(new BigInteger(strRequestId));
        Response response = new Response();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        response.setRespondent(username);
        response.setResponseDate(new Date());
        response.setCreatedDate(new Date());
        response.setUpdatedDate(new Date());
        response.setComment("New File Uploaded " + "\"" + fileUploadBean.getOriginalFileName() + "\"");
        response.setFileType(FilenameUtils.getExtension(fileUploadBean.getFileName()));
        response.setFileName(fileUploadBean.getFileName());
        List<Response> responses = request.getReponses();
        if (responses == null) {
            responses = new ArrayList<Response>();
        }
        responses.add(response);
        request.setReponses(responses);
        requestService.saveRequest(request);
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }

}
