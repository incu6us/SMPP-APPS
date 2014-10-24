package ua.com.life.smpp.db.dao;

import java.util.List;

import javax.management.Query;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.com.life.smpp.db.domain.Campaign;

@Repository
public class CampaignDaoImpl implements CampaignDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Campaign getByCampaignId(Long id) {
		return (Campaign) sessionFactory.getCurrentSession().get(Campaign.class, id);
	}

	@Override
	public List<Campaign> getAllCampaign() {
		Criteria c = (Criteria) sessionFactory.getCurrentSession().createCriteria(Campaign.class).addOrder(Order.desc("campaignId"));
		return c.list();
	}

	@Override
	public Long save(Campaign campaign) {
		return (Long) sessionFactory.getCurrentSession().save(campaign);
	}

	@Override
	public void delete(Campaign campaign) {
		sessionFactory.getCurrentSession().delete(campaign);
	}
}
