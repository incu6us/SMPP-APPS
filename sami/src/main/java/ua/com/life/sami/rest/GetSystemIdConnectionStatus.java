package ua.com.life.sami.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.life.smpp.db.domain.SmppSettings;
import ua.com.life.smpp.db.service.SmppManage;


@Component
@Path("getSystemIdConnectionStatus")
public class GetSystemIdConnectionStatus {
	
	private static Logger LOGGER = Logger.getLogger(GetSystemIdConnectionStatus.class);

	@Autowired
	private SmppManage smppSettings;
	
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatuses(@PathParam("systemId") String systemId){
		
		String jsonResult = "{ ";
		List<SmppSettings> smpps = smppSettings.getAllSettings();
		
		for (SmppSettings smpp: smpps) {
			if(smpp.getConnection()==1){
				jsonResult += "\""+smpp.getSystemId()+"\" : \"connected\", ";
			}else{
				jsonResult += "\""+smpp.getSystemId()+"\" : \"disconnected\", ";
			}
		}
		jsonResult = jsonResult.replaceAll("\\, $", "");
		jsonResult += "}";
		
//		LOGGER.debug();
		return Response.status(200).entity(jsonResult).build();
	}
}
