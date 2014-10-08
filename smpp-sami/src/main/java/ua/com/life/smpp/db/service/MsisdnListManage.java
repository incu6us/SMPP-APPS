package ua.com.life.smpp.db.service;

import java.util.List;
import java.util.Set;

import ua.com.life.smpp.db.domain.MsisdnList;
	public interface MsisdnListManage {
	public MsisdnList getByMsisdnId(Long id);
	public List<MsisdnList> getAllMsisdnList();
	public void save(Set<MsisdnList> msisdnList);
}
