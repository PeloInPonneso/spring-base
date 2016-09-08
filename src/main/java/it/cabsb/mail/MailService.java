package it.cabsb.mail;

import it.cabsb.model.User;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class MailService {
	
	private static Logger _log = LoggerFactory.getLogger(MailService.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private MessageSource messageSource;
	
	public void sendRegistrationConfirmationEmail(final User user, final Locale locale) {		
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
        	String subject = messageSource.getMessage("email.registration.confirmation.subject", null, locale);
        	String body = messageSource.getMessage("email.registration.confirmation.body", new String[]{user.getUsername()}, locale);
        	String bodyLink = messageSource.getMessage("email.registration.confirmation.body.link", null, locale);
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getEmail());
                message.setSubject(subject);
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("user", user);
                model.put("body", body);
                model.put("bodyLink", bodyLink);
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "it/cabsb/velocity/registration-confirmation.vm", "UTF-8", model);
                message.setText(text, true);
            }
        };
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
			preparator.prepare(mimeMessage);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			_log.error(e.getMessage());
		}
    }
	
}
