package ua.com.life.smpp.sami.smpp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.smpp.Data;
import org.smpp.ServerPDUEvent;
import org.smpp.ServerPDUEventListener;
import org.smpp.Session;
import org.smpp.SmppObject;
import org.smpp.TCPIPConnection;
import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.Address;
import org.smpp.pdu.AddressRange;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransciever;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.DeliverSMResp;
import org.smpp.pdu.EnquireLink;
import org.smpp.pdu.EnquireLinkResp;
import org.smpp.pdu.PDU;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.Unbind;
import org.smpp.pdu.UnbindResp;
import org.smpp.pdu.ValueNotSetException;
import org.smpp.pdu.WrongLengthOfStringException;
import org.smpp.util.ByteBuffer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.qos.logback.core.joran.action.NewRuleAction;
import ua.com.life.smpp.db.domain.SmppSettings;
import ua.com.life.smpp.db.service.SmppManage;

public class SMPPConnection extends Thread  {
	
	private static Logger LOGGER = Logger.getLogger(SMPPConnection.class);

	/**
	 * File with default settings for the application.
	 */
//	static String propsFilePath = "src/main/resources/smppparams.cfg";
	static String propsFilePath = "smppparams.cfg";
	{
		System.out.println("Absolute path path: "+new File(".").getCanonicalPath());
		LOGGER.info("Cannonical path: "+ new File(".").getCanonicalPath());
	}
	/**
	 * This is the SMPP session used for communication with SMSC.
	 */
	static Session session = null;
	
	private PDUReceiver pduReceiver;
	private Thread enquireLinkThread;
	
	private boolean asynchronous = true;
	
	private String sessName = null;
	/**
	 * Contains the parameters and default values for this test application such
	 * as system id, password, default npi and ton of sender etc.
	 */
	Properties properties = new Properties();
	/**
	 * If the application is bound to the SMSC.
	 */
	boolean bound = false;
	/**
	 * Address of the SMSC.
	 */
	private String ipAddress = null;
	/**
	 * The port number to bind to on the SMSC server.
	 */
	private int port = 0;
	/**
	 * The name which identifies you to SMSC.
	 */
	private String systemId = null;
	/**
	 * The password for authentication to SMSC.
	 */
	private String password = null;
	/**
	 * How you want to bind to the SMSC: transmitter (t), receiver (r) or
	 * transciever (tr). Transciever can both send messages and receive
	 * messages. Note, that if you bind as receiver you can still receive
	 * responses to you requests (submissions).
	 */
	String bindOption = "tr";
	/**
	 * The range of addresses the smpp session will serve.
	 */
	AddressRange addressRange = new AddressRange();
	/*
	 * for information about these variables have a look in SMPP 3.4
	 * specification
	 */
	String systemType = "";
	String serviceType = "";
	Address sourceAddress = new Address();
	Address destAddress = new Address();
	String scheduleDeliveryTime = "";
	String validityPeriod = "";
	String shortMessage = "";
	int numberOfDestination = 1;
	String messageId = "";
	byte esmClass = 0;
	byte protocolId = 0;
	byte priorityFlag = 0;
	byte registeredDelivery = 1;
	byte replaceIfPresentFlag = 0;
	byte dataCoding = 0x08;
	byte smDefaultMsgId = 0;
	/**
	 * If you attemt to receive message, how long will the application wait for
	 * data.
	 */
	long receiveTimeout = Data.RECEIVE_BLOCKING;

	/**
	 * Initialises the application, lods default values for connection to SMSC
	 * and for various PDU fields.
	 * @throws IOException 
	 */

	public SMPPConnection(String sessName, String systemId, String password, String ipAddress, int port) throws IOException {
		this.sessName = sessName;
		this.systemId = systemId;
		this.password = password;
		this.ipAddress = ipAddress;
		this.port = port;
		
		loadProperties(propsFilePath);
		setDaemon(true);
		start();
	}
	
	public SMPPConnection(SmppSettings settings) throws IOException{
		this.sessName = settings.getName();
		this.systemId = settings.getSystemId();
		this.password = settings.getPassword();
		this.ipAddress = settings.getHost();
		this.port = settings.getPort();
		
		loadProperties(propsFilePath);
		setDaemon(true);
		start();
	}

	/**
	 * Sets global SMPP library debug and event objects. Runs the application.
	 * 
	 * @see SmppObject#setDebug(Debug)
	 * @see SmppObject#setEvent(Event)
	 */

	/**
	 * The first method called to start communication betwen an ESME and a SMSC.
	 * A new instance of <code>TCPIPConnection</code> is created and the IP
	 * address and port obtained from user are passed to this instance. New
	 * <code>Session</code> is created which uses the created
	 * <code>TCPIPConnection</code>. All the parameters required for a bind are
	 * set to the <code>BindRequest</code> and this request is passed to the
	 * <code>Session</code>'s <code>bind</code> method. If the call is
	 * successful, the application should be bound to the SMSC.
	 * 
	 * See "SMPP Protocol Specification 3.4, 4.1 BIND Operation."
	 * 
	 * @see BindRequest
	 * @see BindResponse
	 * @see TCPIPConnection
	 * @see Session#bind(BindRequest)
	 * @see Session#bind(BindRequest,ServerPDUEventListener)
	 */

	public synchronized void bind() {
		try {
			if (bound) {
				System.out.println("Already bound (" + this.sessName + "), unbind first.");
				return;
			}
			BindRequest request = null;
			BindResponse response = null;
//			request = new BindTransmitter();
			request = new BindTransciever();
			TCPIPConnection connection = new TCPIPConnection(ipAddress, port);
//			connection.setReceiveTimeout(20 * 1000);
			session = new Session(connection);
			// set values
			request.setSystemId(systemId);
			request.setPassword(password);
			request.setSystemType(systemType);
			request.setInterfaceVersion((byte) 0x34);
			request.setAddressRange(addressRange);
			// send the request
			System.out.println("Bind request " + request.debugString());
			response = session.bind(request);

			pduReceiver = new PDUReceiver(session);
			pduReceiver.start();
			
			enquireLinkThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						enquireLink();
						try {
							Thread.sleep(60000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			
			enquireLinkThread.setDaemon(true);
			enquireLinkThread.setName("enquireLink");
			enquireLinkThread.start();
			
			System.out.println("Bind response " + response.debugString());
			if (response.getCommandStatus() == Data.ESME_ROK) {
				bound = true;
			} else {
				System.out.println("Bind failed for " + this.sessName + ", code "
						+ response.getCommandStatus());
			}
		} catch (Exception e) {
			System.out.println("Bind operation failed. " + e);
			LOGGER.error("Bind operation failed. "+ this.sessName + ": " + e);
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bind();
		}
	}

	/**
	 * Check if smpp already bound
	 */
	
	public Boolean isBound(){
		
		 if(session.isBound()){
			 this.bound = true;
		 }else{
			 this.bound = false;
		 }
		
		return this.bound;
	}
	
	/**
	 * Ubinds (logs out) from the SMSC and closes the connection.
	 * 
	 * See "SMPP Protocol Specification 3.4, 4.2 UNBIND Operation."
	 * 
	 * @see Session#unbind()
	 * @see Unbind
	 * @see UnbindResp
	 */
	public synchronized void unbind() {
		try {
			if(!session.isBound()){
				bound = false;
			}

			if (!bound) {
				System.out.println("Not bound, cannot unbind: "+ this.sessName);
				return;
			}
			// send the request
			System.out.println("Going to unbind.");
			if (session.getReceiver().isReceiver()) {
				System.out.println("It can take a while to stop the receiver.");
			}
			enquireLinkThread.stop();
			pduReceiver.stop();
			UnbindResp response = session.unbind();
			this.session.close();
			System.out.println("Unbind response " + response.debugString());
			bound = false;
		} catch (Exception e) {
			System.out.println("Unbind operation failed. " + e);
			LOGGER.warn("Unbind operation failed. " + this.sessName + ": " + e);
		}
	}

	/**
	 * Creates a new instance of <code>SubmitSM</code> class, lets you set
	 * subset of fields of it. This PDU is used to send SMS message to a device.
	 * 
	 * See "SMPP Protocol Specification 3.4, 4.4 SUBMIT_SM Operation."
	 * 
	 * @see Session#submit(SubmitSM)
	 * @see SubmitSM
	 * @see SubmitSMResp
	 */
	public void submit(String destAddress, String shortMessage, String sender,
			byte senderTon, byte senderNpi) {
		
		bind();
		
		try {
			SubmitSM request = new SubmitSM();
//			SubmitSMResp response;
			// set values
			request.setServiceType(serviceType);
			if (sender != null) {
				if (sender.startsWith("+")) {
					sender = sender.substring(1);
					senderTon = 1;
					senderNpi = 1;
				}
				if (!sender.matches("\\d+")) {
					senderTon = 5;
					senderNpi = 0;
				}
				if (senderTon == 5) {
					request.setSourceAddr(new Address(senderTon, senderNpi,
							sender, 11));
				} else {
					request.setSourceAddr(new Address(senderTon, senderNpi,
							sender));
				}
			} else {
				request.setSourceAddr(sourceAddress);
			}
			if (destAddress.startsWith("+")) {
				destAddress = destAddress.substring(1);
			}
			request.setDestAddr(new Address((byte) 1, (byte) 1, destAddress));
			request.setReplaceIfPresentFlag(replaceIfPresentFlag);
			request.setShortMessage(shortMessage, Data.ENC_GSM7BIT);
			request.setScheduleDeliveryTime(scheduleDeliveryTime);
			request.setValidityPeriod(validityPeriod);
			request.setEsmClass(esmClass);
			request.setProtocolId(protocolId);
			request.setPriorityFlag(priorityFlag);
			request.setRegisteredDelivery(registeredDelivery);
			request.setDataCoding(dataCoding);
			request.setSmDefaultMsgId(smDefaultMsgId);
//			request.setPayloadType(new ByteBuffer(shortMessage.getBytes("UTF-8")));
			// send the request
			request.assignSequenceNumber(true);
			session.submit(request);
//			System.out.println("Submit request: " + request.debugString());
//			response = session.submit(request);
//			System.out.println("Submit response " + response.debugString());
//			messageId = response.getMessageId();
//			System.out.println("MessageID: "+messageId);
//			LOGGER.info("Response ---> messageId: "+messageId+" was sent.");
		} catch (Exception e) {
			System.out.println("Submit operation failed. " + e);
			LOGGER.error("Submit operation failed. " + e);
			if(!isBound() || !session.isOpened()){
				this.bound = false;
				bind();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			submit(destAddress, shortMessage, sender, senderTon, senderNpi);
		}
	}

	/**
	 * Creates a new instance of <code>EnquireSM</code> class. This PDU is used
	 * to check that application level of the other party is alive. It can be
	 * sent both by SMSC and ESME.
	 * 
	 * See "SMPP Protocol Specification 3.4, 4.11 ENQUIRE_LINK Operation."
	 * 
	 * @see Session#enquireLink(EnquireLink)
	 * @see EnquireLink
	 * @see EnquireLinkResp
	 */
	public void enquireLink() {
		try {
			EnquireLink request = new EnquireLink();
			EnquireLinkResp response;
			System.out.println("Enquire Link request " + request.debugString());
			LOGGER.debug("Enquire Link request " + request.debugString());
			
			response = session.enquireLink(request);
			System.out.println("Enquire Link response "
					+ response.debugString());
			LOGGER.debug("Enquire Link response "
					+ response.debugString());
		} catch (Exception e) {
			System.out.println("Enquire Link operation failed. " + e);
			LOGGER.warn("Enquire Link operation failed. " + e + "\n\rRebinding for system_id: " + this.systemId + "...");
			
			this.bound = false;
			unbind();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				LOGGER.warn("Thread sleep failed on reconnection: "+e1);
			}
			
			bind();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				LOGGER.warn("Thread sleep failed on reconnection: "+e1);
			}
		}
	}
	
	/**
	 * Loads configuration parameters from the file with the given name. Sets
	 * private variable to the loaded values.
	 */
	private void loadProperties(String fileName) throws IOException {
		System.out.println("Reading configuration file " + fileName + "...");
		FileInputStream propsFile = new FileInputStream("../applications/sami/WEB-INF/classes/"+fileName);
		properties.load(propsFile);
		propsFile.close();
		System.out.println("Setting default parameters...");
		byte ton;
		byte npi;
		String addr;
		String bindMode;
		int rcvTimeout;
//		ipAddress = properties.getProperty("ip-address");
//		port = getIntProperty("port", port);
//		systemId = properties.getProperty("system-id");
//		password = properties.getProperty("password");
		ton = getByteProperty("addr-ton", addressRange.getTon());
		npi = getByteProperty("addr-npi", addressRange.getNpi());
		addr = properties.getProperty("address-range",
				addressRange.getAddressRange());
		addressRange.setTon(ton);
		addressRange.setNpi(npi);
		try {
			addressRange.setAddressRange(addr);
		} catch (WrongLengthOfStringException e) {
			System.out
					.println("The length of address-range parameter is wrong.");
			LOGGER.error("The length of address-range parameter is wrong.");
		}
		ton = getByteProperty("source-ton", sourceAddress.getTon());
		npi = getByteProperty("source-npi", sourceAddress.getNpi());
		addr = properties.getProperty("source-address",
				sourceAddress.getAddress());
		setAddressParameter("source-address", sourceAddress, ton, npi, addr);
		ton = getByteProperty("destination-ton", destAddress.getTon());
		npi = getByteProperty("destination-npi", destAddress.getNpi());
		addr = properties.getProperty("destination-address",
				destAddress.getAddress());
		setAddressParameter("destination-address", destAddress, ton, npi, addr);
		serviceType = properties.getProperty("service-type", serviceType);
		systemType = properties.getProperty("system-type", systemType);
		bindMode = properties.getProperty("bind-mode", bindOption);
		if (bindMode.equalsIgnoreCase("transmitter")) {
			bindMode = "t";
		} else if (bindMode.equalsIgnoreCase("receiver")) {
			bindMode = "r";
		} else if (bindMode.equalsIgnoreCase("transciever")) {
			bindMode = "tr";
		} else if (!bindMode.equalsIgnoreCase("t")
				&& !bindMode.equalsIgnoreCase("r")
				&& !bindMode.equalsIgnoreCase("tr")) {
			System.out.println("The value of bind-mode parameter in "
					+ "the configuration file " + fileName + " is wrong. "
					+ "Setting the default");
			bindMode = "t";
		}
		bindOption = bindMode;
		// receive timeout in the cfg file is in seconds, we need milliseconds
		// also conversion from -1 which indicates infinite blocking
		// in the cfg file to Data.RECEIVE_BLOCKING which indicates infinite
		// blocking in the library is needed.
//		if (receiveTimeout == Data.RECEIVE_BLOCKING) {
//			rcvTimeout = -1;
//		} else {
//			rcvTimeout = ((int) receiveTimeout) / 1000;
//		}
//		rcvTimeout = getIntProperty("receive-timeout", rcvTimeout);
//		if (rcvTimeout == -1) {
//			receiveTimeout = Data.RECEIVE_BLOCKING;
//		} else {
//			receiveTimeout = rcvTimeout * 1000;
//		}
		
		receiveTimeout = -1;
		rcvTimeout = -1;
	}

	/**
	 * Gets a property and converts it into byte.
	 */
	private byte getByteProperty(String propName, byte defaultValue) {
		return Byte.parseByte(properties.getProperty(propName,
				Byte.toString(defaultValue)));
	}

	/**
	 * Gets a property and converts it into integer.
	 */
	private int getIntProperty(String propName, int defaultValue) {
		return Integer.parseInt(properties.getProperty(propName,
				Integer.toString(defaultValue)));
	}

	/**
	 * Sets attributes of <code>Address</code> to the provided values.
	 */
	private void setAddressParameter(String descr, Address address, byte ton,
			byte npi, String addr) {
		address.setTon(ton);
		address.setNpi(npi);
		try {
			address.setAddress(addr);
		} catch (WrongLengthOfStringException e) {
			System.out.println("The length of " + descr
					+ " parameter is wrong.");
			LOGGER.error("The length of " + descr
					+ " parameter is wrong.");
		}
	}
	
	
	@Override
	public void destroy(){
		unbind();
	}
	
}
