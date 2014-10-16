package ua.com.life.smpp.db.dao;

import java.util.List;

import ua.com.life.smpp.db.domain.TextForCampaign;

public interface TextForCampaignDao {
	public TextForCampaign getTextForCampaignById(Long id);
	public TextForCampaign getTextForCampaignByCompaignId(Long campaignId);
	public TextForCampaign getTextForCampaignByCompaignName(String campaignName);
	public List<TextForCampaign> getAllTexts();
	public void save(TextForCampaign compaignText);	
}
