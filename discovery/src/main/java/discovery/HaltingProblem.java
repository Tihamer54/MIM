package discovery;

public class HaltingProblem {
	
	public static void main(String[] args) {
		System.out.println("Start Halting Problem.");
		for (int i=0; i<20; i++) {
			int randomInteger = (int)( 9.0 * Math.random());
			System.out.println("randomIntegerrandomInteger=" + randomInteger);
			int threeDigitNumber = (randomInteger * 100) + (randomInteger * 10) + randomInteger;
			System.out.println("threeDigitNumber=" + threeDigitNumber);
			int answer = (threeDigitNumber/37)/3;
			System.out.println("answer=" + answer);
		}
	}
	
	/**
	 * Can SonarCube detect simple versions of the Halting Problem?  I wouldn't think so, but I'm checking to see what it does.
	 * These functions should never be called because they will never halt.
	 */
	public static void checkHalt() {
		
		// No way will SonarQube catch this first one
		int randomInteger = (int)( 9.0 * Math.random());
		int threeDigitNumber = (randomInteger * 100) + (randomInteger * 10) + randomInteger;
		int answer = (threeDigitNumber/37)/3;
		while (randomInteger == answer) {
			System.out.println("randomInteger=" + randomInteger);
		}
		
		int i = 8;
		while (i==8) {
			System.out.println(i);
		}
		
		while (true) {
			System.out.println(i);
		}
		
	}

}
