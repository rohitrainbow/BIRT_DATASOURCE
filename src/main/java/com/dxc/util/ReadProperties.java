package com.dxc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

	String location;

	Properties prop = new Properties();

	public String[] dbDetailsReader(String clientName) throws Exception {
		String[] dbDetails = new String[3];
		InputStream inputStream = null;

		// inputStream =
		//
		// String
		// filelocation=getServletContext().getInitParameter("AdministratorEmail");

		// inputStream =
		// Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
		// inputStream=ReadProperties.class.getResourceAsStream(location);
		try {
			try {
				inputStream = new FileInputStream("C:\\BPS_BIRT_Report_Properties\\dbdetails.properties");
			} catch (IOException ex) {
				try{
					inputStream = new FileInputStream("..//dbdetails.properties");
				}
				catch (IOException e){
				inputStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("dbdetails.properties");
				}
			}
			prop.load(inputStream);
			dbDetails[0] = prop.getProperty(clientName + ".url");
			dbDetails[1] = prop.getProperty(clientName + ".username");
			dbDetails[2] = prop.getProperty(clientName + ".password");
		} finally {
			inputStream.close();
		}
		for (String a : dbDetails) {
			System.out.println(a);
		}

		return dbDetails;
	}

}
