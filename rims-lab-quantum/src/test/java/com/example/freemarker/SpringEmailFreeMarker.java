package com.example.freemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

// https://stackoverflow.com/questions/38101671/send-freemarker-templated-email
public class SpringEmailFreeMarker {
	
	@Bean
	public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("/fmtemplates");
		return bean;
	}
	
	public static void main(String[] args) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        final String email = "huy1912@gmail.com",
        		username = "huy1912",
        		password = "conan2408",
        		host = "smtp.gmail.com";
		int port = 465;
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        
        Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         
        mailSender.setJavaMailProperties(props);
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
        	 
            public void prepare(MimeMessage mimeMessage) throws Exception {
            	SpringFreeMarker springFreeMarker = new SpringFreeMarker();
        		Map<String, Object> model = new HashMap<>();
        		model.put("msg", "Welcome to");
        		List<Car> cars = new ArrayList<>();
                cars.add(new Car("Audi", 10000));
                cars.add(new Car("Volvo", 20000));
                cars.add(new Car("Honda", 30000));
                model.put("cars", cars);
                FreeMarkerConfigurationFactoryBean freeMarkerConfiguration = springFreeMarker.getFreeMarkerConfiguration();
        		freeMarkerConfiguration.afterPropertiesSet();
        		String out = FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getObject().getTemplate("test.ftl"), model);
        		
            	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom(email);
                helper.setTo(new String[]{"huy1912@gmail.com", "huy.ho@quantumsystech.com"});
                helper.setText(out, true);
                helper.setSubject("Your order #0003");
                helper.addAttachment("iron.png", new ClassPathResource("icons/iron_man.png"));
            }
        };
        
        mailSender.send(preparator);
	}
}
