package discovery;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.tritonus.share.ArraySet;
/**
 * The Main class calls everything having to do with finding interesting numbers on a digital clock.
 * Including finding interesting rules.
 * First, it calls the manual rules.
 * Second, it calls the meta-rules that generates rules.
 * Third (if possible) it calls meta-meta-rules that generates meta-rules that generate rules. This may require HOL-type capabilities.
 * 
 * First question-- Are the results more or less interesting?  Do the results seem to cover more possibilities?The Most Interesting Clock Times:
 * Second question - Is the code getting more or less complex? Is the code getting more or less interesting?
 * 
 * Note:The JEL project has not been mavenized yet (on my TODO list), so it does not play well with others. You may need to manually copy it to your .m2 directory.
 * Check the pom for details.
 * 
 * @author ttoth-fejel
 *
 */
public class MainRunner {
	public static DecimalFormat decimalFormat = new DecimalFormat("00");
	public ArrayList<String> clockMinutes = new ArrayList<String>();
	//A LinkedHashMap keeps the keys in the order they were inserted
	public LinkedHashMap <String, ArrayList<IfThenRule>> clockRulesMap = new LinkedHashMap <String, ArrayList<IfThenRule>>();
	static int totalCount = 0;
	public int totalInterestingTimes = 0;
	public int totalBoringTimes = 0;
	public ArrayList<IfThenRule> interestingRules = new ArrayList<IfThenRule>();
	public ArrayList<IfThenRule> boringRules = new ArrayList<IfThenRule>();

	public ArrayList<IfThenRule> initialRules = new ArrayList<IfThenRule>();
	public ArrayList<IfThenRule> resultRules = new ArrayList<IfThenRule>();


	public static void main(String[] args) {
		System.out.println("Starting MainRunner main.");
		long startTime = System.currentTimeMillis();
		//		System.out.println("Is 144 fib?" + VariableProviderClock.isFibonacci(144));
		//		System.out.println("2^1,048,576=" + Math.pow(2.0, 1048576.0));

		MainRunner main = new MainRunner();
		//ArrayList<IfThenRule> originalRules = main.getManualRules();
		ArrayList<IfThenRule> originalRules = getMetaRules();
		// Show all rules
				for (IfThenRule rule : originalRules) {
					System.out.println("rule: " + rule);
				}

		ArrayList<ClockNumber> clockNumbers = main.getClockNumbers();
		// Show all clock numbers
		for (ClockNumber clock : clockNumbers) {
			System.out.println("clock: " + clock);
		}
		// The following modifies clockRulesMap.
		main.runRules(clockNumbers, originalRules);

		LinkedHashMap <String, ArrayList<IfThenRule>> mostInterestingClocks = new LinkedHashMap <String, ArrayList<IfThenRule>>();
		//LinkedHashMap <String, Integer> mostInterestingRules = new LinkedHashMap <String, Integer>();
		LinkedHashMap <IfThenRule, Integer> mostInterestingRules = new LinkedHashMap <IfThenRule, Integer>();
		// The following extracts the interesting information
		for (Map.Entry<String, ArrayList<IfThenRule>> entry : main.clockRulesMap.entrySet()) {
			int size = entry.getValue().size();
			if (size>4) {
				mostInterestingClocks.put(entry.getKey(), entry.getValue());
			}
			ArrayList<IfThenRule> rulesAtThisClock = (ArrayList<IfThenRule>)entry.getValue();
			for (IfThenRule rule : rulesAtThisClock) {
				String expression = rule.getExpression();
				if (mostInterestingRules.containsKey(rule)) {
					Integer foundSoFar = mostInterestingRules.get(rule);
					mostInterestingRules.put(rule, foundSoFar + 1);
				} else 
					mostInterestingRules.put(rule, 1);
			}
		}

		System.out.println("\n The Most Interesting Clock Times:");
		for (Map.Entry<String, ArrayList<IfThenRule>> entry : mostInterestingClocks.entrySet()) {
			System.out.println(entry.getKey() +  " count=" + entry.getValue().size() + ", " + entry.getValue()); 
		}

		System.out.println("\n The Most Interesting Rules:");
		//		LinkedHashMap<String, Integer> sortedInterestingRules = new LinkedHashMap<String, Integer>();
		//		mostInterestingRules.entrySet().stream()
		//		    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
		//		    .forEachOrdered(x -> sortedInterestingRules.put(x.getKey(), x.getValue()));
		//		for (Map.Entry<String, Integer> entry : sortedInterestingRules.entrySet()) {
		//			System.out.println(entry.getKey() + ", Frequency=" + entry.getValue()); 
		//		}

		LinkedHashMap<IfThenRule, Integer> orderedInterestingRules = new LinkedHashMap<IfThenRule, Integer>();
		mostInterestingRules.entrySet().stream()
		.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
		.forEachOrdered(x -> orderedInterestingRules.put(x.getKey(), x.getValue()));
		for (Map.Entry<IfThenRule, Integer> entry : orderedInterestingRules.entrySet()) {
			System.out.println(entry.getKey() + ", Frequency=" + entry.getValue()); 
		}
		Utils.measureElapsedTime(startTime);
		
//		// TODO: make it automatically use java Math functions
//		booleanPropertyMethods = JavaReflectionExplorer.getOneInOneOutMethods("java.lang.Math", "number-boolean");
//		System.out.println("/n booleanPropertyMethods=" + booleanPropertyMethods);
//		for (Method method : booleanPropertyMethods) {
//			System.out.println("    " + method.getName());
//			functions.add(method.getName());
//		}
	}


	public MainRunner() {
		super();
		this.clockMinutes = new ArrayList<String>();
		this.clockRulesMap = new LinkedHashMap <String, ArrayList<IfThenRule>>();
	}

	public MainRunner(ArrayList<String> clockMinutes, LinkedHashMap <String, ArrayList<IfThenRule>> rulesMap) {
		super();
		this.clockMinutes = clockMinutes;
		this.clockRulesMap = rulesMap;
	}


	public void runRules(ArrayList<ClockNumber> clockNumbers, ArrayList<IfThenRule> rules) {
		System.out.println("Running " + clockNumbers.size() + " clock Numbers against " + rules.size() + " rules.");
		for (ClockNumber clockNumber : clockNumbers) {
			ArrayList<IfThenRule> firedRules = runClockRules(clockNumber, rules);
			//System.out.println("clockNumber="+ clockNumber);
			if (clockRulesMap.containsKey(clockNumber.toString())) {
				ArrayList<IfThenRule> previousfiredRules = clockRulesMap.get(clockRulesMap);
				if (previousfiredRules != null) { firedRules.addAll(previousfiredRules); }
				clockRulesMap.put(clockNumber.toString(), firedRules);
			} else {
				clockRulesMap.put(clockNumber.toString(), firedRules);
			}
		}
	}


	/**
	 * Must transpose index=0 --> Midnight/Noon is 12:00;
	 * @return
	 */
	public ArrayList<ClockNumber> getClockNumbers() {
		ArrayList<ClockNumber> clockNumberList = new ArrayList<ClockNumber>();
		int hours = 12;  // midnight or noon
		int i = 0;
		String hourString = "12";
		int hourTens = 1;
		int hourOnes = 2;
		String minuteString = "";
		int minuteTens = 0;
		int minuteOnes = 0;
		int timeAsSingleNumber = 0;
		String timeAsSingleString = "";
		for (int minutes=0; minutes<60; minutes++) {
			minuteString = decimalFormat.format(minutes);
			minuteTens = Integer.parseInt(minuteString.substring(0, 1));
			minuteOnes = Integer.parseInt(minuteString.substring(1, 2));
			timeAsSingleNumber = (1000 * hourTens) + (100 * hourOnes) + (10 * minuteTens) + minuteOnes;
			timeAsSingleString = "" + hourTens + hourOnes + minuteTens + minuteOnes;
			//System.out.println("Creating1 " + hourTens + "" + hourOnes + ":" + minuteTens + "" + minuteOnes);
			clockNumberList.add(new ClockNumber(i, timeAsSingleString, timeAsSingleNumber, hours, minutes, hourTens, hourOnes, minuteTens, minuteOnes));
			i = i + 1;
		}
		for (hours=1; hours<=11; hours++) {
			hourString = decimalFormat.format(hours);
			hourTens = Integer.parseInt(hourString.substring(0, 1));
			hourOnes = Integer.parseInt(hourString.substring(1, 2));
			for (int minutes=0; minutes<60; minutes++) {
				minuteString = decimalFormat.format(minutes);
				minuteTens = Integer.parseInt(minuteString.substring(0, 1));
				minuteOnes = Integer.parseInt(minuteString.substring(1, 2));
				timeAsSingleNumber = (1000 * hourTens) + (100 * hourOnes) + (10 * minuteTens) + minuteOnes;
				timeAsSingleString = "" + hourTens + hourOnes + minuteTens + minuteOnes;
				//System.out.println("Creating2 " + hourTens + hourOnes + ":" + minuteTens + minuteOnes);
				clockNumberList.add(new ClockNumber(i, timeAsSingleString, timeAsSingleNumber, hours, minutes, hourTens, hourOnes, minuteTens, minuteOnes));
				i = i + 1;
			}
		}
		//Collections.sort(clockNumberList);  // counts from 1:00 to 9:59
		return clockNumberList;
	}


	public ArrayList<IfThenRule> runClockRules(ClockNumber clockNumber, ArrayList<IfThenRule> rules) {
		ArrayList<IfThenRule> firedRules = new ArrayList<IfThenRule>();
		for (IfThenRule rule : rules) {
			boolean ruleResult = rule.evaluate(clockNumber.timeAsSingleString, clockNumber.timeAsSingleNumber, clockNumber.hours, clockNumber.minutes, 
					clockNumber.hourTens, clockNumber.hourOnes, clockNumber.minuteTens, clockNumber.minuteOnes);
			if (ruleResult) { 
				System.out.println(clockNumber + " " + rule.description + " timesEvaluated=" + rule.timesEvaluated + " timesFired=" + rule.timesFired );
				firedRules.add(rule);
			}
		}
		return firedRules;
	}


	public static ArrayList<IfThenRule> getManualRules() {
		ArrayList<IfThenRule> rules = new ArrayList<IfThenRule>();
		//System.out.println("hours=" + hours + " minutes=" + minutes + " minuteTens=" + minuteTens + " minuteOnes=" + minuteOnes + "timeAsSingleNumber=" + timeAsSingleNumber);
		rules.add(new IfThenRule("hours == minutes", "Hours is same as minutes."));  //1
		rules.add(new IfThenRule("hours == (minutes + 1)", "Counting down from hours to minutes."));
		rules.add(new IfThenRule("(hours == (minutes - 1))", "Counting up from hours to minutes."));
		rules.add(new IfThenRule("(hourTens == (hourOnes + 1) && hourOnes == (minuteTens + 1) && minuteTens == (minuteOnes + 1))", 
				"Counting down digit by digit from hours to minutes."));
		rules.add(new IfThenRule("(hourTens == (hourOnes - 1) && hourOnes == (minuteTens - 1) && minuteTens == (minuteOnes - 1))",
				"Counting up digit by digit from minutes to hours."));
		rules.add(new IfThenRule("(hours == minutes*2)", "Hours is twice as big as minutes."));
		rules.add(new IfThenRule("(hours*2 == minutes)", "Hours is half of minutes."));
		rules.add(new IfThenRule("(hours == minutes*3)", "Hours is three times as big as minutes."));
		rules.add(new IfThenRule("(hours*3 == minutes)", "Hours is a third of minutes."));
		rules.add(new IfThenRule("(hours == minutes*4)", "Hours is four times as big as minutes."));  //10
		rules.add(new IfThenRule("(hours*4 == minutes)", "Hours is a quarter of minutes."));
		rules.add(new IfThenRule("(hours == minutes*5)", "Hours is five times as big as minutes."));
		rules.add(new IfThenRule("(hours*5 == minutes)", "Hours is a fifth of minutes."));
		rules.add(new IfThenRule("(hours == minutes*minutes)", "Hours is minutes squared."));
		rules.add(new IfThenRule("(hours*hours == minutes)", "Minutes is Hours squared."));
		rules.add(new IfThenRule("(hourTens == minuteOnes && hourOnes == minuteTens)", "Mirror Mirror."));
		rules.add(new IfThenRule("(hours == 31 && minutes == 41)", "Throw PI!"));
		rules.add(new IfThenRule("(hours == 11 && minutes == 23)", "Fibbonacci!"));
		rules.add(new IfThenRule("(hourTens + hourOnes == minuteTens + minuteOnes && hours != minutes)", "Hours digits added equals Minutes digits added.")); 
		rules.add(new IfThenRule("(hourTens == hourOnes && minuteTens == minuteOnes)", "Hours and Minutes both doubles."));       //20
		rules.add(new IfThenRule("(hourTens == hourOnes && hourOnes == minuteTens && minuteTens== minuteOnes)", "All the same digit.")); 
		rules.add(new IfThenRule("(hourTens + hourOnes + minuteTens == minuteOnes)", "First three digits added up equals last digit.")); 
		rules.add(new IfThenRule("(hourTens == 0 && hourOnes == minuteTens && minuteTens == minuteOnes)", "Last three digits match 37 Trick (xxx/(x+x+x)=37).")); 
		rules.add(new IfThenRule("(hourTens == 0 && (pow(hourOnes,3) + pow(minuteTens,3) + pow(minuteOnes,3) == timeAsSingleNumber))", "Hardy Narcissistic: Last three digits each cubed and then added to each other equals time as a single number.")); 
		rules.add(new IfThenRule("(hours == minuteTens * minuteOnes)", "Hours are the result of multiplying the minute digits."));
		rules.add(new IfThenRule("(minutes == hourTens * hourOnes)", "Minutes are the result of multiplying the hour digits."));
		rules.add(new IfThenRule("isFactorOf(hours, minutes)", "Minutes is a factor of the hours."));
		rules.add(new IfThenRule("isFactorOf(minutes, hours)", "Hours is a factor of the minutes."));
		rules.add(new IfThenRule("isFactorOf(hours, minuteTens) && isFactorOf(hours, minuteOnes)", "The digits of the minutes are factors of the hours."));  
		rules.add(new IfThenRule("(hours == 4 && minutes == 0)",
				"This is the exact time that God woke me up and told me to get up and lose two more pounds at the USA Wrestling Nationals. I ended up second. :-)"));
		rules.add(new IfThenRule("(hours == 9 && minutes == 29)", "This is the month and day that Stephen was born."));
		rules.add(new IfThenRule("(hours == 12 && minutes == 26)", "This is the month and day that Miriam was born."));  //30 
		rules.add(new IfThenRule("(hours == 1 && minutes == 6)", "On this is the month and day Mother Teresa arrived in India (1929) and Alfred Wegener presented his theory of continental drift (1912).")); 
		rules.add(new IfThenRule("(hours == 1 && minutes == 8)", "On this is the month and day Herman Hollerith patented his punched card calculator, and we've been suffering from the 80 character limit ever since.")); 
		rules.add(new IfThenRule("(hours == 1 && minutes == 14)", "On this is the month and day the Huygens space probe landed on Saturn's moon Titan (2005).")); 
		rules.add(new IfThenRule("(hours == 1 && minutes == 17)", "This is the month and day that Sophie was born.")); 
		rules.add(new IfThenRule("(hours == 12 && minutes == 25)", "Merry Christmas!")); 
		rules.add(new IfThenRule("(hours == 6 && minutes == 02)", "When raised to the 23rd power is Avogadro's Number.")); 
		rules.add(new IfThenRule("(hours == 3 && minutes == 42)", "Both are a Sum of Three cubes. 3 = (1^3 + 1^3 + 1^3) = (4^3 + 4^3 + (-5)^3) while 42 = (-80,538,738,812,075,974)^3 + 14,197,965,759,741,571^3 + 2,337,348,783,323,923^3).")); 
		rules.add(new IfThenRule("(isPrime(hours) && isPrime(minutes))", "Both hours and minutes are prime."));
		rules.add(new IfThenRule("(isFibonacci(hours) && isFibonacci(minutes))", "Both hours and minutes are Fibonacci numbers."));
		rules.add(new IfThenRule("intersection(factors(hours), factors(minutes)).length() == 0", "The hours and minutes have no common factors (relatively prime)."));
		rules.add(new IfThenRule("(factors(hours).equals(factors(minutes)))", "The hours and minutes have exactly the same factors."));
		rules.add(new IfThenRule("(isSquarePyramidNumber(hours) && isSquarePyramidNumber(minutes))", "Both hours and minutes are Square Pyramid Numbers."));
		rules.add(new IfThenRule("((hours%2 == 0) && (minutes%2 == 0))", "Both hours and minutes are boringly even."));   // 40
		rules.add(new IfThenRule("((hours%3 == 0) && (minutes%3 == 0))", "Both hours and minutes are divisible by three."));
		rules.add(new IfThenRule("((hours%4 == 0) && (minutes%4 == 0))", "Both hours and minutes are divisible by four."));
		rules.add(new IfThenRule("((hours%5 == 0) && (minutes%5 == 0))", "Both hours and minutes are divisible by five."));
		rules.add(new IfThenRule("((hours%6 == 0) && (minutes%6 == 0))", "Both hours and minutes are divisible by six."));
		rules.add(new IfThenRule("(isPerfectSquare(hours) && isPerfectSquare(minutes))", "Both hours and minutes are perfect squares."));
		rules.add(new IfThenRule("(timeAsSingleNumber == 203)", "The time as a single number (203) is a Bell number (x^0 + x^2 + x^3 is prime).")); 
		rules.add(new IfThenRule("isPalidrome(timeAsSingleString)", "The time as a single string is a palidrome.")); 
		rules.add(new IfThenRule("isPrime(timeAsSingleNumber)", "The time as a single number is prime.")); 
		rules.add(new IfThenRule("isSquarePyramidNumber(timeAsSingleNumber)", "The time as a single number is a Square Pyramid.")); 
		rules.add(new IfThenRule("isPerfectSquare(timeAsSingleNumber)", "The time as a single number is a perfect square.")); 
		rules.add(new IfThenRule("isPerfectCube(timeAsSingleNumber)", "The time as a single number is a perfect cube.")); 
		rules.add(new IfThenRule("isFibonacci(timeAsSingleNumber)", "The time as a single number is a Fibonacci Number.")); 

		//		//TODO: Palindromic curiosity: 122 x 122 = 14884 and 48841 = 221 x 221  // how do you write a meta-rule for that?  This is actually for a different problem: interesting relationships between digital times.

		//		if (contains(words, numbersToLettersCipher(hourTens, hourOnes, minuteTens, minuteOnes))) {
		//			String matchedWord = numbersToLettersCipher(hourTens, hourOnes, minuteTens, minuteOnes);  
		//			ruleResults = ruleResults + "The number to letter cipher spells out the word '" + matchedWord + "'. ";
		//			countDiscoveries++;
		//		}
		//		if (minutes == 0) {
		//			String dingdongs = StringUtils.repeat("DingDong ", hours);  
		//			ruleResults = ruleResults + "Grandfather clock goes " + dingdongs.trim() + ". ";
		//			countDiscoveries++;
		//		}
		//		if (ruleResults.equals("")) { 
		//			countBoring = countBoring + 1;
		//			return "Nothing. "; 
		//		}
		return rules;
	}


	static ArraySet<String> functions = new ArraySet<String>();
	/**
	 * For more ideas, see https://en.wikipedia.org/wiki/Combinatorics
	 * @return
	 */
	public static ArrayList<IfThenRule> getMetaRules() {
		ArrayList<Method> booleanPropertyMethods = JavaReflectionExplorer.getOneInOneOutMethods("discovery.VariableProviderClock", "number-boolean");
		System.out.println("/n booleanPropertyMethods=" + booleanPropertyMethods);
		for (Method method : booleanPropertyMethods) {
			System.out.println("    " + method.getName());
			functions.add(method.getName());
		}
		ArrayList<IfThenRule> singles = generateSingleRules();
		ArrayList<IfThenRule> both = generateBothRules();
		ArrayList<IfThenRule> singlesBoth = VariableProviderClock.union(singles, both);
		ArrayList<IfThenRule> hourMinutes = generateHourMinuteRules();
		return VariableProviderClock.union(singlesBoth, hourMinutes);
	}

	/**
	 * When both have the same property ie. prime, even, same value, etc.
	 * @return
	 */
	public static ArrayList<IfThenRule> generateBothRules() {
		ArrayList<IfThenRule> result = new ArrayList<IfThenRule>();
		String[] singleOperations = { "isPrime", "isSquarePyramidNumber", "isPerfectSquare", "isPerfectCube", "isFibonacci", "isPi", "isEuler",
					 "isSumOfItsDigitsCubes", "isSumOfItsDigitsSquares"};
		int count = 0;
		for (String operation : singleOperations) {
			totalCount = totalCount + 1;
			count = count + 1;
			String condition = "(" + operation + "(hours) && " + operation + "(minutes))";
			String description = "Both hours and minutes " + camelToSentence(operation).replace("is", "are");
			System.out.println(count + ". rule: " + condition + " \"" + description + "\"");
			result.add(new IfThenRule(condition, description));
		}
		return result;
	}

	
	public static ArrayList<IfThenRule> generateSingleRules() {
		ArrayList<IfThenRule> result = new ArrayList<IfThenRule>();
		String[] mathFunctions = { "isPrime", "isSquarePyramidNumber", "isPerfectSquare", "isPerfectCube", "isFibonacci", "isPi", "isEuler",
					 "isLucky", "isSquareLucky"};
		/* functions is defined earlier from VariableProviderClock. e.g.:
	    isPerfectSquare, isPi, isPerfectCube, isEuler, isPrime, isFibonacci, isSquarePyramidNumber
	    isMersennePrime, isGoldenRatio, isSumOfCubesOfDigits, isSumOfSquaresOfDigits
	    TODO:  Search the internet  (or maybe just wikipedia) for famous dates e.g. 7/4/1776, 9/11, 
	    */
		int count = 0;
		for (String operation : functions) {
			totalCount = totalCount + 1;
			count = count + 1;
			String condition = "(" + operation + "(timeAsSingleNumber))";
			String description = "Time as single number " + camelToSentence(operation);
			System.out.println(count + ". rule: " + condition + " \"" + description + "\"");
			result.add(new IfThenRule(condition, description));
		}
		return result;
	}


	/*
	 * Generates all combinations that take the time as two numbers.
	 * Possibilities have the following forms:
	 *  Some Arithmetic Equivalence
	 *  	"hours OP constant(1-12) == minutes"  where OP can be plus, minus, times, or divided by
	 *  	"minutes OP constant == hours"
	 *  	"Math.FN(minutes) == hours"  where FN can be any java.Math function that takes an integer as input and returns an integer.
	 *  	"Math.FN(hours) == minutes"
	 *  Some shared property: EG:
	 *  	prime(hours) && prime(minutes)
	 */
	public static ArrayList<IfThenRule> generateHourMinuteRules() {
		System.out.println("Generate all combinations of (hoursOrMinutes operation [1-12] == minutesOrHours).");
		ArrayList<IfThenRule> result = new ArrayList<IfThenRule>();
		String realNumber1 = "";
		String realNumber2 = "";
		String[] numbers = { "hours", "minutes" };
		String[] arithmeticOperations = { "plus", "minus", "times", "divided by" };
		String[] realOperations = { "+", "-", "*", "/" };
		int count = 0;
		totalCount = totalCount + 1;
		count = count + 1;
		// Identity relationships
		for (int m=0; m<2; m++) {  // to reverse the order
			String number1, number2;
			if (m==0) {
				number1 = numbers[0]; 
				number2 = numbers[1];
			} else {
				number1 = numbers[1];
				number2 = numbers[0];
			}
			String condition = "(" + number1 + " == " + number2 + ")";
			String description = number1 + " is equal to " + number2;
			System.out.println(count + ". rule: " + condition + " \"" + description + "\"");
			result.add(new IfThenRule(condition, description));
		}
		// Operator inline on one side with a constant
		for (int constant=0; constant<=12; constant++) {
			for (int m=0; m<2; m++) {  // to reverse the order
				String number1, number2;
				if (m==0) {
					number1 = numbers[0]; 
					number2 = numbers[1];
				} else {
					number1 = numbers[1];
					number2 = numbers[0];
				}
				//System.out.println("number1=" + number1);
				//System.out.println("number2=" + number2);
				// System.out.println("Working on combinations of : " + number1 + " " + Arrays.toString(realOperations) + " " + constant +" = " + number2);
				for (int k=0; k<arithmeticOperations.length; k++) {
					String operation = arithmeticOperations[k];
					String realOperation = realOperations[k];
					realNumber1 = number1;
					realNumber2 = number2;
					totalCount = totalCount + 1;
					count = count + 1;
					String condition = "(" + realNumber1 + " " + realOperation + " " + constant + " == " + realNumber2 + ")";
					String description = realNumber1 + " " + operation + " " + constant + " is equal to " + realNumber2;
					//System.out.println(count + ". expression is: " + expression);
					//System.out.println(count + ". description is: " + description);
					// Ignore if identities (+/- 0; */ 1);
					if (constant == 0 && (realOperation.equals("+") || realOperation.equals("-"))) { continue; }
					if (constant == 1 && (realOperation.equals("*") || realOperation.equals("/"))) { continue; }
					System.out.println(count + ". rule: " + condition + " \"" + description + "\"");
					result.add(new IfThenRule(condition, description));
				}
			}
		}
		String[] powerOperations = { "squared", "cubed", "raised to the fourth power", "raised to the fifth power"};
		String[] powerExponents = { "2", "3", "4", "5" };
		for (int m=0; m<2; m++) {  // to reverse the order
			String number1, number2;
			if (m==0) {
				number1 = numbers[0];
				number2 = numbers[1];
			} else {
				number1 = numbers[1];
				number2 = numbers[0];
			}
			System.out.println("Working on combinations of : " + number1 + " " + Arrays.toString(powerOperations)  +" = " + number2);
			for (int k=0; k<powerOperations.length; k++) {
				totalCount = totalCount + 1;
				count = count + 1;
				String condition = "(pow(" + number1 + ", " + powerExponents[k]+ ") == " + number2 + ")";
				String description = number1 + " " + powerOperations[k] + " is equal to " + number2;
				System.out.println(count + ". rule: " + condition + ", \"" + description + "\"");
				result.add(new IfThenRule(condition, description));
			}
		}
		// Other functions comparing one number to another
		String[] mathOperations = { "isFactorOf", "isRelativelyPrimeTo", "hasSameFactorsAs", "isSquareRootOf", "isCubeRootOf" };
		for (int m=0; m<2; m++) {  // to reverse the order
			String number1, number2;
			if (m==0) {
				number1 = numbers[0];
				number2 = numbers[1];
			} else {
				number1 = numbers[1];
				number2 = numbers[0];
			}
			System.out.println("Working on combinations of : " + number1 + " " + Arrays.toString(mathOperations)  +" = " + number2);
			for (String mathOperation : mathOperations) {
				String englishOperation = camelToSentence(mathOperation);
				String condition = "(" + mathOperation + "(" + number1 + "," + number2 + "))";
				String description = number1 + " " + englishOperation + " " + number2;
				System.out.println(count + ". rule: " + condition + ", \"" + description + "\"");
				result.add(new IfThenRule(condition, description));
			}
		}
		//		ArrayList<Method> intIntOperationMethods = JavaReflectionExplorer.getOneInOneOutMethods("java.lang.Math", "int-int");
		//		ArrayList<Method> doubleDoubleOperationMethods = JavaReflectionExplorer.getOneInOneOutMethods("java.lang.Math", "double-double");
		return result;
	}
	
	
	/*
	 * Generates all combinations that ultimately take two numbers.
	 * Possibilities have the following forms:
	 *   Equivalence Relationships-- 
	 *   	H == M where 
	 *   	H = hours, or hours OP constant, or Math.FN(hours), or Math.FN(hoursTens OP hoursOnes), or "hoursTens OP hoursOnes" 
	 *   					where OP can be plus, minus, times, or divided by.
	 *      and
	 *      M = minutes, or minutes OP constant, or Math.FN(minutes), or Math.FN(minutesTens OP minutesOnes), or "minutesTens OP minutesOnes".
	 *      				Where OP can be the same or different; 
	 *      e.g.: 
	 *      		squared(hours) == minuteTens * minutesOnes  (2:22 and 2:14 and 2:41)
	 *      		squared(hours+1) == minuteTens + minuteOnes (2:54)  -- seems too baroque; should be more elegant
	 *      		squared(hours) == squared(minuteTens) - squared(minuteOnes)  (3:54)
	 *      		squared(hours) == minuteTens + minuteOnes (3:54)
	 *  Shared Properties-- E.g.:
	 *  	Both isPrime(hours) AND isPrime(minutes) 
	 *  */ 
	public static ArrayList<IfThenRule> generateHourDigitsMinuteDigitsRules() {
		System.out.println("Generate all combinations of (hoursOrMinutes operation [1-12] == minutesOrHours).");
		ArrayList<IfThenRule> result = new ArrayList<IfThenRule>();
		String realNumber1 = "";
		String realNumber2 = "";
		String[] numbers = { "H", "M" };
		String[] numbersH = { "hours", "Math.FN(hours)", "Math.FN(hoursTens OP hoursOnes)", "hoursTens OP hoursOnes" };
		String[] numbersM = { "minutes", "Math.FN(minutes)", "Math.FN(minutesTens OP minutesOnes)", "minutesTens OP minutesOnes" };
		String[] arithmeticOperations = { "plus", "minus", "times", "divided by" };
		String[] mathFunctions = { "plus", "minus", "times", "divided by" };
		String[] realOperations = { "+", "-", "*", "/" };
		int count = 0;
		totalCount = totalCount + 1;
		count = count + 1;
		// Identity relationships
		for (int m=0; m<2; m++) {  // to reverse the order
			String number1, number2;
			if (m==0) {
				number1 = numbers[0]; 
				number2 = numbers[1];
			} else {
				number1 = numbers[1];
				number2 = numbers[0];
			}
			String condition = "(" + number1 + " == " + number2 + ")";
			String description = number1 + " is equal to " + number2;
			System.out.println(count + ". rule: " + condition + " \"" + description + "\"");
			result.add(new IfThenRule(condition, description));
		}
		// Operator inline on one side with a constant
		for (int constant=0; constant<=12; constant++) {
			for (int m=0; m<2; m++) {  // to reverse the order
				String number1, number2;
				if (m==0) {
					number1 = numbers[0]; 
					number2 = numbers[1];
				} else {
					number1 = numbers[1];
					number2 = numbers[0];
				}
				//System.out.println("number1=" + number1);
				//System.out.println("number2=" + number2);
				// System.out.println("Working on combinations of : " + number1 + " " + Arrays.toString(realOperations) + " " + constant +" = " + number2);
				for (int k=0; k<arithmeticOperations.length; k++) {
					String operation = arithmeticOperations[k];
					String realOperation = realOperations[k];
					realNumber1 = number1;
					realNumber2 = number2;
					totalCount = totalCount + 1;
					count = count + 1;
					String condition = "(" + realNumber1 + " " + realOperation + " " + constant + " == " + realNumber2 + ")";
					String description = realNumber1 + " " + operation + " " + constant + " is equal to " + realNumber2;
					//System.out.println(count + ". expression is: " + expression);
					//System.out.println(count + ". description is: " + description);
					// Ignore if identities (+/- 0; */ 1);
					if (constant == 0 && (realOperation.equals("+") || realOperation.equals("-"))) { continue; }
					if (constant == 1 && (realOperation.equals("*") || realOperation.equals("/"))) { continue; }
					System.out.println(count + ". rule: " + condition + " \"" + description + "\"");
					result.add(new IfThenRule(condition, description));
				}
			}
		}
		String[] powerOperations = { "squared", "cubed", "raised to the fourth power", "raised to the fifth power"};
		String[] powerExponents = { "2", "3", "4", "5" };
		for (int m=0; m<2; m++) {  // to reverse the order
			String number1, number2;
			if (m==0) {
				number1 = numbers[0];
				number2 = numbers[1];
			} else {
				number1 = numbers[1];
				number2 = numbers[0];
			}
			System.out.println("Working on combinations of : " + number1 + " " + Arrays.toString(powerOperations)  +" = " + number2);
			for (int k=0; k<powerOperations.length; k++) {
				totalCount = totalCount + 1;
				count = count + 1;
				String condition = "(pow(" + number1 + ", " + powerExponents[k]+ ") == " + number2 + ")";
				String description = number1 + " " + powerOperations[k] + " is equal to " + number2;
				System.out.println(count + ". rule: " + condition + ", \"" + description + "\"");
				result.add(new IfThenRule(condition, description));
			}
		}
		// Other functions comparing one number to another
		String[] mathOperations = { "isFactorOf", "isRelativelyPrimeTo", "hasSameFactorsAs", "isSquareRootOf", "isCubeRootOf" };
		for (int m=0; m<2; m++) {  // to reverse the order
			String number1, number2;
			if (m==0) {
				number1 = numbers[0];
				number2 = numbers[1];
			} else {
				number1 = numbers[1];
				number2 = numbers[0];
			}
			System.out.println("Working on combinations of : " + number1 + " " + Arrays.toString(mathOperations)  +" = " + number2);
			for (String mathOperation : mathOperations) {
				String englishOperation = camelToSentence(mathOperation);
				String condition = "(" + mathOperation + "(" + number1 + "," + number2 + "))";
				String description = number1 + " " + englishOperation + " " + number2;
				System.out.println(count + ". rule: " + condition + ", \"" + description + "\"");
				result.add(new IfThenRule(condition, description));
			}
		}
		//		ArrayList<Method> intIntOperationMethods = JavaReflectionExplorer.getOneInOneOutMethods("java.lang.Math", "int-int");
		//		ArrayList<Method> doubleDoubleOperationMethods = JavaReflectionExplorer.getOneInOneOutMethods("java.lang.Math", "double-double");
		
		return result;
	}
	
	
	public static String camelToSentence(String camelcase) {
		String[] words = StringUtils.splitByCharacterTypeCamelCase(camelcase);
		String result = "";
		for (String word : words) {
			result = (result + " " + word.toLowerCase());
		}
		return result.trim();
	}


	public static ArrayList<String> generateTripleRules() {
		// generates all combinations of digit operation digit == number
		System.out.println("Generate all combinations of (digit operation digit == number).");
		ArrayList<String> result = new ArrayList<String>();
		String[] digits = { "hourTens", "hourOnes", "minuteTens", "minuteOnes" };
		String[] englishDigits = { "Tens place of Hours", "Ones place of Hours", "Tens place of Minutes", "Ones place of Minutes" };
		String[] numbers = { "hours", "minutes" };
		String[] operations = { "Plus", "Minus", "reverseMinus", "Times", "Divided by", "reverseDivided by" };
		String[] realOperations = { "+", "-", "-", "*", "/", "/" };
		int count = 0;
		String realDigit1 = "";
		String realDigit2 = "";
		String realEnglishDigit1 = "";
		String realEnglishDigit2 = "";
		for (int i=0; i<digits.length; i++) {
			String digit1 = digits[i];
			String englishDigit1 = englishDigits[i];
			for (int j=0; j<digits.length; j++) {
				String digit2 = digits[j];
				String englishDigit2 = englishDigits[j];
				//System.out.println("Working on combinations of : " + digit1 + " " + Arrays.toString(operations) + " " + digit2);
				if (!digit2.equals(digit1)) {
					for (int k=0; k<operations.length; k++) {
						String operation = operations[k];
						String realOperation = realOperations[k];
						for (String number : numbers) {
							//System.out.println("Working on combinations of : " + digit1 + " " + realOperation + " " + digit2 + " = " + number);
							if (containsCaseInsensitive(digit1, number) || containsCaseInsensitive(digit1, number)) {
								totalCount = totalCount + 1;
								//System.out.println(totalCount + ". Nonsensical: " + digit1 + " and/or " + digit2 + " are part of " + number);
							} else {
								if (operation.startsWith("reverse")) {
									operation = operation.replace("reverse", "");
									realDigit2 = digit1;
									realDigit1 = digit2;
									realEnglishDigit2 = englishDigit1;
									realEnglishDigit1 = englishDigit2;
								} else {
									realDigit1 = digit1;
									realDigit2 = digit2;
									realEnglishDigit1 = englishDigit1;
									realEnglishDigit2 = englishDigit2;
								}
								totalCount = totalCount + 1;
								count = count + 1;
								String condition = "(" + realDigit1 + " " + realOperation + " " + realDigit2 + " == " + number + ")";
								String description = realEnglishDigit1 + " " + operation + " " + realEnglishDigit2 + " is equal to " + number;
								//System.out.println(count + ". expression is: " + expression);
								//System.out.println(count + ". description is: " + description);
								//String rule = writeRule(expression, description);
								System.out.println(count + ". rule: " + condition + " " + description);
								result.add(condition + " " + description);
							}
						}
					}				
				} else {
					//System.out.println(totalCount + ". Boring: " + digit1 + " = " + digit2);
				}
			}
		}
		return result;
	}


	public static boolean containsCaseInsensitive(String bigString, String smallString) {
		//System.out.println("bigString=" + bigString + " smallString=" + smallString);
		/// Since we have to do this everywhere in this application, we will accept this small ugliness here.
		return bigString.toUpperCase().contains(smallString.replaceAll("s$", "").toUpperCase());
	}

}
