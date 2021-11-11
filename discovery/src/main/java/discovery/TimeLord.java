package discovery;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

//import com.sun.tools.classfile.Annotation.element_value;

/**
 * Finds all the interesting numbers that appear on a 12-hour digital clock.
 * First method uses manually-written rules.
 * Second method uses rule generator for two or three classes of rules.
 * How to write a third (meta) method that generates rule generators for many classes of rules?
 * How to write a fourth method that is an infinite number of meta generators.
 * 
 * TODO: One of the properties of "interesting" is that it's relatively rare.
 * After first exposure, even numbers (and odd ones, unless they are *really*) are no longer interesting.
 * This means that if a rule produces more than 10% hits, it's probably not interesting.
 * If it has only one it, it *might* be interesting, as long as it's not too random.
 * 
 * TODO: Web Scraping 
 * TODO: Look for interesting sequences in https://oeis.org/
 * TODO: Look for interesting dates in history, celebrity birthdays, personal data of the whoever is running the program.
 * 
 * @author ttoth-fejel
 *
 */
public class TimeLord {

	
	public static void main(String[] args) {
		System.out.println("Starting TimeLord main.");
		long startTime = System.currentTimeMillis();
		int countDiscoveries = 0;
		int countBoring = 0;
		String ruleResults = "";
		ArrayList<IfThenRule> initialClockRulesMap = new ArrayList<IfThenRule>();

		ArrayList<IfThenRule> autoRulesFirstGeneration = new ArrayList<IfThenRule>();
		ArrayList<IfThenRule> autoRulesSecondGeneration = new ArrayList<IfThenRule>();
		
		// Test two custom functions
		System.out.println("Is 144 fib?" + VariableProviderClock.isFibonacci(144));
		System.out.println("2^1,048,576=" + Math.pow(2.0, 1048576.0));

		//		BigDecimal two = new BigDecimal("2.0");
		//		int smallExponent = 106301;
		//		BigDecimal result = two.pow(smallExponent, new MathContext(30, RoundingMode.DOWN));  // The parameter n must be in the range 0 through 999999999 says the docs. Baloney.
		//		System.out.println("precision=" + result.precision() + " \nBigDecimal 2^" + smallExponent + "=" + result);  // Note: that number has 32,000 characters.
		//		// The biggest number I can calculate is BigDecimal 2^106301.  Any larger number, and java quietly dies (without throwing an exception).
		//		// Unless I give it a MathContext
		//		System.out.println("Integer.MAX_VALUE=" + Integer.MAX_VALUE + " Long.MAX_VALUE=" +  Long.MAX_VALUE); 
		//		// Where Integer.MAX_VALUE= 2147483647 Long.MAX_VALUE=9223372036854775807
		//		int exponent = 1048576;
		//		System.out.println("BigDecimal 2^" + exponent + "=" + two.pow(exponent, new MathContext(30, RoundingMode.DOWN))); // I would expect something about 10^300,000.
		//		BigDecimal six = new BigDecimal("6.0");

		autoRulesFirstGeneration = generateDoubleRules();
		for (IfThenRule rule : autoRulesFirstGeneration) {
			//runRulesOnClock();
		}
		
		System.out.println("autoRulesFirstGeneration = " + autoRulesFirstGeneration.toString());
		//		autoRules.addAll(generateTripleRules()); 
		//		System.out.println("all autoRules = " + autoRules.toString());
		//runClock(false);
		//             0        1         2         3         4         5
		//             1234567890123456789012345678901234567890123456789012
		//String rule = "ifThen(hours == minutes, \"Hours is same as minutes. \"); ";
		//rule = "ifThen";
		//writeRandomRule(rule);
		
	}

	/**
	 * For testing how long it would take for a random process to generate a rule.
	 * Doug Lenat got around this problem by using a rachet effect; i.e. keeping progress heading towards success.
	 * For example, if each piece of the rule was caught and stored when it came up with something useful, 
	 * then a laptop could generate a 52 character rule in only three hours instead of 2.73x10^116 years.  
	 * BTW, the word "if" takes between a hundredth and six hundredths of a second to generate randomly.
	 * 
	 * @param targetRule
	 */
	public static void writeRandomRule(String targetRule) {
		int iterations = 0;
		long startTime = System.currentTimeMillis();
		String characters = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z a b c d e f g h i j k l m n o p q r s t u v w x y z 0 1 2 3 4 5 6 7 8 9 0 = \" . + ( )";
		String[] characterArray = characters.split(" ");
		ArrayList<String> characterList = new ArrayList<String>(Arrays.asList(characterArray)); 
		characterList.add(" ");
		int characterLength = characterList.size();
		System.out.println("characterLength=" + characterLength);
		Random r = new Random();
		String letter1 = characterList.get(r.nextInt(characterLength));

		if (targetRule.length() > 20) {
			double yearsRequired = Math.pow(70, characterLength)/10000000.0/60/24/365;
			System.out.println("Failure.  You don't have time for such foolishness. The string you are looking for (" + targetRule + ") will require about " +
					yearsRequired + " years.");  // 60 million wpm / 6 letters per word = 10 million
			System.out.println("Failure. If you had Avogadro's number of laptops then it would take only " + (yearsRequired/(6.02 * Math.pow(10, 23))));
			return;
		}
		String actualRandomRule = "";
		//ArrayList<String> actualRuleList = new ArrayList();
		// 70 characters. Chances for just six characters in the first word (ifThen) to be correct is one in 70*70*70*70*70*70 = 117,649,000,000                                                                                // 100,000,000,000L  
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
		Utils.measureElapsedTime(startTime);
	}
	


	public static void runClock(boolean autoMode) {
		// Go through all the minutes and hours of the day from midnight to noon.
		for (int hour=1; hour<=12; hour++) {
			DecimalFormat decimalFormat = new DecimalFormat("00");
			String hourString = decimalFormat.format(hour);
			int hourTens = Integer.parseInt(hourString.substring(0, 1));
			int hourOnes = Integer.parseInt(hourString.substring(1, 2));

			//System.out.println(hourString);
			for (int minute=0; minute<60; minute++) {
				String minuteString = decimalFormat.format(minute);
				int minuteTens = Integer.parseInt(minuteString.substring(0, 1));
				int minuteOnes = Integer.parseInt(minuteString.substring(1, 2));
				int timeAsSingleNumber = Integer.parseInt("" + hourTens + hourOnes + minuteTens + minuteOnes);
				String timeAsSingleString = "" + hourTens + hourOnes + minuteTens + minuteOnes;
				String discovery = "";
				if (autoMode) {
					//discovery = discover(autoRulesFirstGeneration, timeAsSingleString, timeAsSingleNumber, hour, minute, hourTens, hourOnes, minuteTens, minuteOnes);
				} else {
					//discovery = discover(timeAsSingleString, timeAsSingleNumber, hour, minute, hourTens, hourOnes, minuteTens, minuteOnes);
				}
				System.out.println(hourString + ":" + minuteString + " " + discovery);
			}
		}
		//System.out.println("Made " + countDiscoveries + " discoveries out of 720 hour:minutes. And there were " + countBoring + " boring times.");
	}





	

	/**
	 * When both have the same property ie. prime, even, same value, etc.
	 * @return
	 */
	public static ArrayList<String> generateBothNumberRules() {
		ArrayList<String> result = new ArrayList<String>();

		return result;
	}

	public static ArrayList<String> generateSingleNumberRules() {
		ArrayList<String> result = new ArrayList<String>();

		return result;
	}

	
	/*
	 * Generates all combinations that take the time as two numbers.
	 * Possiblities have the following forms:
	 *  "hours OP constant(1-12) == minutes"  where OP can be plus, minus, times, or divided by
	 *  "minutes OP constant == hours"
	 *  "Math.FN(minutes) == hours"  where FN can be any java.Math function that takes an integer as input and returns an integer.
	 *  "Math.FN(hours) == minutes"
	 *   "VariableProviderClock.FN(minutes) == hours"  where FN can be any java.Math function that takes an integer as input and returns an integer.
	 *   "VariableProviderClock.FN(hours) == minutes"
	 *   "Math.FN(minutes) == hours"  where FN can be any java.Math function that takes an integer as input and returns an integer.
	 *   "Math.FN(hours) == minutes"
	 *   "VariableProviderClock.FN(minutes) == hours"  where FN can be any java.Math function that takes an integer as input and returns an integer.
	 *   "VariableProviderClock.FN(hours) == minutes"
	 *   
	 */
	public static ArrayList<IfThenRule> generateDoubleRules() {
		// 
		System.out.println("Generate all combinations of (hoursOrMinutes operation [1-10] == minutesOrHours).");
		ArrayList<IfThenRule> result = new ArrayList<IfThenRule>();
		String[] numbers = { "hours", "minutes" };
		String[] arithmeticOperations = { "plus", "minus", "times", "divided by" };
		String[] realOperations = { "+", "-", "*", "/" };
		int count = 0;
		int totalCount = 0;
		String realNumber1 = "";
		String realNumber2 = "";
		String realEnglishDigit1 = "";
		String realEnglishDigit2 = "";
		for (int constant=0; constant<=12; constant++) {
			for (int i=0; i<numbers.length; i++) {
				for (int m=0; m<2; m++) {  // to reverse the order
					String number1, number2;
					if (m==0) {
						number1 = numbers[i]; 
						number2 = numbers[(i+1)%2];
					} else {
						number1 = numbers[(i+1)%2];
						number2 = numbers[i];
					}
					//System.out.println("Working on combinations of : " + number1 + " " + Arrays.toString(operations) + " " + constant +" = " + number2);
					for (int k=0; k<arithmeticOperations.length; k++) {
						String operation = arithmeticOperations[k];
						String realOperation = realOperations[k];
						if (operation.startsWith("reverse")) {
							operation = operation.replace("reverse", "");
							realNumber2 = number1;
							realNumber1 = number2;
						} else {
							realNumber1 = number1;
							realNumber2 = number2;
						}
						totalCount = totalCount + 1;
						count = count + 1;
						String condition = "(" + realNumber1 + " " + realOperation + " " + constant + " == " + realNumber2 + ")";
						String description = realNumber1 + " " + operation + " " + constant + " is equal to " + realNumber2;
						//System.out.println(count + ". expression is: " + expression);
						//System.out.println(count + ". description is: " + description);
						//String rule = writeRule(expression, description);
						System.out.println(count + ". rule: " + condition + " \"" + description + "\"");
						result.add(new IfThenRule(condition, description));
					}
				}
			}				
		}
		String[] powerOperations = { "squared", "cubed", "raised to the fourth power", "raised to the fifth power" };
		String[] powerExponents = { "2", "3", "4", "5" };
		for (int constant=0; constant<=12; constant++) {
			for (int i=0; i<numbers.length; i++) {  // hours and minutes, and then minutes and hours
				for (int m=0; m<2; m++) {  // to reverse the order
					String number1, number2;
					if (m==0) {
						number1 = numbers[i]; 
						number2 = numbers[(i+1)%2];
					} else {
						number1 = numbers[(i+1)%2];
						number2 = numbers[i];
					}
					System.out.println("Working on combinations of : " + number1 + " " + Arrays.toString(powerOperations)  +" = " + number2);
					for (int k=0; k<powerOperations.length; k++) {
						String operation = powerOperations[k];
						totalCount = totalCount + 1;
						count = count + 1;

						String condition = "(pow(" + number1 + ", " + powerExponents[k]+ ") == " + number2 + ")";
						String description = number1 + " " + operation + " is equal to " + number2;
						//String rule = writeRule(expression, description);
						System.out.println(count + ". rule: " + condition + ", \"" + description + "\"");
						result.add(new IfThenRule(condition, description));
					}
				}
			}				
		}
		//		ArrayList<Method> intIntOperationMethods = JavaReflectionExplorer.getOneInOneOutMethods("java.lang.Math", "int-int");
		//		ArrayList<Method> doubleDoubleOperationMethods = JavaReflectionExplorer.getOneInOneOutMethods("java.lang.Math", "double-double");
		ArrayList<Method> booleanPropertyMethods = JavaReflectionExplorer.getOneInOneOutMethods("discovery.TimeLord", "number-boolean");
		return result;
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
		int totalCount = 0;
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
					totalCount = totalCount + 1;
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

	public static String writeRule(String condition, String description) {
		return "if " + condition + " { ruleResults = ruleResults + \"" + description + "\"; countDiscoveries++; }";
	}



	/**
	 * Not including 1 and 0; otherwise there are way too many boring results. .
	 */
	private static ArrayList<Integer> factors(int number) {
		ArrayList<Integer> listFactors = new ArrayList<Integer>();
		for(int i=2; i<number; i++) {
			if (number%i == 0) {
				listFactors.add(i);
			}
		}
		return listFactors;
	}


	static String numberToLetterCipher(String x) {
		return numberToLetterCipher(Integer.parseInt(x));
	}

	static String numberToLetterCipher(int x) {
		if (x==0) { return ""; }
		//int asciiVal = x + 64;
		return new Character((char) (x + 64)).toString();
	}

	// can only use first 9 letters a b c d e f g h i; four letters must start with a; 
	// three letters must start a-i, middle letter must be a-f, last can be a-i;
	static String[] words = { "abbe", "add", "bad", "bed", "bib", "bid", "cab", "cad", "dab", "dig", "fab", "fad", "gab", "gag", "gig" };

	static String numbersToLettersCipher(int first, int second, int third, int fourth) {
		return numberToLetterCipher(first) + numberToLetterCipher(second) + numberToLetterCipher(third) + numberToLetterCipher(fourth) ;
	}
	static String numbersToLettersCipher(String first, String second, String third, String fourth) {
		return numberToLetterCipher(first) + numberToLetterCipher(second) + numberToLetterCipher(third) + numberToLetterCipher(fourth) ;
	}

	static boolean contains(String[] array, String member) {
		for (String thing : array) {
			if (thing.equalsIgnoreCase(member)) { return true; }
		}
		return false;
	}

}
