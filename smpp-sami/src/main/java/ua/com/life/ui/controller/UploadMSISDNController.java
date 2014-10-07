package ua.com.life.ui.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody ModelAndView postFile(
			@RequestParam("file") MultipartFile file,
			@RequestParam("text") String text, 
			@RequestParam("name") String name,
			Model model) {
		
//		String fileName = null;
		if (!file.isEmpty()) {
			try {

				InputStream inputStream = file.getInputStream();
				
				int read = 0;
				byte[] bytes = new byte[4098];
				char content;
				
				while((read = inputStream.read(bytes))!=-1){
					content = (char) read;
					System.out.println(content);
				}
				
				inputStream.close();
				
//				byte[] bytes = file.getBytes();
//
//				// Creating the directory to store file
//				String rootPath = System.getProperty("catalina.home");
//				
//				File dir = new File(rootPath + File.separator + "tmpFiles");
//				if (!dir.exists()) {
//					dir.mkdirs();
//				}
//				
//				// Create the file on server
//				File serverFile = new File(dir.getAbsolutePath()
//						+ File.separator + file.getOriginalFilename());
//				BufferedOutputStream stream = new BufferedOutputStream(
//						new FileOutputStream(serverFile));
//				stream.write(bytes);
//				stream.close();
//
//				LOGGER.info("Saved path: " + serverFile);
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
