package ua.com.life.smpp.sami.smpp;

import org.smpp.ServerPDUEvent;
import org.smpp.ServerPDUEventListener;
import org.smpp.Session;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.PDU;
import org.smpp.pdu.Request;
import org.smpp.pdu.Response;
import org.smpp.pdu.ValueNotSetException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ua.com.life.smpp.db.service.CampaignManage;
import ua.com.life.smpp.db.service.MsisdnListManage;
import ua.com.life.smpp.db.service.TextForCampaignManage;

public class PDUListner implements ServerPDUEventListener {

	private Session session = null;
	
	public PDUListner(Session session) {
		this.session = session;
	}
	
	@Override
	public void handleEvent(ServerPDUEvent event) {
		ApplicationContext ctx = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/manual-context.xml");

		CampaignManage campaign = (CampaignManage) ctx.getBean("campaignManageImpl");
		MsisdnListManage msisdn = (MsisdnListManage) ctx.getBean("msisdnListManageImpl");
		TextForCampaignManage text = (TextForCampaignManage) ctx.getBean("textForCampaignManageImpl");
		
		PDU pdu = event.getPDU();

        if (pdu.isValid() && pdu.isRequest()) {
            Response response = ((Request) pdu).getResponse();

            try {
                session.respond(response);
            }
            catch (Exception e) {
                return;
            }

            if (pdu instanceof DeliverSM) {
                DeliverSM deliverSM = (DeliverSM) pdu;
                System.out.println("Destination address: " + deliverSM.getDestAddr().getAddress());
                System.out.println("Source address: " + deliverSM.getSourceAddr().getAddress());
                System.out.println("SMS length: " + deliverSM.getSmLength());
                System.out.println("SMS: " + deliverSM.getShortMessage());
                
                try {
					System.out.println("-> getReceiptedMessageId: " + deliverSM.getReceiptedMessageId());
				} catch (ValueNotSetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                System.out.println("-> getSmDefaultMsgId: " + deliverSM.getSmDefaultMsgId());
                System.out.println("-> RegisteredDelivery(): " + deliverSM.getRegisteredDelivery());
                try {
					System.out.println("-> getMessageState: " + deliverSM.getMessageState());
				} catch (ValueNotSetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                try {
                	System.out.println("-> getNetworkErrorCode: " + deliverSM.getNetworkErrorCode());
				} catch (ValueNotSetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//                msisdn.sendToSmsC(deliverSM.getSourceAddr().getAddress(), deliverSM.getReceiptedMessageId(), deliverSM.get, doneDate, status, err);
            }
        }
	}

}
