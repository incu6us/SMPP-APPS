package ua.com.life.ui.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.hibernate.loader.custom.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ua.com.life.sami.init.SmppConnectionInit;
import ua.com.life.smpp.db.domain.SmppSettings;
import ua.com.life.smpp.db.service.SmppManage;
import ua.com.life.smpp.sami.smpp.SMPPConnection;

@Controller
@RequestMapping(value = { "/", "/index" })
public class IndexController {
	
	private static Logger LOGGER = Logger.getLogger(IndexController.class);

	@Autowired
	private SmppManage smppSettings;
	
	private SMPPConnection connection;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String hello(Model model) throws NoSuchAlgorithmException {

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
