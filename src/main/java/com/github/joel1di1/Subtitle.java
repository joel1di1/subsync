package com.github.joel1di1;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Subtitle {

	private static DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm:ss,SSS").withZone(DateTimeZone.UTC);
	
	public final int index;
	public final String text;
	public final DateTime startTime;
	public final DateTime endTime;
	
	public Subtitle(int index, String text, String startTime, String endTime) {
		super();
		this.index = index;
		this.text = text;
		this.startTime = fmt.parseDateTime(startTime);
		this.endTime = fmt.parseDateTime(endTime);
	}

	public Subtitle(int index, String text, String period) {
		super();
		this.index = index;
		this.text = text;
		
		String[] times = period.split(" --> ");
		
		this.startTime = fmt.parseDateTime(times[0]);
		this.endTime = fmt.parseDateTime(times[1]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.toDate().hashCode());
		result = prime * result + index;
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.toDate().hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subtitle other = (Subtitle) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.toDate().equals(other.endTime.toDate()))
			return false;
		if (index != other.index)
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.toDate().equals(other.startTime.toDate()))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
}
