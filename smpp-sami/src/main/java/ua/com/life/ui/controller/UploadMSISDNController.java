package ua.com.life.ui.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UploadMSISDNController {
	
	private static Logger LOGGER = Logger.getLogger(UploadMSISDNController.class);
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST, headers="content-type=multipart/form-data")
	public @ResponseBody ModelAndView postFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("text") String text, 
			@RequestParam("name") String name,
			Model model) {
		
		if (!file.isEmpty()) {
			try {
				StringBuffer textFromFile = new StringBuffer();
				
				InputStream inputStream = file.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				
				String str = null;
				
				while((str = reader.readLine()) != null){
					textFromFile.append(str);
					if(reader.ready()){
						textFromFile.append("\r\n");
					}
				}
				
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