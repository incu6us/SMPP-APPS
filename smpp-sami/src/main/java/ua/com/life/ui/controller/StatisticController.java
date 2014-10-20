package ua.com.life.ui.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.com.life.smpp.db.service.CampaignManage;
import ua.com.life.smpp.db.service.MsisdnListManage;
import ua.com.life.smpp.db.service.TextForCampaignManage;

@Controller
@RequestMapping(value="stat")
public class StatisticController {
	
	private static Logger LOGGER = Logger.getLogger(StatisticController.class);
	

	@Autowired
	private CampaignManage campaign;

	@Autowired
	private MsisdnListManage msisdn;

	@Autowired
	private TextForCampaignManage text;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String renderStat(Model model){
		
		model.addAttribute("pageName", "stat");
		
		return "index";
	}
	
	
}
