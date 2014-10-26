package ua.com.life.smpp.db.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ua.com.life.smpp.db.domain.MsisdnList;
import ua.com.life.smpp.db.domain.SmppSettings;
	public interface MsisdnListManage {
	public MsisdnList getByMsisdnId(Long id);
	public List<MsisdnList> getByMsisdnByStatus(Integer status);
	public List<MsisdnList> getByMsisdnByStatus(Integer status, int limit);
	public List<MsisdnList> getByMsisdnByStatusForIdSystemId(Integer status, int limit, Long idSystemId);
	public void deleteMsisdnsByCampaignId(Long campaignId);
	public List<MsisdnList> getAllMsisdnList();
	public void save(Set<MsisdnList> msisdnList);
	
	public void acceptDeliveryReport(String msisdn, String messageId, Date submitDate, Date doneDate, Integer status, String err);
	
	public Integer totalMessagesByCampaignId(Long campaignId);
	public Integer inActionMessagesByCampaignId(Long campaignId);
	public Integer successMessagesByCampaignId(Long campaignId);
	public Integer unsuccessMessagesByCampaignId(Long campaignId);
	
	public String messageStatusByCampaignIdInJson(Long campaignId);
	public String getBusyStatusForAllSystemIds();
}
