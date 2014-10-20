package ua.com.life.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.com.life.smpp.db.domain.Campaign;
import ua.com.life.smpp.db.service.CampaignManage;
import ua.com.life.smpp.db.service.MsisdnListManage;
import ua.com.life.smpp.db.service.TextForCampaignManage;
import ua.com.life.smpp.sami.message.ComparedMessageObject;

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

		List<ComparedMessageObject> messageObjects = new ArrayList<ComparedMessageObject>();
		List<Campaign> campaigns = campaign.getAllCampaign();
		
		for(Campaign campaign : campaigns){
//			ComparedMessageObject messageObject = new ComparedMessageObject(campaign.getCampaignId(), campaign.getName(), campaign.getSourceAddr(), text.getTextForCampaignByCompaignId(campaign.getCampaignId()).getText());
			ComparedMessageObject messageObject = new ComparedMessageObject(campaign.getCampaignId(), campaign.getName(), campaign.getSourceAddr(), text.getTextForCampaignByCompaignId(campaign.getCampaignId()).getText());
			messageObjects.add(messageObject);
		}
		
		model.addAttribute("pageName", "stat");
		model.addAttribute("messageObjects", messageObjects);
		
		return "index";
	}
	
	
}
