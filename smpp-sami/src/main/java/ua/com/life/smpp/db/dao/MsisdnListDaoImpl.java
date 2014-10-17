package ua.com.life.smpp.db.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
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
	public synchronized List<MsisdnList> getByMsisdnByStatus(Integer status) {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from MsisdnList where status = :status");
		q.setInteger("status", status);
		return q.list();
	}
	
	@Override
	public synchronized List<MsisdnList> getByMsisdnByStatus(Integer status, int limit) {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from MsisdnList m where status = :status").setMaxResults(limit).setLockMode("m", LockMode.WRITE);
		q.setInteger("status", status);
		return q.list();
	}

	@Override
	public synchronized void sentToSmsC(Long msisdnId) {
		MsisdnList msisdn = (MsisdnList) sessionFactory.getCurrentSession().get(MsisdnList.class, msisdnId);
		msisdn.setStatus(1);
	}
	
	@Override
	public void sentToSmsC(String msisdn, String messageId, Date submitDate, Date doneDate, Integer status, String err) {
		Query q = sessionFactory.getCurrentSession().createQuery("from MsisdnList where msisdn = :msisdn and status=1");
		q.setString("msisdn", msisdn);
		
		List<MsisdnList> msisdns = (List<MsisdnList>) q.list();
		
		for(MsisdnList m : msisdns){
			m.setDeliveryDateSMSC(submitDate);
			m.setReadingDate(doneDate);
			m.setStatus(status);
			m.setMessageId(messageId);
			m.setSmscErr(err);
		}
	}
	
}
