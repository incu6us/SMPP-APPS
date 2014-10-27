package ua.com.life.ui.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.life.smpp.db.domain.User;
import ua.com.life.smpp.db.service.UserManager;

@Controller
@RequestMapping("/users")
public class UserManagementController {
	private static Logger LOGGER = Logger.getLogger(UserManagementController.class);
	@Autowired
	private UserManager userManager;

	@RequestMapping(method = RequestMethod.GET)
	public String manageUser(Model model) {
		model.addAttribute("pageName", "users");
		model.addAttribute("userList", userManager.getUsers());
		return "index";
	}

	@RequestMapping(params = { "username", "password", "role" }, method = RequestMethod.GET)
	public String addUser(Model model, @RequestParam("username") String username, 
			@RequestParam("password") String password,
			@RequestParam("role") Integer role) throws NoSuchAlgorithmException {
		User user = new User();
		user.setUsername(username.trim());
		user.setPassword(md5(password.trim()));
		user.setEnabled(1);
		if(role == 0){
			user.setRole("ROLE_SU");
		}else if(role == 1){
			user.setRole("ROLE_ADMIN");
		}else if(role == 2){
			user.setRole("ROLE_USER");
		}
		if (!username.trim().isEmpty() && !password.trim().isEmpty()) {
			userManager.insertUser(user);
		}
		LOGGER.debug("USER Created: " + user);
		return "redirect:/users";
	}

	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Long userId, Model model) {
		userManager.deleteUser(userId);
		return "redirect:/users";
	}

	private String md5(String password) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		digest.update(password.trim().getBytes(), 0, password.length());
		byte[] bytes = digest.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		String generatedPassword = sb.toString();
		return generatedPassword;
	}
}