package com.moon.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void SimpleEmailSender(String toEmail,
			String body,
			String subject) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		System.out.println(toEmail + subject + body);
		
		message.setFrom("digitalspark11@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
		mailSender.send(message);
		System.out.println("mail sent to " + toEmail);
	}

	public void SimpleEmailSender(List<String[]> mails, String body, String subject) {
		// TODO Auto-generated method stub
		SimpleMailMessage message = new SimpleMailMessage();
		
	//	System.out.println(toEmail + subject + body);
		
		message.setFrom("digitalspark11@gmail.com");
	//	message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		String address = null;
		
		for (int i = 0; i < mails.size(); i++) {
			 
            // Print all elements of List
			address = mails.get(i)[0];
			try {
				message.setTo(address);
				mailSender.send(message);
				System.out.println("mail sent to " + mails.get(i)[0]);
			} catch (MailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
		
	//	mailSender.send(message);
	}
}
