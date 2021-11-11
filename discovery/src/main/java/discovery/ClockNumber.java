package discovery;

import java.text.DecimalFormat;

public class ClockNumber implements Comparable {
	public int index;
	public String timeAsSingleString;
	public int timeAsSingleNumber;
	public int hours;
	public String hoursString;
	public int minutes;
	public String minutesString;
	public int hourTens;
	public int hourOnes;
	public int minuteTens;
	public int minuteOnes;

	public ClockNumber(int index, int hours, int minutes) {
		DecimalFormat decimalFormat = new DecimalFormat("00");
		this.index = index;
		this.timeAsSingleNumber = hours * 100 + minutes;
		this.hours = hours;
		this.minutes = minutes;
		String hourString = decimalFormat.format(hours);
		hourTens = Integer.parseInt(hourString.substring(0, 1));
		hourOnes = Integer.parseInt(hourString.substring(1, 2));
		String minuteString = decimalFormat.format(minutes);
		minuteTens = Integer.parseInt(minuteString.substring(0, 1));
		minuteOnes = Integer.parseInt(minuteString.substring(1, 2));
		this.timeAsSingleString = hourString + ":" + minuteString;
	}
	
	
	public ClockNumber(int index, String timeAsSingleString, int timeAsSingleNumber, int hours, int minutes, 
									int hourTens, int hourOnes, int minuteTens, int minuteOnes) {
		this.index = index;
		this.timeAsSingleString = timeAsSingleString;
		this.timeAsSingleNumber = timeAsSingleNumber;
		this.hours = hours;
		this.minutes = minutes;
		this.hourTens = hourTens;
		this.hourOnes = hourOnes;
		this.minuteTens = minuteTens;
		this.minuteOnes = minuteOnes;
	}


	@Override
	public String toString() {
		return hourTens + "" + hourOnes + ":" + minuteTens + "" + minuteOnes;
		//return "ClockNumber [" + hourTens + hourOnes + ":" + minuteTens + minuteOnes + "]";
	}


//	@Override
//	public int compareTo(Object o) {
//		ClockNumber e = (ClockNumber) o;
//	     return getTimeAsSingleString().compareTo(e.getTimeAsSingleString());
//	}
	
	public int compareTo(Object o) {
		ClockNumber e = (ClockNumber) o;
	     return ((Integer)getIndex()).compareTo((Integer)e.getIndex());
	}
	

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}


	public String getTimeAsSingleString() {
		return timeAsSingleString;
	}
	public void setTimeAsSingleString(String timeAsSingleString) {
		this.timeAsSingleString = timeAsSingleString;
	}


	public int getTimeAsSingleNumber() {
		return timeAsSingleNumber;
	}
	public void setTimeAsSingleNumber(int timeAsSingleNumber) {
		this.timeAsSingleNumber = timeAsSingleNumber;
	}


	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}


	public String getHoursString() {
		return hoursString;
	}
	public void setHoursString(String hoursString) {
		this.hoursString = hoursString;
	}


	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}


	public String getMinutesString() {
		return minutesString;
	}
	public void setMinutesString(String minutesString) {
		this.minutesString = minutesString;
	}


	public int getHourTens() {
		return hourTens;
	}
	public void setHourTens(int hourTens) {
		this.hourTens = hourTens;
	}


	public int getHourOnes() {
		return hourOnes;
	}
	public void setHourOnes(int hourOnes) {
		this.hourOnes = hourOnes;
	}


	public int getMinuteTens() {
		return minuteTens;
	}
	public void setMinuteTens(int minuteTens) {
		this.minuteTens = minuteTens;
	}


	public int getMinuteOnes() {
		return minuteOnes;
	}
	public void setMinuteOnes(int minuteOnes) {
		this.minuteOnes = minuteOnes;
	} 

}
