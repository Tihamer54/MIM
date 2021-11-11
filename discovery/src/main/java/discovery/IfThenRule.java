package discovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import gnu.jel.CompiledExpression;

public class IfThenRule implements Comparable {
	public String expression = "";
	public String intermediateExpression = "";
	public Object intermediateValue;
	public String description = "none";
	public int timesFired = 0;
	public CompiledExpression compiledExpression = null;
	public int timesEvaluated = 0;
	public Object evaluationResult = null;

	
	/**
	 * Constructors
	 */
	public IfThenRule(String expression, String description) {
		super();
		this.expression = expression;
		this.description = description;
	}
	
	public IfThenRule(String expression, String intermediateExpression, String description) {
		super();
		this.expression = expression;
		this.intermediateExpression = intermediateExpression;
		this.description = description;
	}
	
	/*
	 * For experimenting and development
	 */
	public static void main(String[] args) {
		IfThenRule rule1 = new IfThenRule("isSquarePyramidNumber(timeAsSingleNumberVar)", "The time as a single number is a square pyramid number.");
		int timeAsOneNumber = 819;
		boolean result = (boolean) JavaEvaluatorClock.evaluate(rule1.getExpression(), timeAsOneNumber);
		System.out.println("IfThenRule.main: evaluating timeAsOneNumber=" + timeAsOneNumber + " in expression '" + rule1.getExpression() + 
				"' result=" + result + " ==> " + rule1.getDescription());
		timeAsOneNumber = 820;
		result = (boolean) JavaEvaluatorClock.evaluate(rule1.getExpression(), timeAsOneNumber);
		System.out.println("IfThenRule.main:  evaluating timeAsOneNumber=" + timeAsOneNumber + " in expression '" + rule1.getExpression() + 
				"' result=" + result + (result ? (" ==> " + rule1.getDescription()) : ""));
		
		int hours = 3, minutes = 17;
		rule1 = new IfThenRule("isPrime(hours) && isPrime(minutes)", "Both hours and minutes are prime.");
		result = (boolean) JavaEvaluatorClock.evaluate(rule1.getExpression(), hours, minutes);
		System.out.println("IfThenRule.main: evaluating hours=" + hours + " minutes=" + minutes + " in expression '" + rule1.getExpression() + 
				"' result=" + result + " ==> " + rule1.getDescription());
		hours = 4;
		minutes = 12;
		result = (boolean) JavaEvaluatorClock.evaluate(rule1.getExpression(), hours, minutes);
		System.out.println("IfThenRule.main: evaluating hours=" + hours + " minutes=" + minutes + " in expression '" + rule1.getExpression() + 
				"' result=" + result + (result ? (" ==> " + rule1.getDescription()) : ""));
		
		IfThenRule rule3 = new IfThenRule("hours == minutes", "Hours is same as minutes.");
		ClockNumber clock = new ClockNumber(1, 10, 10);
		result = (boolean) JavaEvaluatorClock.evaluate(rule3.getExpression(),  clock.timeAsSingleString, clock.timeAsSingleNumber, clock.hours, clock.minutes, 
				clock.hourTens, clock.hourOnes, clock.minuteTens, clock.minuteOnes);
		System.out.println("IfThenRule.main: evaluating clock=" + clock + " in expression '" + rule3.getExpression() + 
				"' result=" + result + " ==> " + rule3.getDescription());
		
		IfThenRule rule4 = new IfThenRule("hours == (minutes + 1)", "Counting down from hours to minutes.");
		clock = new ClockNumber(4, 10, 9);
		result = (boolean) JavaEvaluatorClock.evaluate(rule4.getExpression(), clock.timeAsSingleString, clock.timeAsSingleNumber, clock.hours, clock.minutes, 
				clock.hourTens, clock.hourOnes, clock.minuteTens, clock.minuteOnes);
		System.out.println("IfThenRule.main: evaluating clock=" + clock + " in expression '" + rule4.getExpression() + 
				"' result=" + result + (result ? "" : (" ==> " + rule4.getDescription())));
		
		IfThenRule rule5 = new IfThenRule("isNumbersToLettersCipher(hourTens, hourOnes, minuteTens, minuteOnes)", 
											"numbersToLettersCipher(hourTens, hourOnes, minuteTens, minuteOnes)",
													"the individual Digits To Letters Cipher results in <result>");
		clock = new ClockNumber(5, 1, 44);
		result = (boolean) JavaEvaluatorClock.evaluate(rule5, clock.timeAsSingleString, clock.timeAsSingleNumber, clock.hours, clock.minutes, 
				clock.hourTens, clock.hourOnes, clock.minuteTens, clock.minuteOnes);
		System.out.println("IfThenRule.main: evaluating clock=" + clock + " in expression '" + rule5.getExpression() + 
				"' value='" + rule5.getIntermediateValue() + "' result=" + result + (result ? "" : (" ==> " + rule5.getDescription())));
		
		//writeRandomRule("IfThen"); takes forever
		///writeRandomRule("What do you want from life?  Well, you can't have that!"); literally takes many lifetimes
	}
	
	
	// TODO We may want an evaluator for every possible rule argument possibility. Otherwise the zeros may be get mislaid.
//	public String runRule(int hours, int minuteTens, int minuteOnes) {
//		boolean result = (boolean)JavaEvaluatorClock.evaluate(getCondition(), hours, minuteTens, minuteOnes);
//		if (result) { return expression; }
//		return "";
//	}
	
	public boolean evaluate(int hours, int minutes) {
		//System.out.println(" Where hours=" + hours + " and minutes=" + minutes);
		evaluationResult = (Boolean)JavaEvaluatorClock.evaluate(getExpression(), hours, minutes);
		timesEvaluated = timesEvaluated + 1;
		if (evaluationResult != null) { 
			boolean result = (Boolean)evaluationResult;
			if (result) { timesFired = timesFired + 1; }
			return result;
		}
		return false;
	}
	
	
	public boolean evaluate(String timeAsSingleString, int timeAsSingleNumber, int hours, int minutes, 
											int hourTens, int hourOnes, int minuteTens, int minuteOnes) {
		try {
			timesEvaluated = timesEvaluated + 1;
			System.out.println("Evaluating " + getExpression() + " on " + timeAsSingleString);
			evaluationResult = (Boolean)JavaEvaluatorClock.evaluate(getExpression(), timeAsSingleString, timeAsSingleNumber, hours, minutes, 
																		hourTens, hourOnes, minuteTens, minuteOnes);
			if (evaluationResult != null) {  
				boolean result = (Boolean)evaluationResult;
				if (result) { timesFired = timesFired + 1; }
				return result;
			}
			return false;
		} catch (ArithmeticException arex) {
			System.out.println("Tried to divide by zero at " + this.toString());
			return false;
		} catch (NullPointerException npex) {
			System.out.println("Null result at " + this.toString());
			return false;
		}
	}

	
	public String getExpression() {
		return expression;
	}
	public void setCondition(String condition) {
		this.expression = condition;
	}


	public String getDescription() {
		return description;	
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

	public String getIntermediateExpression() {
		return intermediateExpression;
	}

	public void setIntermediateExpression(String intermediateExpression) {
		this.intermediateExpression = intermediateExpression;
	}

	public Object getIntermediateValue() {
		return intermediateValue;
	}

	public void setIntermediateValue(Object intermediateValue) {
		this.intermediateValue = intermediateValue;
	}

	public int getTimesFired() {
		return timesFired;
	}

	public void setTimesFired(int timesFired) {
		this.timesFired = timesFired;
	}

	public CompiledExpression getCompiledExpression() {
		return compiledExpression;
	}

	public void setCompiledExpression(CompiledExpression compiledExpression) {
		this.compiledExpression = compiledExpression;
	}

	public int getTimesEvaluated() {
		return timesEvaluated;
	}

	public void setTimesEvaluated(int timesEvaluated) {
		this.timesEvaluated = timesEvaluated;
	}

	public Object getEvaluationResult() {
		return evaluationResult;
	}

	public void setEvaluationResult(Object evaluationResult) {
		this.evaluationResult = evaluationResult;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "IfThenRule [expression=" + expression + ", description=" + description + "]";
	}
	
	/**
	 * It takes a *long* time to randomly generate a rule.
	 * 
	 * @param targetRule
	 */
	public static void writeRandomRule(String targetRule) {
		long startTime = System.currentTimeMillis();
		String characters = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z a b c d e f g h i j k l m n o p q r s t u v w x y z 0 1 2 3 4 5 6 7 8 9 0 = \" . + ( )";
		String[] characterArray = characters.split(" ");
		ArrayList<String> characterList = new ArrayList<String>(Arrays.asList(characterArray)); 
		characterList.add(" ");
		int characterLength = characterList.size();
		System.out.println("characterLength=" + characterLength);

		if (targetRule.length() > 20) {
			System.err.println("Failure.  You don't have time for such foolishness. The string you are looking for (" + targetRule + ") will require " +
					(Math.pow(70, characterLength)/10000000.0) + " seconds.");
			return;
		}
		Random r = new Random();
		String letter1 = characterList.get(r.nextInt(characterLength));
		String actualRandomRule = "";
		//ArrayList<String> actualRuleList = new ArrayList();
		// 70 characters. Chances for just the first word (ifThen) to be correct is one in 70*70*70*70*70*70 = 117,649,000,000                                                                                // 100,000,000,000L  
		for (long j=0; j<10000000000L; j++) {
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<targetRule.length(); i++) {
				String randomCharacter = characterList.get(r.nextInt(characterLength));
				//actualRuleList.add(randomCharacter);
				sb.append(randomCharacter);
			}
			actualRandomRule = sb.toString();
			//log.info(j + ". actual random rule = " + actualRandomRule);
			if (j%10000000==0) { 
				System.out.println(j + ". actual random rule = " + actualRandomRule); 
			}
			if (actualRandomRule.equals(targetRule)) {
				System.out.println(j + ". SUCCESS! actual random rule = " + actualRandomRule);
				break;
			}
		}
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
		long elapsedSeconds = elapsed / secondsInMilli;
		System.out.println("Finished writeRandomRule in " + elapsedDays + " days " + elapsedHours + " hours, " + elapsedMinutes + 
							" minutes, and " + elapsedSeconds + " seconds.");
	}

	
	@Override
	public int compareTo(Object o) {
		IfThenRule e = (IfThenRule) o;
	     return ((String)getExpression()).compareTo((String)e.getExpression());
	} 

}
