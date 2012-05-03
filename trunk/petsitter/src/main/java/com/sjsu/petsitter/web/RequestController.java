package com.sjsu.petsitter.web;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.sjsu.petsitter.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjsu.petsitter.bean.RequestResponseDetail;
import com.sjsu.petsitter.domain.Address;
import com.sjsu.petsitter.domain.Request;
import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.service.UserService;
import com.sjsu.petsitter.util.RequestStatus;

@RequestMapping("/requests")
@Controller
@RooWebScaffold(path = "requests", formBackingObject = Request.class)
public class RequestController {

    @Autowired
    UserService userService;

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel, HttpServletRequest httpServletRequest) {
        String strApproverId = httpServletRequest.getParameter("approverId");
        if (strApproverId == null || strApproverId.length() == 0) {
            return "requests/create";
        }
        BigInteger approverId = new BigInteger(strApproverId);
        User approver = userService.findUser(approverId);
        Address approverAddress = getAddress(approver);
        String requestorUserName = getLogonUsername();
        User requestor = userService.findUserByUserName(requestorUserName);
        Address requestorAddress = getAddress(requestor);
        Request request = new Request();
        request.setStatus(RequestStatus.NEW);
        request.setRequestorId(requestor.getId().toString());
        request.setRequestorUserName(requestor.getUserName());
        request.setApproverUserName(approver.getUserName());
        request.setApproverId(approver.getId().toString());
        uiModel.addAttribute("request", request);
        uiModel.addAttribute("requestorPets", requestor.getPets());
        uiModel.addAttribute("approverName", approver.getDisplayName());
        uiModel.addAttribute("approverPets", approver.getPets());
        uiModel.addAttribute("requestorAddress", requestorAddress);
        uiModel.addAttribute("approverAddress", approverAddress);
        addDateTimeFormatPatterns(uiModel);
        return "requests/create";
    }

    private Address getAddress(User user) {
        Address address = new Address();
        address.setLine1(user.getAddressLine1());
        address.setLine2(user.getAddressLine2());
        address.setCity(user.getCity());
        address.setZipCode(user.getZip());
        address.setHomePhone(user.getHomePhone());
        address.setMobile(user.getMobile());
        return address;
    }

    private void setAddress(Address address, User user) {
        address.setLine1(user.getAddressLine1());
        address.setLine2(user.getAddressLine2());
        address.setCity(user.getCity());
        address.setZipCode(user.getZip());
        address.setHomePhone(user.getHomePhone());
        address.setMobile(user.getMobile());
    }

    public static String getLogonUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        return username;
    }

    @SuppressWarnings("deprecation")
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Request request, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, request);
            return "requests/create";
        }
        uiModel.asMap().clear();


        if (httpServletRequest.getParameter("fromJson") != null) {

                    System.out.println(httpServletRequest.getParameter("fromJson"));
        String[] substr_start = null;
        String[] substr_end = null;
        String start_date = httpServletRequest.getParameter("startDate");

        String end_date = httpServletRequest.getParameter("endDate");
        substr_start = start_date.split("/");
        substr_end = end_date.split("/");

        int smonth = 0;
        int sdate = 0;
        int syear = 0;
        int emonth = 0;
        int edate = 0;
        int eyear = 0;

        syear = Integer.parseInt(substr_start[2]);
        System.out.println("Year is .... " + syear);
        smonth = Integer.parseInt(substr_start[0]);
        sdate = Integer.parseInt(substr_start[1]);

        eyear = Integer.parseInt(substr_end[2]);
        emonth = Integer.parseInt(substr_end[0]);
        edate = Integer.parseInt(substr_end[1]);


        System.out.println("Approver User Name is... " + httpServletRequest.getParameter("userName"));
        System.out.println(httpServletRequest.getParameter("petType"));


            System.out.println("start of set methods...");

            User approver = userService.findUserByUserName(httpServletRequest.getParameter("userName"));
            Address approverAddress = getAddress(approver);

            System.out.println("Approver User Display Name is... " + approver.getDisplayName());
            User requestor = userService.findUserByUserName(getLogonUsername());
            Address requestorAddress = getAddress(requestor);

            request.setComment(httpServletRequest.getParameter("message"));
            request.setStatus("New");
//            request.setRequestorId(requestor.getId());
            request.setRequestorUserName(requestor.getUserName());
//            request.setApproverId(approver.getId());
            request.setApproverUserName(approver.getUserName());
            request.setApproverAddress(approverAddress);
            request.setRequestorAddress(requestorAddress);
            request.setCreatedDate(new Date());
            request.setUpdatedDate(new Date());

            final Calendar c = Calendar.getInstance();
            c.set(syear, smonth - 1, sdate);
            request.setRequestStartDate(c.getTime());
            c.set(eyear, emonth - 1, edate);
            request.setRequestEndDate(c.getTime());


            request.setRequestorPet(petDetailService.findPetDetailByPetType(httpServletRequest.getParameter("petType")));
            System.out.println("End of set methods...");
            requestService.saveRequest(request);
            return "redirect:/requests/" + encodeUrlPathSegment(request.getId().toString(), httpServletRequest);

        }


        User approver = userService.findUser(new BigInteger(request.getApproverId()));
        Address approverAddress = getAddress(approver);
        User requestor = userService.findUser(new BigInteger(request.getRequestorId()));
        Address requestorAddress = getAddress(requestor);
        request.setCreatedDate(new Date());
        request.setUpdatedDate(new Date());
        request.setApproverAddress(approverAddress);
        request.setRequestorAddress(requestorAddress);
        requestService.saveRequest(request);
        return "redirect:/requests/" + encodeUrlPathSegment(request.getId().toString(), httpServletRequest);
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("requests", requestService.findMyRequestEntries(getLogonUsername(), firstResult, sizeNo));
            float nrOfPages = (float) requestService.countAllRequests() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("requests", requestService.findAllMyRequests(getLogonUsername()));
        }
        addDateTimeFormatPatterns(uiModel);
        return "requests/list";
    }


    @RequestMapping(value = "simple", produces = "application/json")
    public String listRequests(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {

        int sizeNo = size == null ? 10 : size.intValue();
        final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
        float nrOfPages = (float) requestService.countAllRequests() / sizeNo;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        List<Request> requests = new ArrayList<Request>();
        List<RequestResponseDetail> requestResponseDetails = new ArrayList<RequestResponseDetail>();
        requests = requestService.findAllMyRequests(getLogonUsername());
        System.out.println(requests.size());
        for (Request request : requests) {

            RequestResponseDetail reqResDetail = new RequestResponseDetail();
            reqResDetail.setPetType(request.getRequestorPet().getPetType());
            reqResDetail.setComment(request.getComment());
            reqResDetail.setStatus(request.getStatus());
            reqResDetail.setRequestorUserName(request.getRequestorUserName());
            reqResDetail.setApproverUserName(request.getApproverUserName());

            if (request.getRequestStartDate() == null) {
                reqResDetail.setRequestStartDate(sdf.format(new Date()));
            } else {
                reqResDetail.setRequestStartDate(sdf.format(request.getRequestStartDate()));
            }

            if (request.getRequestEndDate() == null) {
                reqResDetail.setRequestEndDate(sdf.format(new Date()));
            } else {
                reqResDetail.setRequestEndDate(sdf.format(request.getRequestEndDate()));
            }


//        		reqResDetail.setRequestStartDate(sdf.format(request.getRequestStartDate()));
//        		reqResDetail.setRequestEndDate(sdf.format(request.getRequestEndDate()));

//        		System.out.println("Request Start Date is .... "+request.getRequestStartDate());

//        		try{


//        		}
//        		catch (Exception e) {
//        		      e.printStackTrace();
//        		    }
//        		System.out.println("Request End Date is .... "+sdf.format(request.getRequestEndDate()));

            reqResDetail.setRequestorAddress(request.getRequestorAddress().toString());
            reqResDetail.setApproverAddress(request.getApproverAddress().toString());
            reqResDetail.setApproverPhoneNumber(request.getApproverAddress().getMobile());
            reqResDetail.setRequesterPhoneNumber(request.getRequestorAddress().getMobile());
            reqResDetail.setRequestId(request.getId());
            requestResponseDetails.add(reqResDetail);
        }

        uiModel.addAttribute("requestResponseDetails", requestResponseDetails);

//        addDateTimeFormatPatterns(uiModel);
        return "requests/list";
    }

    @RequestMapping(value = "requestresponse", produces = "application/json")
    public String listRequestsWithResponse(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {

        int sizeNo = size == null ? 10 : size.intValue();
        final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
        float nrOfPages = (float) requestService.countAllRequests() / sizeNo;

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        List<Request> requests = new ArrayList<Request>();
        List<RequestResponseDetail> requestResponseDetails = new ArrayList<RequestResponseDetail>();
        requests = requestService.findAllMyRequests(getLogonUsername());
        System.out.println(requests.size());
        for (Request request : requests) {

            RequestResponseDetail reqResDetail = new RequestResponseDetail();
            if (request.getRequestorPet() != null){
                reqResDetail.setPetType(request.getRequestorPet().getPetType());
            }
            reqResDetail.setComment(request.getComment());
            reqResDetail.setStatus(request.getStatus());
            reqResDetail.setRequestorUserName(request.getRequestorUserName());
            reqResDetail.setApproverUserName(request.getApproverUserName());

            if (request.getRequestStartDate() == null) {
                reqResDetail.setRequestStartDate(sdf.format(new Date()));
            } else {
                reqResDetail.setRequestStartDate(sdf.format(request.getRequestStartDate()));
            }

            if (request.getRequestEndDate() == null) {
                reqResDetail.setRequestEndDate(sdf.format(new Date()));
            } else {
                reqResDetail.setRequestEndDate(sdf.format(request.getRequestEndDate()));
            }

//        		reqResDetail.setRequestStartDate(sdf.format(request.getRequestStartDate()));
//        		reqResDetail.setRequestEndDate(sdf.format(request.getRequestEndDate()));

//        		System.out.println("Request Start Date is .... "+request.getRequestStartDate());

//        		try{


//        		}
//        		catch (Exception e) {
//        		      e.printStackTrace();
//        		    }
//        		System.out.println("Request End Date is .... "+sdf.format(request.getRequestEndDate()));

            reqResDetail.setRequestorAddress(request.getRequestorAddress().toString());
            reqResDetail.setApproverAddress(request.getApproverAddress().toString());
            reqResDetail.setApproverPhoneNumber(request.getApproverAddress().getMobile());
            reqResDetail.setRequesterPhoneNumber(request.getRequestorAddress().getMobile());
            reqResDetail.setRequestId(request.getId());
            if (request.getApproverUserName().equalsIgnoreCase(getLogonUsername())) {
                reqResDetail.setType(RequestResponseDetail.IREQ);
            } else {
                reqResDetail.setType(RequestResponseDetail.OREQ);
            }
            requestResponseDetails.add(reqResDetail);


            if (request.getReponses() != null && request.getReponses().size() > 0) {
                for (Response response : request.getReponses()) {
                    RequestResponseDetail responseDetail = new RequestResponseDetail();
                    responseDetail.setRequestorUserName(response.getRespondent());
                    if (response.getRespondent().equalsIgnoreCase(getLogonUsername())) {
                        responseDetail.setType(RequestResponseDetail.ORES);
                    } else {
                        responseDetail.setType(RequestResponseDetail.IRES);
                    }
                    responseDetail.setRequestStartDate(sdf.format(response.getUpdatedDate()));
                    responseDetail.setRequestId(request.getId());
                    responseDetail.setFileName(response.getFileName());
                    String contentType = "";
                    if(response.getContentType() != null){
                           contentType = response.getContentType();
                    }
                    responseDetail.setContentType(contentType);
                    responseDetail.setComment(response.getComment());
                    requestResponseDetails.add(responseDetail);
                }
            }
        }

        uiModel.addAttribute("requestResponseDetails", requestResponseDetails);

//        addDateTimeFormatPatterns(uiModel);
        return "requests/list";
    }

}
