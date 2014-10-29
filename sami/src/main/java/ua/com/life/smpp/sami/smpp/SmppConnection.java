package ua.com.life.smpp.sami.smpp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.smpp.Data;
import org.smpp.ServerPDUEventListener;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.Address;
import org.smpp.pdu.AddressRange;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransciever;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.Unbind;
import org.smpp.pdu.UnbindResp;
import org.smpp.pdu.WrongLengthOfStringException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.com.life.sami.init.Constants;
import ua.com.life.smpp.db.domain.MsisdnList;
import ua.com.life.smpp.db.domain.SmppSettings;
import ua.com.life.smpp.db.service.MsisdnListManage;
import ua.com.life.smpp.db.service.SmppManage;
import ua.com.life.smpp.db.service.TextForCampaignManage;

public class SmppConnection {

	private static Logger LOGGER = Logger.getLogger(SmppConnection.class);

	private volatile int enquireLinkTimeout = Constants.enquireLinkTimeout;

	/**
	 * File with default settings for the application.
	 */
	private String propsFilePath = Constants.propsFilePath;
	
	/**
	 * This is the SMPP session used for communication with SMSC.
	 */
	private Session session = null;
//	private PDUListner pduListener;

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
	
	private int maxMessagesLimitForSysId;
//	ApplicationContext ctx = new FileSystemXmlApplicationContext(Constants.manualContext);
	ApplicationContext ctx = new ClassPathXmlApplicationContext(Constants.manualContext);
	
	SmppManage smppSettings = (SmppManage) ctx.getBean("smppManageImpl");
	MsisdnListManage msisdn = (MsisdnListManage) ctx.getBean("msisdnListManageImpl");
	TextForCampaignManage text = (TextForCampaignManage) ctx.getBean("textForCampaignManageImpl");
	
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
	 * 
	 * @throws IOException
	 */

	public SmppConnection(String sessName, String systemId, String password, String ipAddress, int port, int maxMessagesLimitForSysId) {
//		this.sessName = sessName;
//		this.systemId = systemId;
//		this.password = password;
//		this.ipAddress = ipAddress;
//		this.port = port;
//		this.maxMessagesLimitForSysId = maxMessagesLimitForSysId;
		
		this.sessName = sessName;
		this.systemId = systemId;
		try{
			this.password = smppSettings.getSettingsByName(sessName).getPassword();
			this.ipAddress = smppSettings.getSettingsByName(sessName).getHost();
			this.port = smppSettings.getSettingsByName(sessName).getPort();
			this.maxMessagesLimitForSysId = smppSettings.getSettingsByName(sessName).getMaxMessagesLimitForSysId();
		}catch(IndexOutOfBoundsException e){
			this.password = password;
			this.ipAddress = ipAddress;
			this.port = port;
			this.maxMessagesLimitForSysId = maxMessagesLimitForSysId;
		}

		try {
			loadProperties(propsFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// setDaemon(true);
		// start();
	}

	public void setBound(boolean bound) {
		this.bound = bound;
		if(bound){
			smppSettings.changeConnectionState(smppSettings.getSettingsByName(sessName).getId(), 1);
		}else{
			smppSettings.changeConnectionState(smppSettings.getSettingsByName(sessName).getId(), 0);
		}
	}

	public boolean getBound() {
		return bound;
	}

	public String getIpAddr() {
		return ipAddress;
	}

	public SmppConnection(SmppSettings settings) {
		this.sessName = settings.getName();
		this.systemId = settings.getSystemId();
		
		try{
			this.password = smppSettings.getSettingsByName(sessName).getPassword();
			this.ipAddress = smppSettings.getSettingsByName(sessName).getHost();
			this.port = smppSettings.getSettingsByName(sessName).getPort();
			this.maxMessagesLimitForSysId = smppSettings.getSettingsByName(sessName).getMaxMessagesLimitForSysId();
		}catch(IndexOutOfBoundsException e){
			this.password = settings.getPassword();
			this.ipAddress = settings.getHost();
			this.port = settings.getPort();
			this.maxMessagesLimitForSysId = settings.getMaxMessagesLimitForSysId();
		}
		try {
			loadProperties(propsFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// setDaemon(true);
		// start();

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

	public void bind() {
		try {
			if (isBound()) {
				System.out.println("Already bound (" + this.sessName + "), unbind first.");
				return;
			}
		} catch (NullPointerException e) {
		}

		try {
			BindRequest request = null;
			BindResponse response = null;
			request = new BindTransciever();
			TCPIPConnection connection = new TCPIPConnection(ipAddress, port);
			session = new Session(connection);
//			pduListener = new PDUListner(session);
			// set values
			request.setSystemId(systemId);
			request.setPassword(password);
			request.setSystemType(systemType);
			request.setInterfaceVersion((byte) 0x34);
			request.setAddressRange(addressRange);
			// send the request
//			System.out.println("Bind request " + request.debugString());
			LOGGER.debug("Bind request " + request.debugString());
			
			response = session.bind(request, new PDUListner(session));

			Thread.sleep(1000);

//			System.out.println("Bind response " + response.debugString());
			LOGGER.debug("Bind response " + response.debugString());
			if (response.getCommandStatus() == Data.ESME_ROK) {
				setBound(true);
			} else {
//				System.out.println("Bind failed for " + this.sessName + ", code " + response.getCommandStatus());
				LOGGER.debug("Bind failed for " + this.sessName + ", code " + response.getCommandStatus());
				while (!isBound()) {
					try{
						if(smppSettings.getSettingsByName(sessName).getActive() == 1){
							bind();
							Thread.sleep(5000);
						}else{
							closeSmppConnection();
							break;
						}
					}catch(Exception e){
						LOGGER.info("Thread "+sessName+" was interrupted. "+e);
						closeSmppConnection();
						break;
					}
				}
			}

		} catch (Exception e) {
//			System.out.println("Bind operation failed. " + e);
			LOGGER.error("Bind operation failed. " + this.sessName + ": " + e);
			while (!isBound()) {
				try{
					if(smppSettings.getSettingsByName(sessName).getActive() == 1){
						Thread.sleep(5000);
						bind();
					}else{
						closeSmppConnection();
						break;
					}
				}catch(Exception e1){
					LOGGER.info("Thread "+sessName+" was interrupted. "+e1);
					closeSmppConnection();
					break;
				}
			}
		}
	}

	/**
	 * Check if smpp already bound
	 */

	public Boolean isBound() {

		try {
			if (session.isBound() && session.isOpened()) {
				if(!getBound()){
					setBound(true);
				}
			} else {
				setBound(false);
			}
		} catch (Exception e) {
			setBound(false);
		} finally {
			return getBound();
		}
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
	public void unbind() {
		try {

			if (!isBound()) {
//				System.out.println("Not bound, cannot unbind: " + sessName);
				LOGGER.warn("Not bound, cannot unbind: " + sessName);
				return;
			}
			
			// send the request
//			System.out.println("Going to unbind.");
			LOGGER.info("Going to unbind.");
			if (session.getReceiver().isReceiver()) {
//				System.out.println("It can take a while to stop the receiver.");
				LOGGER.info("It can take a while to stop the receiver.");
			}
			UnbindResp response = session.unbind();
			session.close();
//			System.out.println("Unbind response " + response.debugString());
			LOGGER.info("Unbind response " + response.debugString());
			setBound(false);
		} catch (Exception e) {
//			System.out.println("Unbind operation failed. " + e);
			LOGGER.warn("Unbind operation failed. " + sessName + ": " + e);
			try {
				setBound(false);
				session.close();
			} catch (Exception e1) {
				LOGGER.warn("Unbind failed: "+e1);
			}
			setBound(false);
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
	public void submit(String destAddress, String shortMessage, String sender, String validityPeriod) {
		byte senderTon = 0;
		byte senderNpi = 0;

		if(!isBound()){
			bind();
		}

		try {
			SubmitSM request = new SubmitSM();
			// SubmitSMResp response;
			// set values
			request.setServiceType(serviceType);
			if (sender != null) {
				if (sender.matches("\\d+")) {
					senderTon = 5;
					senderNpi = 0;
				}
				if (!sender.matches("\\d+")) {
					senderTon = 1;
					senderNpi = 1;
				}
				if (senderTon == 5) {
					request.setSourceAddr(new Address(senderTon, senderNpi, sender, 11));
				} else {
					request.setSourceAddr(new Address(senderTon, senderNpi, sender));
				}
			} else {
				request.setSourceAddr(sourceAddress);
			}
			if (destAddress.startsWith("+")) {
				destAddress = destAddress.substring(1);
			}
			request.setDestAddr(new Address((byte) 1, (byte) 1, destAddress));
			request.setReplaceIfPresentFlag(replaceIfPresentFlag);
			// request.setShortMessage(shortMessage, Data.ENC_GSM7BIT);
			request.setScheduleDeliveryTime(scheduleDeliveryTime);
			request.setValidityPeriod(validityPeriod);
			request.setEsmClass(esmClass);
			request.setProtocolId(protocolId);
			request.setPriorityFlag(priorityFlag);
			request.setRegisteredDelivery(registeredDelivery);
			request.setDataCoding(dataCoding);
			request.setSmDefaultMsgId(smDefaultMsgId);
			// request.setPayloadType(new
			// ByteBuffer(shortMessage.getBytes("UTF-8")));
			// send the request
			request.assignSequenceNumber(true);
			session.submit(request);
		} catch (Exception e) {
//			System.out.println("Submit operation failed. " + e);
			LOGGER.error("Submit operation failed. " + e);
			if (!isBound() || !session.isOpened()) {
				setBound(false);
				bind();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					LOGGER.error("Interruption thread: "+e1);
				}
			}
			// submit(destAddress, shortMessage, sender);
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
	/**
	 * Loads configuration parameters from the file with the given name. Sets
	 * private variable to the loaded values.
	 */
	private void loadProperties(String fileName) throws IOException {
//		System.out.println("Reading configuration file " + fileName + "...");
		LOGGER.info("Reading configuration file " + fileName + "...");
		FileInputStream propsFile = new FileInputStream(this.getClass().getClassLoader().getResource(fileName).getFile());
		properties.load(propsFile);
		propsFile.close();
//		System.out.println("Setting default parameters...");
		LOGGER.info("Setting default parameters...");
		byte ton;
		byte npi;
		String addr;
		String bindMode;
		int rcvTimeout;
		// ipAddress = properties.getProperty("ip-address");
		// port = getIntProperty("port", port);
		// systemId = properties.getProperty("system-id");
		// password = properties.getProperty("password");
		ton = getByteProperty("addr-ton", addressRange.getTon());
		npi = getByteProperty("addr-npi", addressRange.getNpi());
		addr = properties.getProperty("address-range", addressRange.getAddressRange());
		addressRange.setTon(ton);
		addressRange.setNpi(npi);
		try {
			addressRange.setAddressRange(addr);
		} catch (WrongLengthOfStringException e) {
//			System.out.println("The length of address-range parameter is wrong.");
			LOGGER.error("The length of address-range parameter is wrong.");
		}
		ton = getByteProperty("source-ton", sourceAddress.getTon());
		npi = getByteProperty("source-npi", sourceAddress.getNpi());
		addr = properties.getProperty("source-address", sourceAddress.getAddress());
		setAddressParameter("source-address", sourceAddress, ton, npi, addr);
		ton = getByteProperty("destination-ton", destAddress.getTon());
		npi = getByteProperty("destination-npi", destAddress.getNpi());
		addr = properties.getProperty("destination-address", destAddress.getAddress());
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
		} else if (!bindMode.equalsIgnoreCase("t") && !bindMode.equalsIgnoreCase("r") && !bindMode.equalsIgnoreCase("tr")) {
//			System.out.println("The value of bind-mode parameter in " + "the configuration file " + fileName + " is wrong. " + "Setting the default");
			LOGGER.error("The value of bind-mode parameter in " + "the configuration file " + fileName + " is wrong. " + "Setting the default");
			bindMode = "t";
		}
		bindOption = bindMode;
		// receive timeout in the cfg file is in seconds, we need milliseconds
		// also conversion from -1 which indicates infinite blocking
		// in the cfg file to Data.RECEIVE_BLOCKING which indicates infinite
		// blocking in the library is needed.
		// if (receiveTimeout == Data.RECEIVE_BLOCKING) {
		// rcvTimeout = -1;
		// } else {
		// rcvTimeout = ((int) receiveTimeout) / 1000;
		// }
		// rcvTimeout = getIntProperty("receive-timeout", rcvTimeout);
		// if (rcvTimeout == -1) {
		// receiveTimeout = Data.RECEIVE_BLOCKING;
		// } else {
		// receiveTimeout = rcvTimeout * 1000;
		// }

		receiveTimeout = -1;
		rcvTimeout = -1;
	}

	/**
	 * Gets a property and converts it into byte.
	 */
	private byte getByteProperty(String propName, byte defaultValue) {
		return Byte.parseByte(properties.getProperty(propName, Byte.toString(defaultValue)));
	}

	/**
	 * Gets a property and converts it into integer.
	 */
	private int getIntProperty(String propName, int defaultValue) {
		return Integer.parseInt(properties.getProperty(propName, Integer.toString(defaultValue)));
	}

	/**
	 * Sets attributes of <code>Address</code> to the provided values.
	 */
	private void setAddressParameter(String descr, Address address, byte ton, byte npi, String addr) {
		address.setTon(ton);
		address.setNpi(npi);
		try {
			address.setAddress(addr);
		} catch (WrongLengthOfStringException e) {
//			System.out.println("The length of " + descr + " parameter is wrong.");
			LOGGER.error("The length of " + descr + " parameter is wrong.");
		}
	}
	

	public void run() {
		Runnable run = new Runnable() {

			@Override
			public void run() {
				
				// Binding
				bind();

				Long msisdnOrigId = null;
				String msisdnOrigNum = null;
				String message = null;
				String sourceAddr = null;
				String validityPeriod = null;
//				List<MsisdnList> msisdnList = null;
				Long oldSmppSettingVersion = smppSettings.getSettingsByName(sessName).getVersion();
				List<MsisdnList> msisdnList = null;

				while (true) {
					
					msisdnList = msisdn.getByMsisdnByStatusForIdSystemId(0, maxMessagesLimitForSysId, smppSettings.getSettingsByName(sessName).getId());
					
//					if(msisdnList.size() == 0){
//						msisdnList = msisdn.getByMsisdnByStatus(0, maxMessagesLimitForSysId);
//					}
					
					
					if(msisdnList.size() == 0){
						
						// Send EnquireLink
						try {
							session.enquireLink();
//							System.out.println("enquirelink: " + sessName + " " + session);
							LOGGER.debug("enquirelink: " + sessName + " " + session);
						} catch (TimeoutException | PDUException | WrongSessionStateException | IOException e) {
							LOGGER.warn("Error happenes while enquirelink sending: "+e);
							
							// Rebind if session is closed
							unbind();
							while (!isBound()) {
								if(smppSettings.getSettingsByName(sessName).getActive() == 1){
									bind();
									try {
										Thread.sleep(5000);
									} catch (InterruptedException e1) {
										LOGGER.warn("Interruption thread: "+e1);
									}
								}else{
									closeSmppConnection();
									break;
								}
							}
						} 
						
						// Timeout for EnquireLink
						try {
							Thread.sleep(enquireLinkTimeout);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							LOGGER.warn("Interruption thread: "+e);
						}
	
					}else{
						//// Sending messages
						Long started = System.currentTimeMillis();

						for (MsisdnList num : msisdnList) {
							
							msisdnOrigId = num.getId();
							msisdnOrigNum = num.getMsisdn();
							message = text.getTextForCampaignByCompaignId(num.getCampaign().getCampaignId()).getText();
							sourceAddr = num.getCampaign().getSourceAddr();
							validityPeriod = num.getValidityPeriod();
	
//							System.out.println("--->>> Sysid:" + sessName + " msisdn: " + msisdnOrigNum);
							LOGGER.debug("--->>> Sysid:" + sessName + " msisdn: " + msisdnOrigNum);
							submit(msisdnOrigNum, message, sourceAddr, validityPeriod);
							
						}
						
						Long finished = System.currentTimeMillis();
						Long timeResult = finished - started;
						
						// check time interval for 1 second
						if(timeResult < 1000){
							try {
								Thread.sleep(1000 - timeResult);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
					Long newSmppSettingVersion = new Long(-1);
					
					try{
						newSmppSettingVersion = smppSettings.getSettingsByName(sessName).getVersion();
					}catch(IndexOutOfBoundsException e){
						
						closeSmppConnection();
						break;
					}
					
					if(oldSmppSettingVersion != newSmppSettingVersion){
						if(smppSettings.getSettingsByName(sessName).getActive() != 1){
							unbind();
							
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								LOGGER.warn("Interruption exception: "+ e);
							}
							
							closeSmppConnection();
							break;
							
						}else{
							unbind();
							
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							SmppSettings smppSetting = smppSettings.getSettingsByName(sessName);
							new SmppConnection(smppSetting);
							oldSmppSettingVersion = newSmppSettingVersion;
							
							bind();
							
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								LOGGER.warn("Interruption exception: "+ e);
							}
						}
					}
				}
			}
		};

		Thread t = new Thread(run);
		t.setName(sessName);
		t.start();

	}
	

	private void closeSmppConnection(){
		try {
			session.close();
		} catch (WrongSessionStateException | IOException e) {
			// TODO Auto-generated catch block
			LOGGER.warn("Interruption exception: "+ e);
		}
		Thread.currentThread().interrupt();
	}
}
