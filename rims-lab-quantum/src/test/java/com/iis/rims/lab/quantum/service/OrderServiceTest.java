package com.iis.rims.lab.quantum.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.tempuri.LISIntegrationWebserviceSoap;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
		  locations = "classpath:application.properties")
public class OrderServiceTest {
	@MockBean
	private LISIntegrationWebserviceSoap integrationWebserviceSoap;
	
	@Test
	public void testGetResults() {
		OrderService orderService = new OrderService();
		System.err.println(orderService);
	}

}
