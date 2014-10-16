package ua.com.life.smpp.sami.smpp;

import org.smpp.ServerPDUEvent;
import org.smpp.ServerPDUEventListener;
import org.smpp.Session;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.PDU;
import org.smpp.pdu.Request;
import org.smpp.pdu.Response;

public class PDUListner implements ServerPDUEventListener {

	private Session session = null;
	
	public PDUListner(Session session) {
		this.session = session;
	}
	
	@Override
	public void handleEvent(ServerPDUEvent event) {
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
            }
        }
	}

}
