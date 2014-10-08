package ua.com.life.smpp.db.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.com.life.smpp.db.domain.TextForCampaign;

@Repository
public class TextForCampaignDaoImpl implements TextForCampaignDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public TextForCampaign getTextForCampaignById(Long id) {
		return (TextForCampaign) sessionFactory.getCurrentSession().get(TextForCampaign.class, id);
	}

	@Override
	public TextForCampaign getTextForCampaignByName(String campaignName) {
		return (TextForCampaign) sessionFactory.getCurrentSession().get(TextForCampaign.class, campaignName);
	}

	@Override
	public List<TextForCampaign> getAllTexts() {
		return (List<TextForCampaign>) sessionFactory.getCurrentSession().createCriteria(TextForCampaign.class);
	}

	@Override
	public void save(TextForCampaign compaignText) {
		sessionFactory.getCurrentSession().save(compaignText);
	}

}
