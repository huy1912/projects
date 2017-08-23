package com.example.freemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

// http://websystique.com/spring/spring-4-email-using-velocity-freemaker-template-library/
public class SpringFreeMarker {
	
	@Bean
	public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("/fmtemplates");
		return bean;
	}
	
	public static void main(String[] args) throws Exception {
		SpringFreeMarker springFreeMarker = new SpringFreeMarker();
		Map<String, Object> model = new HashMap<>();
		model.put("msg", "Welcome to<&");
		model.put("name", "JOHN");
		List<Car> cars = new ArrayList<>();
        cars.add(new Car("Audi", 10000));
        cars.add(new Car("Volvo", 20000));
        cars.add(new Car("Honda", 30000));
        model.put("cars", cars);
        FreeMarkerConfigurationFactoryBean freeMarkerConfiguration = springFreeMarker.getFreeMarkerConfiguration();
		freeMarkerConfiguration.afterPropertiesSet();
		String out = FreeMarkerTemplateUtils.processTemplateIntoString(freeMarkerConfiguration.getObject().getTemplate("test.ftl"), model);
		
		System.err.println(out);
	}
}
