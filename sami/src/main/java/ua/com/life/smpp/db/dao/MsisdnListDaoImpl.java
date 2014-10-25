package ua.com.life.smpp.db.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.com.life.smpp.db.domain.Campaign;
import ua.com.life.smpp.db.domain.MsisdnList;
import ua.com.life.smpp.db.domain.SmppSettings;

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
	public List<MsisdnList> getByMsisdnByStatus(Integer status) {
		List<MsisdnList> list;
		
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from MsisdnList where status = :status");
		q.setInteger("status", status);
		list = q.list();
		
		return list;
	}
	
	@Override
	public List<MsisdnList> getByMsisdnByStatus(Integer status, int limit) {
		List<MsisdnList> list;
		
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from MsisdnList m where status = :status and (idSystemId = '' or idSystemId = null)").setMaxResults(limit).setLockMode("m", LockMode.PESSIMISTIC_READ);
//		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from MsisdnList m where status = :status").setMaxResults(limit);
		q.setInteger("status", status);
		list = q.list();
		
		updateSendedToSmsc((List<MsisdnList>) list);
		
		return list;
		
	}
	
	@Override
	public List<MsisdnList> getByMsisdnByStatusForIdSystemId(Integer status, int limit, Long idSystemId) {
		List<MsisdnList> list;
		
		Query q = (Query) sessionFactory.getCurrentSession().createQuery("from MsisdnList m where status = :status and (idSystemId = :idSystemId or idSystemId = '' or idSystemId = null)").setMaxResults(limit).setLockMode("m", LockMode.PESSIMISTIC_READ);
		q.setInteger("status", status);
		q.setLong("idSystemId", idSystemId);
		list = q.list();
		
		updateSendedToSmsc((List<MsisdnList>) list);
		
		return list;
		
	}

	@Override
	public void deleteMsisdnsByCampaignId(Long campaignId){
		Query q = (Query) sessionFactory.getCurrentSession().createSQLQuery("delete from msisdn_list where campaign_id = :campaignId").setLong("campaignId", campaignId);
		q.executeUpdate();
	}
	
	private void updateSendedToSmsc(List<MsisdnList> msisdnId) {
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
//		Query q = sessionFactory.getCurrentSession().createQuery("from MsisdnList where msisdn = :msisdn and status=1");
		Query q = sessionFactory.getCurrentSession().createSQLQuery("update msisdn_list set submit_date = :deliveryDateSMSC, done_date = :readingDate,"
				+ " status = :status, smsc_err = :err where msisdn = :msisdn and (message_id = :messageId or (status=1 and message_id is null))");
		q.setString("msisdn", msisdn);
		q.setString("messageId", messageId);
		q.setDate("deliveryDateSMSC", submitDate);
		q.setDate("readingDate", doneDate);
		q.setInteger("status", status);
		q.setString("err", err);
		q.executeUpdate();
		
//		List<MsisdnList> msisdns = q.list();
//		
//		for(MsisdnList m : msisdns){
//			m.setDeliveryDateSMSC(submitDate);
//			m.setReadingDate(doneDate);
//			m.setStatus(status);
//			m.setMessageId(messageId);
//			m.setSmscErr(err);
//		}
	}
	
	@Override
	public Integer totalMessagesByCampaignId(Long campaignId){
		Integer totalCount = null;
		Criteria c = (Criteria) sessionFactory.getCurrentSession().createCriteria(MsisdnList.class)
				.add(Restrictions.eq("campaign", sessionFactory.getCurrentSession().load(Campaign.class, campaignId)))
				.setProjection(Projections.rowCount());
		
		totalCount = Integer.parseInt(c.list().get(0).toString());
		return totalCount;
	}

	@Override
	public Integer inActionMessagesByCampaignId(Long campaignId) {
		Integer totalCount = null;
		Criteria c = (Criteria) sessionFactory.getCurrentSession().createCriteria(MsisdnList.class)
				.add(Restrictions.eq("campaign",sessionFactory.getCurrentSession().load(Campaign.class, campaignId)))
				.add(Restrictions.not(Restrictions.eq("status", 0)))
				.setProjection(Projections.rowCount());
		
		totalCount = Integer.parseInt(c.list().get(0).toString());
		return totalCount;
	}

	@Override
	public Integer successMessagesByCampaignId(Long campaignId) {
		Integer totalCount = null;
		Criteria c = (Criteria) sessionFactory.getCurrentSession().createCriteria(MsisdnList.class)
				.add(Restrictions.eq("campaign", sessionFactory.getCurrentSession().load(Campaign.class, campaignId)))
				.add(Restrictions.eq("status", 7))
				.setProjection(Projections.rowCount());
		
		totalCount = Integer.parseInt(c.list().get(0).toString());
		return totalCount;
	}

	@Override
	public Integer unsuccessMessagesByCampaignId(Long campaignId) {
		Integer totalCount = null;
		Criteria c = (Criteria) sessionFactory.getCurrentSession().createCriteria(MsisdnList.class)
				.add(Restrictions.eq("campaign", sessionFactory.getCurrentSession().load(Campaign.class, campaignId)))
				.add(Restrictions.eq("status", -1))
				.setProjection(Projections.rowCount());
		
		totalCount = Integer.parseInt(c.list().get(0).toString());
		return totalCount;
	}
	
	@Override
	public String messageStatusByCampaignIdInJson(Long campaignId){
		String jsonResult = "";
		BigInteger total = new BigInteger("0");
		BigInteger sent = new BigInteger("0");
		
		Query q = (Query) sessionFactory.getCurrentSession().createSQLQuery("select status, count(*) as count from msisdn_list "
				+ "where campaign_id = :campaignId group by status;").setLong("campaignId", campaignId);
		
		
		List<Object[]> states = (List<Object[]>) q.list();
		
		jsonResult = "{ ";
		for(Object[] state : states){
			if((Integer) state[0] == 7){
				jsonResult += "\"delivered\" : "+(BigInteger) state[1]+", ";
				sent = sent.add((BigInteger) state[1]);
			}
			if((Integer) state[0] == 6){
				jsonResult += "\"expired\" : "+(BigInteger) state[1]+", ";
				sent = sent.add((BigInteger) state[1]);
			}
			if((Integer) state[0] == 5){
				jsonResult += "\"deleted\" : "+(BigInteger) state[1]+", ";
				sent = sent.add((BigInteger) state[1]);
			}
			if((Integer) state[0] == 4){
				jsonResult += "\"undeliverable\" : "+(BigInteger) state[1]+", ";
				sent = sent.add((BigInteger) state[1]);
			}
			if((Integer) state[0] == 3){
				jsonResult += "\"accepted\" : "+(BigInteger) state[1]+", ";
				sent = sent.add((BigInteger) state[1]);
			}
			if((Integer) state[0] == 2){
				jsonResult += "\"rejected\" : "+(BigInteger) state[1]+", ";
				sent = sent.add((BigInteger) state[1]);
			}
			if((Integer) state[0] == -1){
				jsonResult += "\"unknown\" : "+(BigInteger) state[1]+", ";
				sent = sent.add((BigInteger) state[1]);
			}
			if((Integer) state[0] == 1){
				sent = sent.add((BigInteger) state[1]);
			}
			
			total = total.add((BigInteger) state[1]);
		}
		
		jsonResult += "\"total\" : "+total+", ";
		jsonResult += "\"sent\" : "+sent+", ";
		jsonResult = jsonResult.replaceAll("\\, $", " }");
		
		return jsonResult;
	}
	
	@Override
	public String getBusyStatusForAllSystemIds(List<SmppSettings> connectedSmpp){
		String jsonResult;
		
		Query getAll = (Query) sessionFactory.getCurrentSession().createSQLQuery("SELECT 1 FROM sami.msisdn_list where (id_systemid is null or id_systemid = '') and status=0 limit 1");
		if(getAll.list().size() != 0){
			jsonResult = "{ ";
			for(SmppSettings smpp : connectedSmpp){
				jsonResult += "\""+smpp.getSystemId()+"\" : \"busy\", ";
			}
			jsonResult = jsonResult.replaceAll("\\, $", " }");
			return jsonResult;
		}
		
		jsonResult = "{ ";
		for(SmppSettings smpp : connectedSmpp){
			Query getBySmppId = (Query) sessionFactory.getCurrentSession().createSQLQuery("SELECT 1 FROM sami.msisdn_list where id_systemid = "+smpp.getId()+" and status=0 limit 1");
			if(getBySmppId.list().size() != 0){
				jsonResult += "\""+smpp.getSystemId()+"\" : \"busy\", ";
			}else{
				jsonResult += "\""+smpp.getSystemId()+"\" : \"free\", ";
			}
		}
		jsonResult = jsonResult.replaceAll("\\, $", " }");
		
		return jsonResult;
	}
}
