package ua.com.life.smpp.db.dao;

import java.util.List;

import ua.com.life.smpp.db.domain.MsisdnList;

public interface MsisdnListDao {
	public MsisdnList getByMsisdnId(Long id);
	public List<MsisdnList> getAllMsisdnList();
	public void save(MsisdnList msisdn);
}
