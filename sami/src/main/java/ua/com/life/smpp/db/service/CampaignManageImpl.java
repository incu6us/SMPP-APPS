package ua.com.life.smpp.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.life.smpp.db.dao.CampaignDao;
import ua.com.life.smpp.db.domain.Campaign;

@Service
public class CampaignManageImpl implements CampaignManage {
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Transactional
	public Campaign getByCampaignId(Long id) {
		return campaignDao.getByCampaignId(id);
	}

	@Transactional
	public List<Campaign> getAllCampaign() {
		return campaignDao.getAllCampaign();
	}

	@Transactional
	public Long save(Campaign compaign) {
		return campaignDao.save(compaign);
	}

	@Transactional
	public void delete(Campaign campaign) {
		campaignDao.delete(campaign);
	}

}
