import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ua.com.life.smpp.db.domain.Campaign;
import ua.com.life.smpp.db.domain.MsisdnList;
import ua.com.life.smpp.db.domain.TextForCampaign;
import ua.com.life.smpp.db.service.CampaignManage;
import ua.com.life.smpp.db.service.MsisdnListManage;
import ua.com.life.smpp.db.service.TextForCampaignManage;
import ua.com.life.smpp.db.domain.SmppSettings;
import ua.com.life.smpp.db.service.SmppManage;
import ua.com.life.smpp.db.service.SmppManageImpl;
import ua.com.life.smpp.sami.smpp.SmppConnection;

public class Main {

	private static SmppConnection connection;
	
	public static void main(String[] args) throws Exception {

		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/manual-context.xml");
		
//		SmppManage smppSettings = (SmppManage) ctx.getBean("smppManageImpl");

//		for (SmppSettings smpp : smppSettings.getActiveAccounts()) {
//			System.out.println("---- Started sysid: " + smpp.getName());
//
////			connection = new SmppConnection(smpp);
////			Thread bind = new Thread(connection);
////			bind.start();
//		}
		
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

		
		/*
		 * Compaign add
		 */
		
		CampaignManage campaign = (CampaignManage) ctx.getBean("campaignManageImpl");
		MsisdnListManage msisdn = (MsisdnListManage) ctx.getBean("msisdnListManageImpl");
		TextForCampaignManage text = (TextForCampaignManage) ctx.getBean("textForCampaignManageImpl");
		
		Campaign camp = new Campaign("Test Campaign 1", "test-src");
		System.out.println("----------------");
		MsisdnList msisdnRecord = new MsisdnList("380632105719", camp);
		MsisdnList msisdnRecord1 = new MsisdnList("380937530213", camp);
		MsisdnList msisdnRecord2 = new MsisdnList("380631234567", camp);
		
		TextForCampaign campText = new TextForCampaign("hren kakaja-to...", camp);

		Set<MsisdnList> msisdnList = new HashSet<MsisdnList>();
		msisdnList.add(msisdnRecord);
		msisdnList.add(msisdnRecord1);
		msisdnList.add(msisdnRecord2);
		
		campaign.save(camp);
		text.save(campText);
		msisdn.save(msisdnList);
		
		
		/*
		 * Campaign show all
		 */
		
//		CampaignManage campaign = (CampaignManage) ctx.getBean("campaignManageImpl");
//		MsisdnListManage msisdn = (MsisdnListManage) ctx.getBean("msisdnListManageImpl");
//		TextForCampaignManage text = (TextForCampaignManage) ctx.getBean("textForCampaignManageImpl");
		
//		msisdn.sentToSmsC("380937530213", "0123", new Date(1410171158*1000), new Date(1410171158), 7, "001");
		
//		for(MsisdnList msisdnObj : msisdn.getByMsisdnByStatus(0,100)){
//			System.out.println(msisdnObj.getMsisdn()+" "+msisdnObj.getCampaign().getCampaignId());
//			System.out.println(text.getTextForCampaignByCompaignId(msisdnObj.getCampaign().getCampaignId()).getText());
////			msisdn.sendToSmsC(msisdnObj.getId());
//		}
		
//		List<MsisdnList> msisdnList = msisdn.getByMsisdnByStatus(0, 3);
		
				
//		for (MsisdnList msisdnObj : msisdnList.subList(0, 1)){
//			System.out.println(msisdnObj.getMsisdn()+" "+msisdnObj.getCampaign().getCampaignId());
//		}
		
		
		Long started = System.currentTimeMillis();
		
		Thread.sleep(1000);
		
		Long finished = System.currentTimeMillis();
		
		System.out.println(finished - started);
	}
}




















