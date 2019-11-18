package pe.edu.upc.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class Email {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail(String subject, String content, String email) {

		MimeMessage msg = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);

			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(content, true);

			javaMailSender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
