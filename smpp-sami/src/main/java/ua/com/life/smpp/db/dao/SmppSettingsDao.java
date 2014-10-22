package ua.com.life.smpp.db.dao;

import java.util.List;

import ua.com.life.smpp.db.domain.SmppSettings;

public interface SmppSettingsDao {
	public void addSmppAccount(SmppSettings smppSettings);
	public List<SmppSettings> getAllSettings();
	public SmppSettings getSettingsById(Long id);
	public void deleteSmppAccountById(Long id);
	public List<SmppSettings> getActiveAccounts();
	public SmppSettings getActiveAccount(Long id);
	public List<SmppSettings> getInactiveAccounts();
	public void makeActiveInactiveAccount(Long id, int state);
	public void changeSpeed(Long id, int speed);
}
