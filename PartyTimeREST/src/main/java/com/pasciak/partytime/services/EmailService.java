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
import com.sendgrid.helpers.mail.objects.Personalization;

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

	public void sendEmailWithDesignTemplate(String to, String subject, String body) throws IOException {

		SendGrid sendGrid = new SendGrid(sendGridApiKey);
		Request request = new Request();

		try {
			Mail mail = buildDynamicTemplateMail(to, subject, body);
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sendGrid.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}

	private static Mail buildDynamicTemplateMail(String to, String subject, String body) {
		Mail mail = new Mail();
		Email fromEmail = new Email("sheldon@pasciak.com");
		Email toEmail = new Email(to);
		mail.setFrom(fromEmail);
		mail.setSubject(subject);

		Personalization personalization = new Personalization();
		personalization.addTo(toEmail);

		// Add dynamic template data (these keys should match your template variables)
		personalization.addDynamicTemplateData("username", to);
		personalization.addDynamicTemplateData("datetime", new java.util.Date().toString());
		mail.addPersonalization(personalization);

		// Set the dynamic template ID (replace with your actual template ID)
		mail.setTemplateId("d-1147b355ecb84a56a8f8f0c600825059");

		return mail;
	}

}
