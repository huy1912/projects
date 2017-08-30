package com.iis.rims.lab.quantum.handler;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class QuantumLabDownloadHandlerTest {

	@Test
	public void testProcessResults() throws Exception {
		InputStream pdfInputStream = new FileInputStream("reports/10000007_REPORT.pdf");
		QuantumLabDownloadHandler.processResults(FileUtils.readFileToString(new File("reports/10000007_REPORT.xml")), "10000007", "reports/10000007_REPORT.xml",
				"10000007_REPORT.pdf", IOUtils.toByteArray(pdfInputStream));
	}

}
