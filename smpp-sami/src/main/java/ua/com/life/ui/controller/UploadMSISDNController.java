package ua.com.life.ui.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ua.com.life.smpp.db.domain.Campaign;
import ua.com.life.smpp.db.domain.MsisdnList;
import ua.com.life.smpp.db.domain.TextForCampaign;
import ua.com.life.smpp.db.service.CampaignManage;
import ua.com.life.smpp.db.service.MsisdnListManage;
import ua.com.life.smpp.db.service.TextForCampaignManage;

@Controller
public class UploadMSISDNController {

	@Autowired
	private CampaignManage campaign;
	
	@Autowired
	private MsisdnListManage msisdn;
	
	@Autowired
	private TextForCampaignManage text;
	
	private static Logger LOGGER = Logger.getLogger(UploadMSISDNController.class);
	
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, headers="content-type=multipart/form-data")
	public @ResponseBody ModelAndView postFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("message") String message, 
			@RequestParam("campName") String campName,
			@RequestParam("sourceAddress") String sourceAddress,
			@RequestParam("validityPeriod") String validityPeriod,
			@RequestParam("radios") Long idSystemId,
			Model model) {
		
		
		Campaign camp = new Campaign(campName, sourceAddress);
		TextForCampaign campText = new TextForCampaign(message, camp);
		Set<MsisdnList> msisdnList = new HashSet<MsisdnList>();
		
		if (!file.isEmpty()) {
			try {
				InputStream inputStream = file.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				
				String str = null;
				
				while((str = reader.readLine()) != null){
//					msisdnList.add(new MsisdnList(str.trim(), camp));
					msisdnList.add(new MsisdnList(str.trim(), camp, validityPeriod, idSystemId));
				}
				
				campaign.save(camp);
				text.save(campText);
				msisdn.save(msisdnList);
				
				reader.close();
				inputStream.close();
				
				LOGGER.info("File upload successfuly!");
				
				return new ModelAndView("redirect:/index/state?uploadState=success");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return new ModelAndView("redirect:/index/state?uploadState=fail");
			}
		} else {
			return new ModelAndView("redirect:/index/state?uploadState=fileIsEmpty");
		}

	}
}
