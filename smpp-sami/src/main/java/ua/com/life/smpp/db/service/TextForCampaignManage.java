package ua.com.life.smpp.db.service;

import java.util.List;

import ua.com.life.smpp.db.domain.TextForCampaign;

public interface TextForCampaignManage {
	public TextForCampaign getTextForCampaignById(Long id);
	public TextForCampaign getTextForCampaignByName(String campaignName);
	public List<TextForCampaign> getAllTexts();
	public void save(TextForCampaign compaignText);	
}
