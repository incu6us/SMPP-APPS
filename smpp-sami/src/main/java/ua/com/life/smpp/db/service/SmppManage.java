package ua.com.life.smpp.db.service;

import java.util.List;

import ua.com.life.smpp.db.domain.SmppSettings;

public interface SmppManage {
	public void addSmppAccount(SmppSettings smppSettings);
	public List<SmppSettings> getAllSettings();
	public SmppSettings getSettingsById(Long id);
	public SmppSettings getSettingsByName(String name);
	public void deleteSmppAccountById(Long id);
	public List<SmppSettings> getActiveAccounts();
	public SmppSettings getActiveAccount(Long id);
	public List<SmppSettings> getInactiveAccounts();
	public void makeActiveInactiveAccount(Long id, int state);
	public void changeSpeed(Long id, int speed);
	public void changeSystemIdById(Long id, String systemId, String password, String host, int port, int active, int speed);
	public void changeConnectionState(Long id, int connection);
}
