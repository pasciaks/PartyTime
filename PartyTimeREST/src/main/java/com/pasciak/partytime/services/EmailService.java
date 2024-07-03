package com.pasciak.partytime.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {

	@Value("${sendgrid.api.key}")
	private String sendGridApiKey;

	public void sendEmail(String to, String subject, String body) {
		SendGrid sendGrid = new SendGrid(sendGridApiKey);
		Email from = new Email("sheldon@pasciak.com");
		Email toEmail = new Email(to);
		Content content = new Content("text/plain", body);
		Mail mail = new Mail(from, subject, toEmail, content);

		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sendGrid.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			System.out.println("Error sending email: " + ex.getMessage());
		}
	}
}
