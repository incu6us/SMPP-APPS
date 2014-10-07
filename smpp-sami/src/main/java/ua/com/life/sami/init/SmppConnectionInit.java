package ua.com.life.sami.init;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.life.smpp.db.domain.SmppSettings;
import ua.com.life.smpp.db.service.SmppManage;
import ua.com.life.smpp.sami.smpp.SMPPConnection;

public class SmppConnectionInit {

	@Autowired
	private SmppManage smppSettings;

	private SMPPConnection connection;

	@PostConstruct
	public void init() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (final SmppSettings smpp : smppSettings.getActiveAccounts()) {
			System.out.println(smpp.getName());
			Thread conn = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					connection = new SMPPConnection(smpp);
					connection.bind();
				}
			});
//			conn.setDaemon(true);
			conn.setName(smpp.getSystemId());
			conn.start();
		}
	}

	@SuppressWarnings("deprecation")
	@PreDestroy
	public void destroy() {
//		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//		Iterator<Thread> i = threadSet.iterator();
//		
//		for (SmppSettings smpp : smppSettings.getActiveAccounts()) {
//			while (i.hasNext()) {
//				Thread t = i.next();
//				if(t.getName().equals(smpp.getSystemId())){
//					t.destroy();
//				}
//			}
//		}
	}
}
