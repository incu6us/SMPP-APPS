package ua.com.life.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
			ComparedMessageObject messageObject = new ComparedMessageObject(campaign.getCampaignId(), campaign.getName(), campaign.getSourceAddr(), 
					text.getTextForCampaignByCompaignId(campaign.getCampaignId()).getText(), msisdn.totalMessagesByCampaignId(campaign.getCampaignId()), msisdn.inActionMessagesByCampaignId(campaign.getCampaignId()),
					msisdn.successMessagesByCampaignId(campaign.getCampaignId()), msisdn.unsuccessMessagesByCampaignId(campaign.getCampaignId()));
			
			messageObjects.add(messageObject);
		}
		
		model.addAttribute("pageName", "stat");
		model.addAttribute("messageObjects", messageObjects);
		
		return "index";
	}
	
	@RequestMapping(params={"campaing_id"}, method=RequestMethod.GET)
	public String renderStatByCampaign(@RequestParam("campaing_id") String campaignId, Model model){
		model.addAttribute("pageName", "stat");
		model.addAttribute("pageSubName", "statDetails");
		
		Campaign camp = campaign.getByCampaignId(Long.parseLong(campaignId));
		
		ComparedMessageObject messageObject = new ComparedMessageObject(camp.getCampaignId(), camp.getName(), camp.getSourceAddr(), text.getTextForCampaignByCompaignId(camp.getCampaignId()).getText());
		
		model.addAttribute("messageObject", messageObject);
		
//		model.addAttribute("tmp", msisdn.messageStatusByCampaignIdInJson(new Long(7)));
		
		return "index";
	}
}
