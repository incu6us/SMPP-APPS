import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ua.com.life.smpp.db.domain.SmppSettings;
import ua.com.life.smpp.db.service.SmppManage;
import ua.com.life.smpp.db.service.SmppManageImpl;
import ua.com.life.smpp.sami.smpp.SMPPConnection;

public class Main {

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml");

		SmppManage smppSettings = (SmppManage) ctx.getBean("smppManageImpl");

		String destAddress = "ervge";
		String shortMessage = "test";
		String sender = "2345";
		byte senderTon = 0x05;
		byte senderNpi = 0x00;

		Long id = smppSettings.getActiveAccount(new Long(2)).getId();
		System.out.println("account id: "+id);
		String sessName = smppSettings.getActiveAccount(new Long(2)).getName();
		String systemId = smppSettings.getActiveAccount(new Long(2)).getSystemId();
		String password = smppSettings.getActiveAccount(new Long(2)).getPassword();
		String ipAddress = smppSettings.getActiveAccount(new Long(2)).getHost();
		int port = smppSettings.getActiveAccount(new Long(2)).getPort();

		
		final SMPPConnection smpp = new SMPPConnection(sessName, systemId, password, ipAddress, port);

		smpp.bind();
		System.out.println("1-----------------");
		Thread.sleep(2000);
		
		for(int i = 0; i<100; i++){
			smpp.submit(destAddress, shortMessage+" - "+i, sender, senderTon, senderNpi);
			System.out.println("2-----------------");
		}

//		Thread.sleep(5000);
//		smpp.unbind();

		/*
		 * add settings
		 */

		// String name = "smppTest1";
		// String systemId = "smppclient1";
		// String password = "password";
		// String host = "127.0.0.1";
		// int port = 2775;
		// int active = 1;
		//
		// SmppSettings smppSettings = new SmppSettings(name, systemId,
		// password, host, port, active);
		//
		// SmppManage addSettings = (SmppManage) ctx.getBean("smppManageImpl");
		// addSettings.addSmppAccount(smppSettings);

		/*
		 * Get settings by ID
		 */

		// SmppManage smppSettings = (SmppManage) ctx.getBean("smppManageImpl");
		// System.out.println("\n\nSettings: "+smppSettings.getSettingsById(new
		// Long(2)).getName());

		/*
		 * Get all settings
		 */
		// SmppManage smppSettings = (SmppManage) ctx.getBean("smppManageImpl");
		// System.out.println("\n\nSettings: "+smppSettings.getAllSettings().get(0).getName());

		/*
		 * Get all active settings
		 */

		// SmppManage smppSettings = (SmppManage) ctx.getBean("smppManageImpl");
		// System.out.println(smppSettings.getActiveAccounts().get(0).getName());

		/*
		 * Delete settings by id
		 */

		// SmppManage smppSettings = (SmppManage) ctx.getBean("smppManageImpl");
		// smppSettings.deleteSmppAccountById(new Long(1));

	}
}
