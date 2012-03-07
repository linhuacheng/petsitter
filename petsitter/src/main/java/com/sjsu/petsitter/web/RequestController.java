package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.Address;
import com.sjsu.petsitter.domain.Request;
import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.service.UserService;
import com.sjsu.petsitter.util.RequestStatus;
import java.math.BigInteger;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Request request, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, request);
            return "requests/create";
        }
        uiModel.asMap().clear();
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
}
