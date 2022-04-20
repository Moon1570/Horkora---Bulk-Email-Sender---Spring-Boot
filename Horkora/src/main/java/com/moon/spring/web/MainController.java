package com.moon.spring.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.moon.spring.model.Email;
import com.moon.spring.service.EmailSenderService;
import com.opencsv.CSVReader;


@Controller
public class MainController {
	
    static List<String[]> mails = new ArrayList<>();
    
    @Autowired
    private EmailSenderService service;

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/upload")
	public String showFileUploadForm() {
		return "fileuploadform";
	}
	
	@PostMapping("/uploadfile")
	public String uploadData(@RequestParam("file") MultipartFile file, Model model) throws Exception{
		
		if (file.isEmpty()) {
            System.out.println("File Empty");
        } else {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
     
            	CSVReader csvReader = new CSVReader(reader);
            	List<String[]> emails = new ArrayList<>();
            	emails = csvReader.readAll();
                reader.close();
                csvReader.close();

            	                
           //     System.out.println("Done-------------------------S");
                
                List<String> list = new ArrayList<>();
                
          //      emails.forEach((n) -> System.out.println(n[0]));
                emails.forEach((n) -> list.add(n[0]));
                
                mails = emails;
            
                model.addAttribute("emails", list);
          //      emails.forEach((n) -> model.addAttribute("emails", list));

            } catch (Exception e) {
				// TODO: handle exception
            	System.out.println(e);
            }
        }
		
		return "fileuploadstatus";
	}
	
	@GetMapping("/sendemails")
	public String sendEmails() {
		return "emailform";
	}
	
	@PostMapping("/sendemails")
	public String sendEmails(@RequestParam("files") MultipartFile[] files, @RequestParam("body") String body, @RequestParam("subject") String subject) throws Exception{
		
		System.out.println("Hello");
			
		mails.forEach((n) -> service.SimpleEmailSender(n[0], body, subject));
		
		return "emailSentSuccess";
	}


}
