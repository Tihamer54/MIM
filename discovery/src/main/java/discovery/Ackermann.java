package discovery;

import java.math.BigDecimal;

public class Ackermann {

	// makes it to: 11. ack=16381
	public static void main(String[] args) {
		//run(1000);
		for (int j = 0; j < 1000; j++) {
			System.out.println(j + ", " + rn1(j).length());
		}
	}

	
	public static void run(int x) {
		for (int i=0; i<x; i++) {
			System.out.print(" " + i);
		}
	}
	
	public static String rn1(int x) {
		String result = "";
		for (int i=0; i<x; i++) {
			result = result + i + " ";
		}
		return result;
	}
	
	public static String rn2(int x) {
		String result = "";
		for (int i=0; i<x; i++) {
			result = result + i + " ";
		}
		return result;
	}
	


//	public static void run(int x) {
//		//System.out.print(" final: " + ackermann(new BigDecimal(3), new BigDecimal(6)));
//		System.out.print(" i=" + ack(3, 4));
//		//BigDecimal bigTwo = BigDecimal.valueOf(2);
//		//BigDecimal bigTwo = BigDecimal.ONE.add(BigDecimal.ONE);
//		//System.out.print(" 2^65536=" + (bigTwo.pow(65536)));
//		for (int i=0;i<x;i++) {
//			System.out.println(i + ". ack=" + ackermann(new BigDecimal(3), new BigDecimal(i)));
//		}
//	}


	public static BigDecimal ackermann(BigDecimal m, BigDecimal n) {
		//System.out.print("A0(" + m + ", " + n +") = ");
		if (m.equals(BigDecimal.ZERO)) {
			//System.out.print(n.add(BigDecimal.ONE) + "\n");
			return n.add(BigDecimal.ONE);
		}
		if (n.equals(BigDecimal.ZERO)) {
			//System.out.print("A1(" + m.subtract(BigDecimal.ONE) + ", " + BigDecimal.ONE+ ")");
			return ackermann(m.subtract(BigDecimal.ONE), BigDecimal.ONE);
		}
		BigDecimal first = m.subtract(BigDecimal.ONE);
		BigDecimal second = ackermann(m, n.subtract(BigDecimal.ONE));
		//System.out.print("A2(" + first + ", " + second + ")");
		return ackermann(first, second);
	}


	public static int ack(int m, int n) {
		if (m == 0) {
			return n + 1;
		} else if (n == 0) {
			return ack(m - 1, 1);
		} else {
			return ack(m - 1, ack(m, n - 1));
		}
	}

}
