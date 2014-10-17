package ua.com.life.smpp.db.dao;

import java.util.Date;
import java.util.List;

import ua.com.life.smpp.db.domain.MsisdnList;

public interface MsisdnListDao {
	public MsisdnList getByMsisdnId(Long id);
	public List<MsisdnList> getByMsisdnByStatus(Integer status);
	public List<MsisdnList> getByMsisdnByStatus(Integer status, int limit);
	public List<MsisdnList> getAllMsisdnList();
	public void save(MsisdnList msisdn);
	
	public void sendToSmsC(Long msisdnId);
	public void sendToSmsC(String msisdn, String messageId, Date submitDate, Date doneDate, Integer status, String err);
}