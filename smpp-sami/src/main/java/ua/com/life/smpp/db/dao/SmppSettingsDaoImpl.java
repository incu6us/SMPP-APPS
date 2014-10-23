package ua.com.life.smpp.db.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.com.life.smpp.db.domain.SmppSettings;

@Repository
public class SmppSettingsDaoImpl implements SmppSettingsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addSmppAccount(SmppSettings smppSettings) {
		sessionFactory.getCurrentSession().save(smppSettings);
	}

	@Override
	public List<SmppSettings> getAllSettings() {
		return sessionFactory.getCurrentSession().createQuery("from SmppSettings").list();
	}

	@Override
	public void deleteSmppAccountById(Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("delete from SmppSettings where id=?");
		q.setLong(0, id);
		q.executeUpdate();
	}

	@Override
	public SmppSettings getSettingsById(Long id) {
		Query q = sessionFactory.getCurrentSession().createQuery("from SmppSettings where id=?");
		q.setLong(0, id);

		return (SmppSettings) q.list().get(0);
	}

	@Override
	public SmppSettings getSettingsByName(String name) {
		Query q = sessionFactory.getCurrentSession().createQuery("from SmppSettings where name=?");
		q.setString(0, name);

		return (SmppSettings) q.list().get(0);
	}

	@Override
	public List<SmppSettings> getActiveAccounts() {
		Query q = sessionFactory.getCurrentSession().createQuery("from SmppSettings where active=1");
		return q.list();
	}

	@Override
	public List<SmppSettings> getInactiveAccounts() {
		Query q = sessionFactory.getCurrentSession().createQuery("from SmppSettings where active!=1");
		return q.list();
	}

	@Override
	public void makeActiveInactiveAccount(Long id, int state) {
		Query q = sessionFactory.getCurrentSession().createQuery("update SmppSettings set active=? where id=?");
		q.setInteger(0, state);
		q.setLong(1, id);
		q.executeUpdate();
	}

	@Override
	public SmppSettings getActiveAccount(Long id) {
		return (SmppSettings) sessionFactory.getCurrentSession().createQuery("from SmppSettings where active=1 and id = ?").setLong(0, id).list().get(0);
	}

	@Override
	public void changeSpeed(Long id, int speed) {
		Query q = (Query) sessionFactory.getCurrentSession().createQuery(
				"update SmppSettings set maxMessagesLimitForSysId = :maxMessagesLimitForSysId, version=version+1 where id = :id");
		q.setLong("id", id);
		q.setInteger("maxMessagesLimitForSysId", speed);
		q.executeUpdate();
	}

	@Override
	public void changeSystemIdById(Long id, String systemId, String password, String host, int port, int active, int speed) {
		sessionFactory
				.getCurrentSession()
				.createSQLQuery(
						"update smpp_settings set name = :systemId, system_id = :systemId,"
								+ "password = :passwd, host = :host, port = :port, active = :active, speed = :speed, version=version+1 where id = :id")
				.setString("systemId", systemId).setString("passwd", password).setString("host", host).setInteger("port", port).setInteger("active", active)
				.setInteger("speed", speed).setLong("id", id).executeUpdate();
	}

	/**
	 * Method for update connection state when application connect or disconnect
	 * to/from SMSC
	 * 
	 * @param id
	 *            - ID field for systemId
	 * @param connection
	 *            - connection status (can be equels to 0 or 1)
	 */
	@Override
	public void changeConnectionState(Long id, int connection) {
		sessionFactory.getCurrentSession().createQuery("update SmppSettings set connection = :connection where id = :id").setLong("id", id).setInteger("connection", connection)
				.executeUpdate();
	}

}
