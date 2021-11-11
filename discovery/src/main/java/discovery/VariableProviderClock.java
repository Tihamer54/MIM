package discovery;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class VariableProviderClock {

	public static DecimalFormat decimalFormat = new DecimalFormat("0000");

	public String timeAsSingleStringVar;
	public double timeAsSingleNumberVar;

	public double hoursVar;
	public double minutesVar;

	public double hourTensVar;
	public double hourOnesVar;
	public double minuteTensVar;
	public double minuteOnesVar;

	public String timeAsSingleString() {return timeAsSingleStringVar;}
	public double timeAsSingleNumber() {return timeAsSingleNumberVar;}

	public double hours() {return hoursVar;}
	public double minutes() {return minutesVar;}

	public double hourTens() {return hourTensVar;}
	public double hourOnes() {return hourOnesVar;}
	public double minuteTens() {return minuteTensVar;}
	public double minuteOnes() {return minuteOnesVar;}


	public static boolean isPalidrome(String string) {
		return string.equalsIgnoreCase(StringUtils.reverse(string));
	}


	/*
	 * The traditional, recursive way. But not good for Java, given how slow and expensive recursion is.
	 */
	//	public static boolean isFibonacci(long x) { 
	//		long n1=0, n2=1, n3;
	//		int count = 93;    // starts overflowing if we go higher than that. TODO:BigNumbers?
	//		//System.out.print("FIB(1): " + n1);//printing 0   
	//		if (x==n1) { return true; }
	//		//System.out.print(" FIB(2): " + n2);//printing 1    
	//		if (x==n2) { return true; }
	//		//loop starts from 2 because 0 and 1 are already checked 
	//		for(int i=2;i<count;++i)    {    
	//			n3 = n1 + n2;    
	//			//System.out.print(" FIB(" + i + "):" + n3);  
	//			if (x==n3) { return true; }
	//			if (x<n3) {return false; }
	//			n1=n2;    
	//			n2=n3;    
	//		} 
	//		return false;
	//	}

	/**
	 * https://oeis.org/A000045
	 * 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597,
	 * @param n
	 * @return
	 */
	public static boolean isFibonacci(double n) {
		return isFibonacci((long)n);
	}

	public static boolean isFibonacci(long n) {
		long previousPreviousNumber, previousNumber = 0, currentNumber = 1;
		for (int i=1; i<1300 ; i++) {
			previousPreviousNumber = previousNumber;
			previousNumber = currentNumber;
			currentNumber = previousPreviousNumber + previousNumber;
			//System.out.println("Current Number=" + currentNumber);
			if (n == currentNumber) { return true; } 
			else if (n < currentNumber) { return false; } 
		}
		return false;
	}

	/*
	 * https://oeis.org/A000040
	 *  2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 
	 *  127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271
	 */
	public static boolean isPrime(double n) {
		if (n == 0.0 || n == 1.0) { return false; }
		for (int i=2; i <= (n/2); ++i) {
			if (n%i == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Many numbers that can be expressed as Math.pow(2.0, i) - 1.0 are prime. 
	 * 
	 * For example: 3, 7, 31, 127, 8191..
	 * where i= 2, 3, 5, 7, 13, 17, 19, 31, 61, 89, 107, 127, 521, 607, 1279, 2203
	 * @param x
	 * @return
	 */
	public static boolean isMersennePrime(double x) {
		if (isPrime(x)) {
			BigInteger trial = BigInteger.ONE;
			BigInteger xInt = new BigInteger(("" + (int)x));
			BigInteger two = new BigInteger("2");
			System.out.println("X=" + x + " is prime;");
			for (int i=0; i <= 256; i++) {
				trial = two.pow(i).subtract(BigInteger.ONE);
				if (trial.equals(xInt)) {
					return true;
				} else if (trial.compareTo(xInt)>0) {  // save time; it missed. compareTo returns:-1, 0 or 1 as this BigInteger is numerically less than, equalto, or greater than val.
					return false;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * e.g. Many primes occur in pairs on either side of an even integer: e.g. 5/7, 11/13
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean arePrimePairs(double x, double y) {
		boolean flag = false;
		if ((x == y+2.0) || (x == y-2.0)) { 
			if (isPrime(x) && (isPrime(y))) {
				return true;
			}
		} 
		return false;
	}

	/**
	 * https://oeis.org/A000290
	 * 0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100, 121, 144, 169, 196, 225, 256, 289, 324, 361, 400, 441, 484, 529, 576, 
	 * 625, 676, 729, 784, 841, 900, 961, 1024, 1089, 1156, 1225, 1296, 1369,
	 */
	public static boolean isPerfectSquare(int n) { 
		return isPerfectSquare((double)n); 
	} 
	public static boolean isPerfectSquare(double n) { 
		double square = Math.sqrt(n); 
		return ((square - Math.floor(square)) == 0); 
	} 

	/*
	 * https://oeis.org/A000578
	 * 0, 1, 8, 27, 64, 125, 216, 343, 512, 729, 1000, 1331,
	 */
	public static boolean isPerfectCube(int n) { 
		return isPerfectCube((double)n);
	} 
	public static boolean isPerfectCube(double w) { 
		double cube = Math.cbrt(w); 
		return ((cube - Math.floor(cube)) == 0); 
	} 

	// pi: 3.1415926
	//  golden ratio of relatively 1.618

	/*
	 * A lucky number is the sum of the cubes of it's digits
	 * 153 370 371 407 
	 */
	public static boolean isSumOfItsDigitsCubes(double x) {
		if (x == 0.0 || x == 1.0) { return false; }
		String numberString = decimalFormat.format(x);
		int thousands = Integer.parseInt(numberString.substring(0,1));
		int hundreds = Integer.parseInt(numberString.substring(1,2));
		int tens = Integer.parseInt(numberString.substring(2,3));
		int ones = Integer.parseInt(numberString.substring(3,4));
		if (x == (double)(Math.pow(thousands, 3) + Math.pow(hundreds, 3) + Math.pow(tens, 3) + Math.pow(ones, 3))) {
			return true;
		}
		return false;
	}

	public static boolean isSumOfItsDigitsSquares(double x) {
		String numberString = decimalFormat.format(x);
		int thousands = Integer.parseInt(numberString.substring(0,1));
		int hundreds = Integer.parseInt(numberString.substring(1,2));
		int tens = Integer.parseInt(numberString.substring(2,3));
		int ones = Integer.parseInt(numberString.substring(3,4));
		if (x == (double)(Math.pow(thousands, 2) + Math.pow(hundreds, 2) + Math.pow(tens, 2) + Math.pow(ones, 2))) {
			return true;
		}
		return false;
	}

	
	/**
	 * Bell number: (x^0 + x^2 + x^3 is prime).
	 * 1,1, 5, 15, 52, 203, 877, 4140
	 * Not sure if this is correct; takes forever at any rate.
	 * @param x
	 * @return
	 */
//	public static boolean isBellsNumber(double x) {
//		if (isPrime(Math.pow(x, 0) + Math.pow(x, 2) + Math.pow(x, 3))) {
//			System.out.println("x (" + x + ") is a Bells number (Math.pow(x, 0) + Math.pow(x, 2) + Math.pow(x, 3)): " + (Math.pow(x, 0) + Math.pow(x, 2) + Math.pow(x, 3)));
//			return true;
//		}
//		return false;
//	}

	public static boolean isSquarePyramidNumber(double n) {
		return isSquarePyramidNumber((int)n);
	}

	// TODO: do this for the general case
	// Decided that rounding was for the birds.
	public static boolean isPi(double number) {
		long n = (long)number;
		if (n == 314) { return true; }
		else if (n == 3141) { return true; }
		else if (n == 31415) { return true; }
		else if (n == 314159) { return true; }
		else if (n == 3141592) { return true; }
		else if (n == 31415926) { return true; }
		else if (n == 314159265) { return true; }
		else if (n == 3141592653L) { return true; }
		return false;
	}
	
	
	//TODO Generalize
	//Math.E = 2.7182818284590452353602874
	public static boolean isEuler(double n) {
		if (n == 271) { return true; }
		else if (n == 2718) { return true; }
		else if (n == 27182) { return true; }
		else if (n == 271828) { return true; }
		else if (n == 2718281) { return true; }
		else if (n == 27182818) { return true; }
		else if (n == 271828182) { return true; }
		return false;
	}


	// TODO: do this for the general case
	public static boolean isGoldenRatio(double number) {
		// golden ratio (1 + Square root of√5)/2 = 1.6180339887
		long n = (long)number;
		if (n == 161) { return true; }
		else if (n == 1618) { return true; }
		else if (n == 16180) { return true; }
		else if (n == 161803) { return true; }
		else if (n == 1618033) { return true; }
		else if (n == 16180339) { return true; }
		else if (n == 161803398) { return true; }
		else if (n == 1618033988) { return true; }
		else if (n == 16180339887L) { return true; }
		return false;
	}

	
	/*
	 * Corresponds to a configuration of points which form a square pyramid, kindof like a cone, except square.
	 * The top layer has one point; the second has four, and the third has nine, fourth has 16, etc.
	 * 0, 1, 5, 14, 30, 55, 91, 140, 204, 285, 385, 506, 650, 819, 1015, 1240, 1496, 
	 * https://oeis.org/A000330
	 */
	public static boolean isSquarePyramidNumber(int n) {
		for (int i=1; i<1300; i++) {
			int squarePyramidNumber = (i * (i + 1) * ((2*i) + 1))/6;
			if (squarePyramidNumber == n) { return true; }
			if (squarePyramidNumber > n) { return false; }
		}
		return false;
	}

	public static boolean isSquareRootOf(double x, double y) {
		return (Math.sqrt(x) == y);
	}

	public static boolean isCubeRootOf(double x, double y) {
		return (Math.cbrt(x) == y);
	}


	public static boolean isRelativelyPrimeTo(double x, double y) {
		return (null == intersection(factors(x), factors(y)));
	}

	public static boolean hasSameFactorsAs(double x, double y) {
		ArrayList<Double> xFactors = factors(x);
		ArrayList<Double> yFactors = factors(y);
		return (union(factors(x), factors(y)) == intersection(factors(x), factors(y)));
	}

	public static boolean isFactorOf(double smallNumber, double bigNUmber) {
		return factors(smallNumber).contains(bigNUmber);
	}

	public static boolean isFactorOf(int smallNumber, int bigNUmber) {
		return factors(smallNumber).contains(bigNUmber);
	}

	public static ArrayList<Double> factors(double number) {
		ArrayList<Double> listFactors = new ArrayList<Double>();
		for(int i=2; i<number; i++) {
			if (number%i == 0) {
				listFactors.add((double)i);
			}
		}
		return listFactors;
	}


	/**
	 * Not including 1 and 0; otherwise there are way too many boring results. .
	 */
	public static ArrayList<Integer> factors(int number) {
		ArrayList<Integer> listFactors = new ArrayList<Integer>();
		for(int i=2; i<number; i++) {
			if (number%i == 0) {
				listFactors.add(i);
			}
		}
		return listFactors;
	}


	public boolean contains(String[] array, String member) {
		for (String thing : array) {
			if (thing.equalsIgnoreCase(member)) { return true; }
		}
		return false;
	}
	
	/**
	 * Narcissistic number is the sum of its own digits each raised to the power of the number of digits.
	 * e.g: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 153, 370, 371, 407, 1634,
	 * 1:53 --> 1^3 + 5^3 + 3^3 - 1 + 125 + 27 = 153
	 */
	
	/**
	 * Dudeney number is a number which is equal to the perfect cube of another natural number such that 
	 * the digit sum of the first natural number is equal to the second
	 * For example, 512 is a Dudeney number because it is the cube of 8, which is the sum of its decimal digits (5+1+2).
	 * 	19,683 = 27^3 ; 1 + 9 + 6 + 8 + 3 = 27
	 * 		1 =  1 x  1 x  1   ;   1 = 1
  			512 =  8 x  8 x  8   ;   8 = 5 + 1 + 2
 			4,913 = 17 x 17 x 17   ;  17 = 4 + 9 + 1 + 3
 			5,832 = 18 x 18 x 18   ;  18 = 5 + 8 + 3 + 2
			17,576 = 26 x 26 x 26   ;  26 = 1 + 7 + 5 + 7 + 6
			19,683 = 27 x 27 x 27   ;  27 = 1 + 9 + 6 + 8 + 3
	 */
	
	/**
	 * Sum-product numbers equal to the product of the sum of its digits and the product of its digits. 
	 * Must be divisible by its digits as well as the sum of its digits.
	 * 1, 135, 144
	 * 135 = 1 * 3 * 5 * (1+ 3 + 5) = 15 * 9 = 135
	 */
	
	/**
	 * Taxicab number: (also Hardy–Ramanujan number): the smallest integer that can be expressed as a sum of two positive integer cubes in n distinct ways.
	 * The most famous taxicab number is 1729 = Ta(2) = 1^3 + 12^3 = 9^3 + 10^3.
	 * Inescapable conclusion:  Ramanujan's brain was *not* a Turing Equivalent machine. Probably conclusion: None of us are Turing Equivalent.
	 * Either that, or there are gods living amongst us. 
	 * Currently, only six are known. Here are the first 4:
	 * {\displaystyle {\begin{aligned}\operatorname {Ta} (1)=2&=1^{3}+1^{3}\end{aligned}}}
		{\displaystyle {\begin{aligned}\operatorname {Ta} (2)=1729&=1^{3}+12^{3}\\&=9^{3}+10^{3}\end{aligned}}}{\displaystyle {\begin{aligned}\operatorname {Ta} (2)=1729&=1^{3}+12^{3}\\&=9^{3}+10^{3}\end{aligned}}}
		{\displaystyle {\begin{aligned}\operatorname {Ta} (3)=87539319&=167^{3}+436^{3}\\&=228^{3}+423^{3}\\&=255^{3}+414^{3}\end{aligned}}}{\displaystyle {\begin{aligned}\operatorname {Ta} (3)=87539319&=167^{3}+436^{3}\\&=228^{3}+423^{3}\\&=255^{3}+414^{3}\end{aligned}}}
		{\displaystyle {\begin{aligned}\operatorname {Ta} (4)=6963472309248&=2421^{3}+19083^{3}\\&=5436^{3}+18948^{3}\\&=10200^{3}+18072^{3}\\&=13322^{3}+16630^{3}\end{aligned}}}{\displaystyle {\begin{aligned}\operatorname {Ta} (4)=6963472309248&=2421^{3}+19083^{3}\\&=5436^{3}+18948^{3}\\&=10200^{3}+18072^{3}\\&=13322^{3}+16630^{3}\end{aligned}}}
	*/
	
	/**
	 * factorion is equal to the sum of factorials of its digits. There are exactly four such numbers:
		1	=	1!	
		2	=	2!	
		145	=	1! + 4! + 5  =  1 + 24 + 120
		40585	=	4! + 0! + 5! +8! + 5!
	 */
	
	/**
	 * Kaprekar number - when squared, can be split into two numbers, which when added together, equal the original number.
	 * Eg: 45^2 = 2025 --->  20 + 25 = 45.
	 * 		9^2 = 81 --> 8 + 1 = 9	 
	 * 		7777^2 = 60481729 --> 6048 + 1729 = 7777
	 * https://www.w3resource.com/java-exercises/numbers/java-number-exercise-4.php
	 * 
	 * */
	 public static void listKaprekar(String[] args){
	        int ctr = 0;
	        int base = 10;
	        for (long n = 1; n <= 1000; n++){
	            String sqr_Str = Long.toString(n * n, base);
	            for(int j = 0; j < sqr_Str.length() / 2 + 1; j++){
	                String[] parts = split_num(sqr_Str, j);
	                long first_Num = Long.parseLong(parts[0], base);
	                long sec_Num = Long.parseLong(parts[1], base);
	                if (sec_Num == 0) break;
	                if (first_Num + sec_Num == n){
	                    System.out.println(Long.toString(n, base) +
	                            "\t" + sqr_Str + "\t  " + parts[0] + " + " + parts[1]);
	                    ctr++;
	                    break;
	                }
	            }
	        }
	        System.out.println(ctr + " Kaprekar numbers.");
	    }
	     private static String[] split_num(String str, int idx){
	        String[] ans1 = new String[2];
	        ans1[0] = str.substring(0, idx);
	        if(ans1[0].equals("")) ans1[0] = "0"; 
	        ans1[1] = str.substring(idx);
	        return ans1;
	    }	


	/**
	 * Happy Numbers: Take any number and sum the squares of its digits. Take the sum and do the same. 
	 * Repeat the process until you get a number with a single digit. 
	 * If this number is 1 then the original number is dubbed a “happy” number.
	 * For example, let’s take the number 7.
			The square of its only digit, 7, is 49.
			The squares of 4 and 9 are 16 and 81, and their sum is 97.
			The squares of 9 and 7 are 81 and 49, which equal 130.
			The squares of 1 and 3 and 0 are 1, 9, and 0 and their sum is 10.
			The square of 1 is 1 and there is no point in continuing because the square of 1 is 1 and we have an end to the process.
			So we have found that 7 goes to 1.
			It is easy to show that all other numbers—those that do not go to 1 (and obviously remain there forever)—will end up at 4. 
			But, since the square of 4 is 16 they will then forever cycle through the following numbers: 4, 16, 37, 58, 89, 145, 42, 20, and back to 4, etc.
	 * @param x
	 * @return
	 */
	public boolean isHappyNumber(String x) {
		// TODO
		return false;
	}

	/**
	 * One of the oldest ciphers is 1-> A; 2->B; 3->C; 4->D; .... 26->Z;
	 * @param x
	 * @return
	 */
	public String numberToLetterCipher(String x) {
		return numberToLetterCipher(Integer.parseInt(x));
	}

	public String numberToLetterCipher(int x) {
		if (x==0) { return ""; }
		//int asciiVal = x + 64;
		return new Character((char) (x + 64)).toString();
	}

	// for single-digit, ciphers can only use first 9 letters a b c d e f g h i; 
	// for a 12:00 clock, the four letters must start with a; 
	// three letters must start a-i, middle letter must be a-f, last can be a-i;
	// If we use two digits, then it's easy to get to 26=Z  // TODO get the whole list of 2-letter words.
	static String[] wordsArray = { "am", "an", "as", "at", "be", "by", "do", "go", "hi", "if", "in", "is", "me", "my", "of", "on", "so", "to", "us", "we",
						"abbe", "add", "bad", "bed", "bib", "bid", "cab", "cad", 
						"dab", "dig", "fab", "fad", "gab", "gag", "gig" };
	//static ArrayList<String> words = (ArrayList<String>)Arrays.asList(wordsArray);
	//List<String> wordsLower = Arrays.asList(wordsArray); 

	public String numbersToLettersCipher(int first, int second, int third, int fourth) {
		return numberToLetterCipher(first) + numberToLetterCipher(second) + numberToLetterCipher(third) + numberToLetterCipher(fourth) ;
	}
	public String numbersToLettersCipher(double first, double second, double third, double fourth) {
		return numberToLetterCipher((int)first) + numberToLetterCipher((int)second) + numberToLetterCipher((int)third) + numberToLetterCipher((int)fourth) ;
	}
	public String numbersToLettersCipher(String first, String second, String third, String fourth) {
		return numberToLetterCipher(first) + numberToLetterCipher(second) + numberToLetterCipher(third) + numberToLetterCipher(fourth) ;
	}
	
	public boolean isNumbersToLettersCipher(double first, double second, double third, double fourth) {
		String word = numbersToLettersCipher((int)first, (int)second, (int)third, (int)fourth).toLowerCase();
		if (caseInsensitveContains(word, wordsArray)) { return true; }
		return false;
	}
	
	public static boolean caseInsensitveContains(String text, String[] textsList) {
		String lowerText = text.trim().toLowerCase();
		for (String item : textsList) {
			if (lowerText.equals(item.trim().toLowerCase())) { 
				return true;
			}
		}
		return false;
	}

	
	public String asciiToLetter(int x) {
		if (x <= 65 && x >= 100) { return ""; } // 65=A ... 91=Z; 92=a ... 118=z  TODO: What about punctuation? 
		return new Character((char) (x)).toString();
	}

	public static <T> ArrayList<T> union(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();
		set.addAll(list1);
		set.addAll(list2);
		return new ArrayList<T>(set);
	}

	public static <T> ArrayList<T> intersection(List<T> list1, List<T> list2) {
		ArrayList<T> list = new ArrayList<T>();
		for (T t : list1) {
			if(list2.contains(t)) {
				list.add(t);
			}
		}
		return list;
	}

}
