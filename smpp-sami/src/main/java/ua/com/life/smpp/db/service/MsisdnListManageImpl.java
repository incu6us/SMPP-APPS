package ua.com.life.smpp.db.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.life.smpp.db.dao.MsisdnListDao;
import ua.com.life.smpp.db.domain.MsisdnList;

@Service
public class MsisdnListManageImpl implements MsisdnListManage {

	@Autowired
	private MsisdnListDao msisdnListDao;
	
	@Transactional
	public MsisdnList getByMsisdnId(Long id) {
		return msisdnListDao.getByMsisdnId(id);
	}

	@Transactional
	public List<MsisdnList> getAllMsisdnList() {
		return msisdnListDao.getAllMsisdnList();
	}

	@Transactional
	public void save(Set<MsisdnList> msisdnList) {
		for(MsisdnList msisdn : msisdnList){
			msisdnListDao.save(msisdn);
		}
	}

	@Transactional
	public List<MsisdnList> getByMsisdnByStatus(Integer status) {
		return msisdnListDao.getByMsisdnByStatus(status);
	}

	@Transactional
	public List<MsisdnList> getByMsisdnByStatus(Integer status, int limit) {
		return msisdnListDao.getByMsisdnByStatus(status, limit);
	}

	@Transactional
	public void sendToSmsC(Long msisdnId, String messageId) {
		msisdnListDao.sendToSmsC(msisdnId, messageId);
	}

}
