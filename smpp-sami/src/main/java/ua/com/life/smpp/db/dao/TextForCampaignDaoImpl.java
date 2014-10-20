package ua.com.life.smpp.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl.CriterionEntry;
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
	public TextForCampaign getTextForCampaignByCompaignId(Long campaignId) {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from TextForCampaign where campaign = :campaign");
		q.setLong("campaign", campaignId);
		return (TextForCampaign) q.uniqueResult();
	}

	@Override
	public TextForCampaign getTextForCampaignByCompaignName(String campaignName) {
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

	public Integer totalMessageByCampaignId(Long campaignId){
		Criteria q = (Criteria) sessionFactory.getCurrentSession().createCriteria("TextForCampaign").setProjection(Projections.rowCount()).add(Restrictions.eq("status", ))
		return null;
	}
}
