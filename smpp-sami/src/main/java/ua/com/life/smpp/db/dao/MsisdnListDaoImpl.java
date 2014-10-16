package ua.com.life.smpp.db.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.com.life.smpp.db.domain.MsisdnList;

@Repository
public class MsisdnListDaoImpl implements MsisdnListDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public MsisdnList getByMsisdnId(Long id) {
		return (MsisdnList) sessionFactory.getCurrentSession().get(MsisdnList.class, id);
	}

	@Override
	public List<MsisdnList> getAllMsisdnList() {
		Criteria c = (Criteria) sessionFactory.getCurrentSession().createCriteria(MsisdnList.class);
		return c.list();
	}

	@Override
	public void save(MsisdnList msisdn) {
		sessionFactory.getCurrentSession().save(msisdn);
	}

	@Override
	public List<MsisdnList> getByMsisdnByStatus(Integer status) {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from MsisdnList where status = :status");
		q.setInteger("status", status);
		return q.list();
	}
	
	@Override
	public List<MsisdnList> getByMsisdnByStatus(Integer status, int limit) {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from MsisdnList where status = :status").setMaxResults(limit);
		q.setInteger("status", status);
		return q.list();
	}

	@Override
	public void sendToSmsC(Long msisdnId, String messageId) {
		MsisdnList msisdn = (MsisdnList) sessionFactory.getCurrentSession().get(MsisdnList.class, msisdnId);
		msisdn.setDeliveryDateSMSC(new Date());
		msisdn.setStatus(1);
		msisdn.setMessageId(messageId);
	}
	
}
