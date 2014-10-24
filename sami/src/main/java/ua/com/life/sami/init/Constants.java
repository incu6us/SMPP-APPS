package ua.com.life.sami.init;

public class Constants {

	// Conf files
	public static String propsFilePath = "src/main/resources/smppparams.cfg";
//	public static String propsFilePath = "smppparams.cfg";
	public static String rootContect = "";
	public static String manualContext = "src/main/webapp/WEB-INF/manual-context.xml";
	public static String mvcContect = "";
	
	// enquire link timeout
	public static int enquireLinkTimeout = 5000;
	
	// Deliver_SM statuses
	public static String statusDelivered		= "DELIVRD";  // 7
	public static String statusExpired			= "EXPIRED";  // 6
	public static String statusDeleted 		= "DELETED";  // 5
	public static String statusUndeliverable 	= "UNDELIV";  // 4
	public static String statusAccepted 		= "ACCEPTD";  // 3
	public static String statusRejected 		= "REJECTD";  // 2   UNKNOWN error = -1

}
