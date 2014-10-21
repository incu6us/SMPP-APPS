package ua.com.life.sami.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.com.life.smpp.db.service.MsisdnListManage;


@Component
@Path("/getMessageCountsByStatuses")
public class GetMessageCountsByStatuses {
	
	private static Logger LOGGER = Logger.getLogger(GetMessageCountsByStatuses.class);

	@Autowired
	private MsisdnListManage msisdn;
	
	@GET
	@Path("/{campaignId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatuses(@PathParam("campaignId") String campaignId){
		
		Long campId = Long.parseLong(campaignId);
//		LOGGER.debug();
		return Response.status(200).entity(msisdn.messageStatusByCampaignIdInJson(campId)).build();
	}
}
