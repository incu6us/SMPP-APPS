package ua.com.life.smpp.db.dao;

import java.util.List;

import ua.com.life.smpp.db.domain.Campaign;

public interface CampaignDao {
	public Campaign getByCampaignId(Long id);
	public List<Campaign> getAllCampaign();
	public Long save(Campaign compaign);
	public void delete(Campaign campaign);
}
