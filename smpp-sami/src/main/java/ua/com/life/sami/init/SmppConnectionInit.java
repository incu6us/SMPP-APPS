package ua.com.life.sami.init;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
		for (SmppSettings smpp : smppSettings.getActiveAccounts()) {

			try {
				connection = new SMPPConnection(smpp);
				Thread conn = new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						connection.bind();	
					}
				});
				conn.setDaemon(true);
				conn.setName(smpp.getSystemId());
				conn.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return;
	}

	@PreDestroy
	public void destroy() {
//		for (SmppSettings smpp : smppSettings.getActiveAccounts()) {
//			try {
//				connection = new SMPPConnection(smpp);
//				connection.unbind();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
}
