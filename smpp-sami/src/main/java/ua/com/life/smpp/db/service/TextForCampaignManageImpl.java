package ua.com.life.smpp.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.life.smpp.db.dao.TextForCampaignDao;
import ua.com.life.smpp.db.domain.TextForCampaign;

@Service
@Transactional
public class TextForCampaignManageImpl implements TextForCampaignManage {

	@Autowired
	private TextForCampaignDao text;
	
	@Override
	public TextForCampaign getTextForCampaignById(Long id) {
		return text.getTextForCampaignById(id);
	}

	@Override
	public TextForCampaign getTextForCampaignByCompaignId(Long campaignId) {
		return text.getTextForCampaignByCompaignId(campaignId);
	}

	@Override
	public TextForCampaign getTextForCampaignByCompaignName(String campaignName) {
		return text.getTextForCampaignByCompaignName(campaignName);
	}

	@Override
	public List<TextForCampaign> getAllTexts() {
		return text.getAllTexts();
	}

	@Override
	public void save(TextForCampaign compaignText) {
		text.save(compaignText);
	}


}
