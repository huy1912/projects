package com.iis.rims.lab.quantum;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Async
	public void sendEmail() {
		for (int i = 0; i < 100; i++) {
			
			try {
				System.err.println("Doing something " + i);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
