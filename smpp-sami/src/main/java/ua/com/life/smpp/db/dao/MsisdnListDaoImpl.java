package ua.com.life.smpp.db.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
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
		Session session = sessionFactory.getCurrentSession();

		session.save(msisdn);
		
		// flushAndClear
		if (session.isDirty())  {
			session.flush();
			session.clear();
	    }
	}

	@Override
	public synchronized List<MsisdnList> getByMsisdnByStatus(Integer status) {
		List<MsisdnList> list;
		
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from MsisdnList where status = :status");
		q.setInteger("status", status);
		list = q.list();
		
		return list;
	}
	
	@Override
	public synchronized List<MsisdnList> getByMsisdnByStatus(Integer status, int limit) {
		List<MsisdnList> list;
		
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from MsisdnList m where status = :status").setMaxResults(limit);
		q.setInteger("status", status);
		list = q.list();
		
		updateSendedToSmsc((List<MsisdnList>) list);
		
		return list;
		
	}

	private synchronized void updateSendedToSmsc(List<MsisdnList> msisdnId) {
		if(msisdnId.size()!=0){
			
			String ids = "";
			
			for(MsisdnList msisdn : msisdnId){
				ids += msisdn.getId()+",";
			}
			
			Query q = (Query) sessionFactory.getCurrentSession().createSQLQuery("update msisdn_list set status=1 where id in ("+ids.substring(0, ids.length()-1)+")");
			q.executeUpdate();
		}
	}
	
	@Override
	public void acceptDeliveryReport(String msisdn, String messageId, Date submitDate, Date doneDate, Integer status, String err) {
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
