package ua.com.life.smpp.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.life.smpp.db.dao.SmppSettingsDao;
import ua.com.life.smpp.db.domain.SmppSettings;

@Service
public class SmppManageImpl implements SmppManage {

	@Autowired
	private SmppSettingsDao settingsDao;
	
	@Transactional
	public List<SmppSettings> getAllSettings() {
		return settingsDao.getAllSettings();
	}

	@Transactional
	public void addSmppAccount(SmppSettings smppSettings) {
		settingsDao.addSmppAccount(smppSettings);
	}

	@Transactional
	public void deleteSmppAccountById(Long id) {
		settingsDao.deleteSmppAccountById(id);
	}

	@Transactional
	public SmppSettings getSettingsById(Long id) {
		return settingsDao.getSettingsById(id);
	}

	@Transactional
	public List<SmppSettings> getActiveAccounts() {
		return settingsDao.getActiveAccounts();
	}

	@Transactional
	public List<SmppSettings> getInactiveAccounts() {
		return settingsDao.getInactiveAccounts();
	}

	@Transactional
	public void makeActiveInactiveAccount(Long id, int state) {
		settingsDao.makeActiveInactiveAccount(id, state);
	}

	@Transactional
	public SmppSettings getActiveAccount(Long id) {
		return settingsDao.getActiveAccount(id);
	}

	@Transactional
	public void changeSpeed(Long id, int speed){
		settingsDao.changeSpeed(id, speed);
	}
	
}
