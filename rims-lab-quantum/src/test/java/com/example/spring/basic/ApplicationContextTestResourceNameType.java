package com.example.spring.basic;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.spring.basic.car")
public class ApplicationContextTestResourceNameType {
	@Bean(name="defaultFile")
	public File defaultFiled() {
		File f = new File("defaultFile.txt");
		return f;
	}
	
	@Bean(name="namedFile")
	public File namedFiled() {
		File f = new File("namedFile.txt");
		return f;
	}
}
