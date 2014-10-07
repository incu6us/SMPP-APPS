package ua.com.life.smpp.sami.smpp;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.smpp.Session;
import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.debug.Debug;
import org.smpp.debug.Event;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.DeliverSMResp;
import org.smpp.pdu.EnquireLink;
import org.smpp.pdu.EnquireLinkResp;
import org.smpp.pdu.PDU;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.ValueNotSetException;

public class PDUReceiver extends Thread {

	private static Logger LOGGER = Logger.getLogger(PDUReceiver.class);
	
	private Session session = null;
	private Event event;
	private Debug debug;
	private EnquireLinkResp enquireLinkResp = null;
	

	public PDUReceiver(Session session) {
		this.session = session;
	}

	@Override
	public void run() {
		System.out.println("**********Receiver Started***************");
		while (true) {
			PDU pdu = receive();
			if (pdu != null) {
				System.out.println(pdu.getClass().getName());
				System.out.println("PDU debug: " + pdu.debugString());
				if (pdu instanceof DeliverSM) {
					System.out.println("DeliverSM");
					System.out.println("Command status = "
							+ pdu.getCommandStatus());
					DeliverSM deliverSm = (DeliverSM) pdu;
					DeliverSMResp deliverSmResp = new DeliverSMResp();
					deliverSmResp.setSequenceNumber(deliverSm
							.getSequenceNumber());
					System.out.println("Sending DeliverSmResp.");
					System.out.println("Получатель: "
							+ deliverSm.getSourceAddr().getDebug());
					try {
						this.session.respond(deliverSmResp);
						System.out.println("DeliverSM debug: "
								+ deliverSm.debugString());
					} catch (ValueNotSetException | WrongSessionStateException
							| IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(pdu instanceof EnquireLinkResp){
					EnquireLink enqLink = new EnquireLink();
					try {
						EnquireLinkResp enqLinkResp = session.enquireLink(enqLink);
						System.out.println("EnquireLinkResp: "+enqLinkResp.debugString());
					} catch (ValueNotSetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (PDUException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (WrongSessionStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					EnquireLinkResp enqLinkResp = (EnquireLinkResp) pdu;
//					enqLinkResp.setSequenceNumber(enqLink.getSequenceNumber());
				}
				
//				else {
//					System.out.println("SubmitSMResp");
//					SubmitSMResp s = (SubmitSMResp) pdu;
//					System.out.println("Command status = "
//							+ s.getCommandStatus());
//				}
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private PDU receive() {
		PDU pdu = null;
		try {
			pdu = session.getReceiver().receive(0);
			if (pdu != null) {
//				pdu.debugString();
				System.out.println("Received PDU " + pdu.debugString());
			} else {
				System.out.println("No PDU received this time.");
			}
		} catch (Exception e) {
			event.write(e, "");
			debug.write("Receiving failed. " + e);
			System.out.println("Receiving failed. " + e);
		} 
		finally {
//			 debug.exit(this);
		}
		return pdu;
	}

	public void enquireLink() {
		try {
			EnquireLink request = new EnquireLink();
			EnquireLinkResp response;
			System.out.println("Enquire Link request " + request.debugString());
			LOGGER.debug("Enquire Link request " + request.debugString());
			response = session.enquireLink(request);
			System.out.println("Enquire Link response "
					+ response.debugString());
			LOGGER.debug("Enquire Link response " + response.debugString());
		} catch (Exception e) {
			System.out.println("Enquire Link operation failed. " + e);
			LOGGER.warn("Enquire Link operation failed. " + e
					+ "\n\rRebinding...");
			// this.bound = false;
			// unbind();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				LOGGER.warn("Thread sleep failed on reconnection: " + e1);
			}
			// bind();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				LOGGER.warn("Thread sleep failed on reconnection: " + e1);
			}
		}
	}
	/*
	 * static Object createObject(String className) { Object object = null; try
	 * { Class classDefinition = Class.forName(className); object =
	 * classDefinition.newInstance(); } catch (InstantiationException e) {
	 * System.out.println(e); e.printStackTrace(); } catch
	 * (IllegalAccessException e) { e.printStackTrace(); } catch
	 * (ClassNotFoundException e) { e.printStackTrace(); } return object; }
	 */
}