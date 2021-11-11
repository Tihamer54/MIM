package discovery;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

import org.apache.commons.math3.util.CombinatoricsUtils;

public class KolmogorovBox {
	
	static void countToX(int x){
		for (int i=0;i<x;i++) {
			// Comment out one of the two System.out lines below to compare Inverse Kolmogorov Complexity - only 20 characters difference
			// results in 
			//System.out.printf(" %.0f", Math.pow(i, x));
			System.out.print(" " + i);
		}
	}
	
	public static BigDecimal TWO = new BigDecimal(2.0);
	public static MathContext roundMode = new MathContext(100, RoundingMode.HALF_EVEN);
	
	public BigDecimal factorial100SignificantDigits(BigDecimal n) {
		if (n.compareTo(TWO) <= 0) { return n; }
		return n.multiply(factorial100SignificantDigits(n.subtract(BigDecimal.ONE, roundMode)), roundMode);
	}
	
	public BigDecimal factorialExact(BigDecimal n) {
		if (n.compareTo(TWO) <= 0) { return n; }
		return n.multiply(factorialExact(n.subtract(BigDecimal.ONE)));
	}
	
	static BigInteger one = new BigInteger("1");
	static BigInteger zero = new BigInteger("0");
	public static BigInteger ackermann(BigInteger m, BigInteger n) {
	    if (m.equals(zero)) return n.add(one);
	    if (n.equals(zero)) return ackermann(m.subtract(one), one);
	    return ackermann(m.subtract(one), ackermann(m, n.subtract(one)));
	}
	
	static BigDecimal oneDecimal = new BigDecimal("1");
	static BigDecimal zeroDecimal = new BigDecimal("0");
	public static MathContext halfEven = new MathContext(10, RoundingMode.HALF_EVEN);

	public static BigDecimal ackermann100SignificantDigits(BigDecimal m, BigDecimal n) {
		//System.out.println("ackermann100SignificantDigits m=" + m + " n=" + n);
	    if (m.equals(zeroDecimal)) return n.add(oneDecimal, halfEven);
	    if (n.equals(zeroDecimal)) return ackermann100SignificantDigits(m.subtract(oneDecimal, halfEven), oneDecimal);
	    return ackermann100SignificantDigits(m.subtract(oneDecimal, halfEven), ackermann100SignificantDigits(m, n.subtract(oneDecimal, halfEven)));
	}
	
	public static long ackermann(long m, long n) {
		//System.out.println("Ackermann m=" + m + " n=" + n);
	    if (m == 0) return n + 1;
	    if (n == 0) return ackermann(m - 1, 1);
	    return ackermann(m - 1, ackermann(m, n - 1));
	}
	
	public static String measureElapsedTime(long startTime) {
		long endTime = System.currentTimeMillis();
		long elapsed = endTime - startTime;
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;
		long elapsedDays = elapsed / daysInMilli;
		elapsed = elapsed % daysInMilli;
		long elapsedHours = elapsed / hoursInMilli;
		elapsed = elapsed % hoursInMilli;
		long elapsedMinutes = elapsed / minutesInMilli;
		elapsed = elapsed % minutesInMilli;
		double elapsedSeconds = (double)elapsed / (double)secondsInMilli;
		//elapsed = elapsed - (elapsedDays*daysInMilli) - (elapsedHours*hoursInMilli) - (elapsedMinutes*minutesInMilli) - (elapsedSeconds*secondsInMilli);
		String elapsedString = elapsedDays + " days " + elapsedHours + " hours, " + 
				elapsedMinutes + " minutes, and " + elapsedSeconds + " seconds.";
		System.out.println("Finished in " + elapsedString);
		return elapsedString;
	}

	public static void main(String[] args) {
		System.out.println("Starting KolmogorovBox");
		KolmogorovBox kBox = new KolmogorovBox();
		long startTime = System.currentTimeMillis();
		//countToX(25);
		/*
		 * 9080 factorial100SignificantDigits=> 2.533644826479513058797727330874664724919150493157090051951539186484139827254251958043752516998063958E+31998
		 * 9080 factorialExact=>                 2533644826479513058797727330874664724919150493157090051951539186484139827254251958043752516998063984250427471087880... <for ten pages>
		 * But it has *lots* of zeros at the end, so was suspicious of round-off error.  Same with 9079 and every other number down to 10! 
		 * Duh.  We ha've multiplied the number by 10. And both 5 and 2.  And for larger numbers, but 20, 30, etc.
		 * So 100! should have about 20 zeros at the end, plus two for itself, and two for 25 and 4.
		 * 
		 * Solution Increase Thread Stack Size: 
			-Xss2m
			This will set the thread’s stack size to 2 mb. (default for Windows 64-bit JVM is 1024K)
			To do this in Eclipse: Run/Run Configurations..., then look for the applications entry in 'Java application').
			The arguments tab has a text box Vm arguments, enter -Xss2m
 
		 */
		int k = 100000;	
		BigDecimal factorial100K = kBox.factorial100SignificantDigits(new BigDecimal(k));
		System.out.println("\n " + k + " factorial100SignificantDigits=> " + factorial100K);
		BigDecimal seventyK = new BigDecimal(70000);
		System.out.println("\n " + k + " factorial100SignificantDigits * 70,000 => " + factorial100K.multiply(seventyK, roundMode));
		
		//Result: ten to almost half a million.
		//	 100000 factorial100SignificantDigits=> 2.824229407960347874293421578024535518477494926091224850578918086542977950901063017872551771413831187E+456573

		//System.out.println("\n " + k + " factorialExact=> " + kBox.factorialExact(new BigDecimal(k)));
//		k = 20;
//		System.out.println("\n " + k + " CombinatoricsUtils.factorial=>" + CombinatoricsUtils.factorial(k));
//		k=21;
//		System.out.println("\n " + k + " CombinatoricsUtils.factorial=>" + CombinatoricsUtils.factorial(k));
		
		try {
	//		System.out.println("\n 2,1=>" +ackermann(2, 1));
	//		System.out.println("\n 4,0=" +ackermann(4,0));
			System.out.println("\n 4,1=" +ackermann(4,1));  //  4,1=65533
			//System.out.print("\n ackermann: 4,2=");  
			//System.out.println(ackermann(4,2)); // Stack overflow in 2 minutes, and 34.671 seconds.
			BigDecimal two = new BigDecimal("2");
			BigDecimal three = new BigDecimal("3");
			BigDecimal four = new BigDecimal("4");
			//System.out.println(ackermann100SignificantDigits(four,two)); 
			System.out.print("\n ackermann: 4,2=");  
			System.out.println(ackermann100SignificantDigits(four, two)); 
		} catch (StackOverflowError sofe) {
			sofe.printStackTrace();
		}
		measureElapsedTime(startTime);
	}

}
