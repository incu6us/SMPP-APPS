package ua.com.life.ui.controller;

import java.security.NoSuchAlgorithmException;

import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.com.life.smpp.db.service.SmppManage;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired 
	private SmppManage smppSettings;
	
	@RequestMapping(method = RequestMethod.GET)
	public String hello(Model model) throws NoSuchAlgorithmException {
		
		model.addAttribute("pageName", "index");
		
		model.addAttribute("smppSettings", smppSettings.getActiveAccounts());
		
		return "index";
	}
}
