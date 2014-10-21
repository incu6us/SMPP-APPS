package ua.com.life.sami.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import ua.com.life.smpp.db.domain.MsisdnList;
import ua.com.life.smpp.db.domain.SmppSettings;
import ua.com.life.smpp.db.service.CampaignManage;
import ua.com.life.smpp.db.service.CampaignManageImpl;
import ua.com.life.smpp.db.service.MsisdnListManage;
import ua.com.life.smpp.db.service.MsisdnListManageImpl;
import ua.com.life.smpp.db.service.SmppManage;
import ua.com.life.smpp.db.service.TextForCampaignManage;
import ua.com.life.smpp.db.service.TextForCampaignManageImpl;
import ua.com.life.smpp.sami.smpp.SmppConnection;

public class SmppConnectionInit {

	@Autowired
	private SmppManage smppSettings;

	@Autowired
	private CampaignManage campaign;

	@Autowired
	private MsisdnListManage msisdn;

	@Autowired
	private TextForCampaignManage text;
	
	private Set<SmppConnection> connection = new HashSet<SmppConnection>();
	private Thread bind;
	private ExecutorService execService;


	@PostConstruct
	public void init() throws InterruptedException {

		Thread.sleep(1000);
		
		for (SmppSettings smpp : smppSettings.getActiveAccounts()) {
			System.out.println("---- Started sysid: " + smpp.getName());

			connection.add(new SmppConnection(smpp));
		}

		for(SmppConnection conn : connection){
			conn.run();
		}
	}

}
