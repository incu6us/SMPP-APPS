package ua.com.life.smpp.db.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ua.com.life.smpp.db.domain.MsisdnList;
	public interface MsisdnListManage {
	public MsisdnList getByMsisdnId(Long id);
	public List<MsisdnList> getByMsisdnByStatus(Integer status);
	public List<MsisdnList> getByMsisdnByStatus(Integer status, int limit);
	public List<MsisdnList> getAllMsisdnList();
	public void save(Set<MsisdnList> msisdnList);
	
	public void acceptDeliveryReport(String msisdn, String messageId, Date submitDate, Date doneDate, Integer status, String err);
}
