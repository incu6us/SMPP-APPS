package ua.com.life.ui.controller;

import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.life.smpp.db.domain.User;
import ua.com.life.smpp.db.service.SmppManage;
import ua.com.life.smpp.db.service.UserManager;
import ua.com.life.utils.Md5Passwd;

@Controller
@RequestMapping(value = { "/camp", "/index", "/"})
public class IndexController {

	private static Logger LOGGER = Logger.getLogger(IndexController.class);

	@Autowired
	private SmppManage smppSettings;
	
	@Autowired
	private UserManager userManager;
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String hello(Model model) throws NoSuchAlgorithmException {

		try {
			userManager.getUsers().get(0);
		} catch (IndexOutOfBoundsException e) {
			User userAdmin = new User("admin", Md5Passwd.createMd5("admin"), "ROLE_SU", 1);
			userManager.insertUser(userAdmin);
		}
		
		model.addAttribute("pageName", "index");
		model.addAttribute("smppSettings", smppSettings.getActiveAccounts());
		
		return "index";
	}
	
	@RequestMapping(value = "/state", method = RequestMethod.GET)
	public String hello(@RequestParam("uploadState") String uploadState,
			Model model) throws NoSuchAlgorithmException {

		model.addAttribute("pageName", "index");
		model.addAttribute("uploadState", uploadState);
		model.addAttribute("smppSettings", smppSettings.getActiveAccounts());
		
		return "index";
	}

}
