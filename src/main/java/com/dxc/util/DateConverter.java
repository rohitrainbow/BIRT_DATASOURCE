package com.dxc.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConverter {
	public Date converter(String input) throws ParseException {
		Date convertedDate = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println();
		java.util.Date date = sdf1.parse(input);
		convertedDate = new java.sql.Date(date.getTime());  
		System.out.println("End Date:"+convertedDate);
		return convertedDate;
	}
}
