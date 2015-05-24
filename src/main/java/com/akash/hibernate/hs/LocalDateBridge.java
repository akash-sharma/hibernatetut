package com.akash.hibernate.hs;

import org.hibernate.search.SearchException;
import org.hibernate.search.bridge.TwoWayStringBridge;
import org.joda.time.LocalDate;

//yyyymmdd format in lucene index
public class LocalDateBridge implements TwoWayStringBridge {

	public LocalDateBridge() {
	}

	public LocalDate stringToObject(String stringValue) {
		if (stringValue != null && stringValue.length() == 0) {
			return null;
		}
		try {
			String yr = stringValue.substring(0, 4);
			String mn = stringValue.substring(4, 6);
			String day = stringValue.substring(6, 8);
			return new LocalDate(yr + "-" + mn + "-" + day);
		} catch (Exception e) {
			throw new SearchException("Unable to parse into date: "
					+ stringValue, e);
		}
	}

	public String objectToString(Object object) {
		if (object instanceof LocalDate) {
			LocalDate date = (LocalDate) object;
			return "" + date.getYear() + date.getMonthOfYear()
					+ date.getDayOfMonth();
		}
		return null;
	}
}