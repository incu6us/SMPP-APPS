package ua.com.life.ui.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.life.smpp.db.domain.SmppSettings;
import ua.com.life.smpp.db.service.SmppManage;


@Controller
@RequestMapping(value="smpp_settings")
public class SmppSettingsController {
	
	private static Logger LOGGER = Logger.getLogger(SmppSettingsController.class);
	
	@Autowired
	private SmppManage smppSettings;
	
	@RequestMapping(method=RequestMethod.GET)
	public String renderSettingsPage(Model model){
		
		model.addAttribute("pageName", "smppSettings");
		
		List<SmppSettings> smpp = smppSettings.getAllSettings();
		
		model.addAttribute("smppSettings", smpp);
		
		return "index";
	}
	
	@RequestMapping(value="add_sysid", method=RequestMethod.POST)
	public String addNewSystemId(Model model,
			@RequestParam("systemId") String systemId,
			@RequestParam("passwd") String passwd,
			@RequestParam("host") String host,
			@RequestParam("port") int port,
			@RequestParam("active") int active,
			@RequestParam("speed") int speed){
		
		smppSettings.addSmppAccount(new SmppSettings(systemId, systemId, passwd, host, port, active, speed));
		
		return "redirect:/smpp_settings";
	}
	
	@RequestMapping(value="delete_sysid", method=RequestMethod.GET)
	public String deleteSystemId(Model model,
			@RequestParam("id") Long id){
		
		smppSettings.deleteSmppAccountById(id);
		
		return "redirect:/smpp_settings";
	}
	
	@RequestMapping(value="change_sysid", method=RequestMethod.GET)
	public String viewForChangeSystemId(Model model,
			@RequestParam("id") Long id){
		
		model.addAttribute("pageName", "smppSettings");
		model.addAttribute("pageSubName", "changeSystemId");
		model.addAttribute("smppSetting", smppSettings.getSettingsById(id));
		
		List<SmppSettings> smpp = smppSettings.getAllSettings();
		
		model.addAttribute("smppSettings", smpp);
		
		return "index";
	}
	
	@RequestMapping(value="change_sysid", method=RequestMethod.POST)
	public String changeSystemId(Model model,
			@RequestParam("id") Long id,
			@RequestParam("systemId") String systemId,
			@RequestParam("passwd") String passwd,
			@RequestParam("host") String host,
			@RequestParam("port") int port,
			@RequestParam("active") int active,
			@RequestParam("speed") int speed){
		
		smppSettings.changeSystemIdById(id, systemId, passwd, host, port, active, speed);
		
		model.addAttribute("pageName", "smppSettings");
		model.addAttribute("pageSubName", "changeSystemId");
		model.addAttribute("smppSetting", smppSettings.getSettingsById(id));
		
		List<SmppSettings> smpp = smppSettings.getAllSettings();
		
		model.addAttribute("smppSettings", smpp);
		
		return "index";
	}
	
}
