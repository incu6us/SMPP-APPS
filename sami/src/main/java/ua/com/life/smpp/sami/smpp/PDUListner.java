package ua.com.life.smpp.sami.smpp;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.id.IntegralDataTypeHolder;
import org.smpp.ServerPDUEvent;
import org.smpp.ServerPDUEventListener;
import org.smpp.Session;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.PDU;
import org.smpp.pdu.Request;
import org.smpp.pdu.Response;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ua.com.life.sami.init.Constants;
import ua.com.life.smpp.db.service.CampaignManage;
import ua.com.life.smpp.db.service.MsisdnListManage;
import ua.com.life.smpp.db.service.TextForCampaignManage;

public class PDUListner implements ServerPDUEventListener {

	private static Logger LOGGER = Logger.getLogger(PDUListner.class); 
	
	private Session session = null;
	private ApplicationContext ctx;
	
	public PDUListner(Session session) {
		this.session = session;
	}
	
	@Override
	public void handleEvent(ServerPDUEvent event) {
		ctx = new FileSystemXmlApplicationContext(Constants.manualContext);

		MsisdnListManage msisdn = (MsisdnListManage) ctx.getBean("msisdnListManageImpl");
		
		PDU pdu = event.getPDU();

//        if (pdu.isValid() && pdu.isRequest()) {
		if (pdu.isRequest()) {
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
                LOGGER.debug("Destination address: " + deliverSM.getDestAddr().getAddress());
                LOGGER.debug("Source address: " + deliverSM.getSourceAddr().getAddress());
                LOGGER.debug("SMS length: " + deliverSM.getSmLength());
                LOGGER.debug("SMS: " + deliverSM.getShortMessage());
                
                Map<String, String> parsedMessage = parseShortMessage(deliverSM.getShortMessage());
                
                SimpleDateFormat dateFormat = new SimpleDateFormat();
                dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));

                String id = parsedMessage.get("id");
                Date submitDate = new Date(Long.parseLong(parsedMessage.get("submitDate"))*1000);
                Date doneDate = new Date(Long.parseLong(parsedMessage.get("doneDate"))*1000);
                String status = parsedMessage.get("stat").trim();
                String err = parsedMessage.get("err").trim();
                
                if(status.equals(Constants.statusDelivered)){
                	msisdn.acceptDeliveryReport(deliverSM.getSourceAddr().getAddress().trim(), id, submitDate, doneDate, 7, err);
                }else if(status.equals(Constants.statusExpired)){
                	msisdn.acceptDeliveryReport(deliverSM.getSourceAddr().getAddress().trim(), id, submitDate, doneDate, 6, err);
                }else if(status.equals(Constants.statusDeleted)){
                	msisdn.acceptDeliveryReport(deliverSM.getSourceAddr().getAddress().trim(), id, submitDate, doneDate, 5, err);
                }else if(status.equals(Constants.statusUndeliverable)){
                	msisdn.acceptDeliveryReport(deliverSM.getSourceAddr().getAddress().trim(), id, submitDate, doneDate, 4, err);
                }else if(status.equals(Constants.statusAccepted)){
                	msisdn.acceptDeliveryReport(deliverSM.getSourceAddr().getAddress().trim(), id, submitDate, doneDate, 3, err);
                }else if(status.equals(Constants.statusRejected)){
                	msisdn.acceptDeliveryReport(deliverSM.getSourceAddr().getAddress().trim(), id, submitDate, doneDate, 2, err);
                }else{
                	msisdn.acceptDeliveryReport(deliverSM.getSourceAddr().getAddress().trim(), id, submitDate, doneDate, -1, err);
                }
            }
        }
	}

	
	public Map<String, String> parseShortMessage(String shortMessage){
		Map<String, String> result = new HashMap<String, String>();
		
		String id = shortMessage.split(" ")[0].split(":")[1];			// String
		String sub= shortMessage.split(" ")[1].split(":")[1];			// Int
		String dlvrd = shortMessage.split(" ")[2].split(":")[1];		// Int
//		String submit = shortMessage.split(" ")[3];
		String submitDate = shortMessage.split(" ")[4].split(":")[1];	// Long
//		String done = shortMessage.split(" ")[5];
		String doneDate = shortMessage.split(" ")[6].split(":")[1];		// Long
		String stat = shortMessage.split(" ")[7].split(":")[1];			// String (state = DELIVRD)
		String err = shortMessage.split(" ")[8].split(":")[1];			// Int
		
		result.put("id", id);
		result.put("sub", sub);
		result.put("dlvrd", dlvrd);
		result.put("submitDate", submitDate);
		result.put("doneDate", doneDate);
		result.put("stat", stat);
		result.put("err", err);
		
		return result;
	}
}
