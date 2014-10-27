package ua.com.life.ui.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.com.life.smpp.db.domain.User;
import ua.com.life.smpp.db.service.UserManager;
import ua.com.life.utils.Md5Passwd;

@Controller
@RequestMapping(value = { "login" })
public class Login {
	
	@Autowired
	private UserManager userManager;

	@RequestMapping(method = RequestMethod.GET)
	public String doLogin() throws NoSuchAlgorithmException {

		try {
			userManager.getUsers().get(0);
		} catch (IndexOutOfBoundsException e) {
			User userAdmin = new User("admin", Md5Passwd.createMd5("admin"), "ROLE_SU", 1);
			userManager.insertUser(userAdmin);
		}

		return "login";
	}
}
