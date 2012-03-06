package com.sjsu.petsitter.web;

import com.sjsu.petsitter.bean.SearchRequestBean;
import com.sjsu.petsitter.domain.PetDetail;
import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

/**
 * Created by IntelliJ IDEA.
 * User: ckempaiah
 * Date: 3/4/12
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/search")
@RooWebScaffold(path = "search", formBackingObject = SearchRequestBean.class)
public class SearchController {
    Log log = LogFactory.getLog(SearchController.class);
    @Autowired
    UserService userService;
    @RequestMapping(params = "form", produces = "text/html")
    public String searchForm(Model uiModel){
        populateEditForm(uiModel, new SearchRequestBean());
        return "search/searchForm";
    }

    void populateEditForm(Model uiModel, SearchRequestBean searchRequestBean) {

        uiModel.addAttribute("searchRequestBean", searchRequestBean);
        //addDateTimeFormatPatterns(uiModel);
    }
    @RequestMapping(produces = "text/html")
    public String searchPetOwner(SearchRequestBean searchRequestBean
            , @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "size", required = false) Integer size
            , Model uiModel, HttpSession session){

        int sizeNo = size == null ? 10 : size.intValue();
        final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
        uiModel.addAttribute("petOwners", userService.findPetOwners(searchRequestBean, firstResult, sizeNo));
        float nrOfPages = (float) userService.countAllUsers() / sizeNo;
        uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        populateEditForm(uiModel, searchRequestBean);
        log.debug("SearchRequestBean:"+searchRequestBean);
        log.debug("SearchRequestBean Session:"+session.getAttribute("searchRequestBean"));
        session.setAttribute("searchRequestBean", searchRequestBean);
        return "search/searchWithResult";
    }

    @RequestMapping(value="/{id}", produces = "text/html")
    public String getOwnerInfo(@PathVariable("id")BigInteger id, Model uiModel){
       uiModel.addAttribute("user", userService.findUser(id));
        uiModel.addAttribute("itemId", id);
        return "search/show";
    }
}
