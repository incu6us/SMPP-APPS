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
	
	@PostConstruct
	public void init(){
		for (final SmppSettings smpp : smppSettings.getActiveAccounts()) {
			Thread t = new Thread(new Runnable() {
				SMPPConnection connection;

				@Override
				public void run() {
					try {
						connection = new SMPPConnection(smpp);
						connection.bind();
						System.out.println("--->Current thread: "+Thread.currentThread().getName());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
			t.setName(smpp.getSystemId());
			t.start();
		}
	}

	@PreDestroy
	public void destroy(){
		
	}
}
