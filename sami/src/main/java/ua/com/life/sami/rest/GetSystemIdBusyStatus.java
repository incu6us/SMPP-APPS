package ua.com.life.sami.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.life.smpp.db.domain.SmppSettings;
import ua.com.life.smpp.db.service.MsisdnListManage;
import ua.com.life.smpp.db.service.SmppManage;

@Component
@Path("getSystemIdBusyStatus")
public class GetSystemIdBusyStatus {
	
	private static Logger LOGGER = Logger.getLogger(GetSystemIdBusyStatus.class);

	@Autowired
	private MsisdnListManage msisdn;
	
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getState(){
		
		return Response.status(200).entity(msisdn.getBusyStatusForAllSystemIds()).build();
	}
}
