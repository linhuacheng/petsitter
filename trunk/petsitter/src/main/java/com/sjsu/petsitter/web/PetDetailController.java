package com.sjsu.petsitter.web;

import java.util.ArrayList;
import java.util.List;

import com.sjsu.petsitter.bean.PetDetailBean;
import com.sjsu.petsitter.domain.PetDetail;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/petdetails")
@Controller
@RooWebScaffold(path = "petdetails", formBackingObject = PetDetail.class)
public class PetDetailController {
	
	@RequestMapping(value="simple", produces = "application/json")
    public String listjson(@RequestParam(value = "userName") String userName, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        
	 	System.out.println("User Name is ... "+userName);
	 	List<PetDetail> petDetails = petDetailService.findPetDetailsByUserName(userName);
	 	List<PetDetailBean> userPetDetails = new ArrayList<PetDetailBean>();
	 	
	 	System.out.println("Number of Pets is ... "+petDetails.size());
	 	
	 	for (PetDetail petDetail: petDetails){
	 		
	 		PetDetailBean pdb = new PetDetailBean();
	 		pdb.setPetName(petDetail.getPetName());
	 		pdb.setPetType(petDetail.getPetType());
	 		pdb.setPetDesc(petDetail.getDescription());
	 		
            userPetDetails.add(pdb);
        }
	 	
	 	uiModel.addAttribute("userPetDetails", userPetDetails);
        return "petdetails/list";
    }
}
