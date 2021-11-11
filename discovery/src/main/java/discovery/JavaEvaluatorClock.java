package discovery;

import gnu.jel.Evaluator;
import gnu.jel.CompiledExpression;
import gnu.jel.Library;
import gnu.jel.CompilationException;

public class JavaEvaluatorClock {


	public static void main(String[] args) {
		System.out.println("\nJavaEvaluator.main: The following example uses one variable.");
		IfThenRule ifThenRule = new IfThenRule("isPalidrome(timeAsSingleStringVar)", "The time as a single string is a Palidrome.");
		System.out.println(ifThenRule + "   result: " + JavaEvaluatorClock.evaluate(ifThenRule.getExpression(), "1221"));
		System.out.println("   result: " + JavaEvaluatorClock.evaluate("isFibonacci(timeAsSingleNumberVar)", 610));
		System.out.println("   result: " + JavaEvaluatorClock.evaluate("isSquarePyramidNumber(timeAsSingleNumberVar)", 819)); 

		System.out.println("\nJavaEvaluator.main: The following examples uses two variables.");
		System.out.println("	result: " + JavaEvaluatorClock.evaluate("hours == minutes", 12, 12));
		System.out.println("	result: " + JavaEvaluatorClock.evaluate("hours * 2 == minutes", 4, 8));
		System.out.println("	answer: " + JavaEvaluatorClock.evaluate("isPrime(hours) && isPrime(minutes)", 3, 17));

		System.out.println("\nJavaEvaluator.main: The following example uses four variables.");
		System.out.println("   result: " + JavaEvaluatorClock.evaluate("hourTens + hourOnes == minuteTens + minuteOnes", 1, 1, 0, 2));
		System.out.println("   result: " + JavaEvaluatorClock.evaluate("hourTens * hourOnes == minuteTens * minuteOnes", 1, 2, 2, 1));

		System.out.println("\nJavaEvaluator.main: The following example uses six out of eight possible variables.");
		System.out.println("   result: " + JavaEvaluatorClock.evaluate("hours + hourTens + hourOnes == minutes + minuteTens + minuteOnes ", 1, 2, 1, 2));
		
		// Which time satisfies timeAsSingleNumberVar == hours + hourTens + hourOnes + minutes + minuteTens + minuteOnes?
		//		System.out.println("\nIf lots of data must fed into the same expression, then compile it separately first.");
		//		VariableProvider variables = new VariableProvider();
		//		Object[] context = getContext(variables);
		//		Library library = JavaEvaluator.setupLibrary(variables);
		//		String expression = "(y + x + z)";
		//		CompiledExpression compiled = JavaEvaluator.compile(expression, library);
		//		for (int i=0; i<10; i++) {
		//			System.out.println(i + ".  Loop result ==> " + JavaEvaluatorClock.run(expression, compiled, variables, context, i, i*2, i*3));
		//		}
		for (int i=0; i<100; i++) {
			System.out.println(i + ".  Loop result ==> " + JavaEvaluatorClock.evaluate("isMersennePrime(timeAsSingleNumberVar)", i));
			System.out.println(i + ".  Bell Loop result ==> " + JavaEvaluatorClock.evaluate("isBellsNumber(timeAsSingleNumberVar)", i));

			}
		
	}

	/*
	 * Convenience functions
	 * We *could* have three entire evaluate methods, but the only difference would be
	 * the three lines variables.xVar=x;  variables.yVar=y; variables.zVar=z;
	 * If they are strings or BigDecimals, or whatever, then you'll need to change that too.
	 */

	static Object evaluate(IfThenRule rule, String timeAsSingleString, int timeAsSingleNumber, int hours, int minutes, 
																int hourTens, int hourOnes, int minuteTens, int minuteOnes) {
		if (!rule.getIntermediateExpression().equals("")) {
			Object intermediateValue = evaluate(rule.getIntermediateExpression(), timeAsSingleString, timeAsSingleNumber, hours, minutes, 
				hourTens, hourOnes, minuteTens, minuteOnes);
			rule.setIntermediateValue(intermediateValue);
			rule.setDescription(rule.getDescription().replace("<result>", intermediateValue.toString()));
		}
		return evaluate(rule.getExpression(), timeAsSingleString, timeAsSingleNumber, hours, minutes, 
							hourTens, hourOnes, minuteTens, minuteOnes);
	}

	static Object evaluate(String expression, String timeAsSingleString) {
		return evaluate(expression, timeAsSingleString, 0, 0, 0,    0, 0, 0, 0);
	}

	static Object evaluate(String expression, int timeAsSingleNumber) {
		return evaluate(expression, "", timeAsSingleNumber, 0, 0,    0, 0, 0, 0);
	}

	static Object evaluate(String expression, int hours, int minutes) {
		return evaluate(expression, "", 0, hours, minutes,    0, 0, 0, 0);
	}

	static Object evaluate(String expression, int hourTens, int hourOnes, int minuteTens, int minuteOnes) {
		return evaluate(expression, "", 0, 0, 0,    hourTens, hourOnes, minuteTens, minuteOnes);
	}
	
	static Object evaluate(String expression, String intermediateExpression, 
							String timeAsSingleString, int timeAsSingleNumber, int hours, int minutes, 
							int hourTens, int hourOnes, int minuteTens, int minuteOnes) {
		Object value = evaluate(intermediateExpression, timeAsSingleString, timeAsSingleNumber, hours, minutes,   hourTens, hourOnes, minuteTens, minuteOnes);
		
		return evaluate(expression, "", 0, 0, 0,    hourTens, hourOnes, minuteTens, minuteOnes);
	}

	static Object evaluate(String expression, String timeAsSingleString, int timeAsSingleNumber, int hours, int minutes, 
												int hourTens, int hourOnes, int minuteTens, int minuteOnes) {
		System.out.println("evaluate: expression: " + expression + " where timeAsSingleString=" + timeAsSingleString + 
				" timeAsSingleNumber=" + timeAsSingleNumber + " hours=" + hours + " minutes=" + minutes + 
				" hourTens=" + hourTens + " hourOnes=" + hourOnes + " minuteTens=" + minuteTens + " minuteOnes=" + minuteOnes);
		// Set up library
		Class[] staticLib=new Class<?>[2];
		try {
			staticLib[0]=Class.forName("java.lang.Math");
			staticLib[1]=Class.forName("discovery.VariableProviderClock");
		} catch(ClassNotFoundException cnfex) {
			cnfex.printStackTrace();
		};

		//Library lib=new Library(staticLib,null,null,null,null); // no variables
		Class[] dynamicLib=new Class[1];
		VariableProviderClock variables = new VariableProviderClock();
		Object[] context=new Object[1];
		context[0]=variables;
		dynamicLib[0]=variables.getClass();

		Library lib = new Library(staticLib, dynamicLib, null, null, null);

		try {
			lib.markStateDependent("random", null);
		} catch (CompilationException cex) {
			cex.printStackTrace();
		};

		CompiledExpression expressionCompiled = null;
		try {
			expressionCompiled = Evaluator.compile(expression, lib);
		} catch (CompilationException cex) {
			System.err.print("--- COMPILATION ERROR :");
			System.err.println(cex.getMessage());
			System.err.print("                       ");
			System.err.println(expression);
			int column = cex.getColumn(); // Column, where error was found
			for(int i=0; i<column+23-1; i++) System.err.print(' ');
			System.err.println('^');
		}

		Object result = null;
		// Once it's compiled, it works much faster (in case we want to use it again).
		if (expressionCompiled != null) {
			try {
				variables.timeAsSingleStringVar = timeAsSingleString; 
				variables.timeAsSingleNumberVar = timeAsSingleNumber;
				variables.hoursVar = hours;
				variables.minutesVar = minutes;
				variables.hourTensVar = hourTens;
				variables.hourOnesVar = hourOnes;
				variables.minuteTensVar = minuteTens;
				variables.minuteOnesVar = minuteOnes;
				result = expressionCompiled.evaluate(context);
				//		              System.out.println("Expression: " + expression + " where timeAsSingleString=" + timeAsSingleString + 
				//		            		  " timeAsSingleNumber=" + timeAsSingleNumber + " hours=" + hours + " minutes=" + minutes + 
				//		            		  " hourTens=" + hourTens + " hourOnes=" + hourOnes + " minuteTens=" + minuteTens + " minuteOnes=" + minuteOnes + " result=" + result);
			} catch (Throwable e) {
				System.err.println("Exception emerged from JEL compiled" + " code (IT'S OK):");
				System.err.print(e);
			};

			// Print result
			if (result==null) {  System.out.println("void"); }
			else { 
				//System.out.println("Expression " + expression + " ==> " + result.toString()); 
				return result;
			}
		} else {
			System.err.println("Missing compiler for expression: " + expression + " where timeAsSingleString=" + timeAsSingleString + 
					" timeAsSingleNumber=" + timeAsSingleNumber + " hours=" + hours + " minutes=" + minutes + 
					" hourTens=" + hourTens + " hourOnes=" + hourOnes + " minuteTens=" + minuteTens + " minuteOnes=" + minuteOnes);
			return null;
		}
		System.out.println("Expression: " + expression + " where timeAsSingleString=" + timeAsSingleString + 
				" timeAsSingleNumber=" + timeAsSingleNumber + " hours=" + hours + " minutes=" + minutes + 
				" hourTens=" + hourTens + " hourOnes=" + hourOnes + " minuteTens=" + minuteTens + " minuteOnes=" + minuteOnes + " result=" + result);
		return result;
	}

	static VariableProviderClock getVariables() {
		return new VariableProviderClock();
	}

	static Object[] getContext(VariableProviderClock variables) {
		Object[] context = new Object[1];
		context[0] = variables;
		return context;
	}

	static Library setupLibrary(VariableProviderClock variables) {
		Class[] staticLib = new Class<?>[2];
		try {
			staticLib[0] = Class.forName("java.lang.Math");
			staticLib[1] = Class.forName("discovery.VariableProviderClock");
		} catch(ClassNotFoundException cnfex) {
			cnfex.printStackTrace();
		};
		Class[] dynamicLib = new Class[1];
		dynamicLib[0] = variables.getClass();
		Library lib = new Library(staticLib, dynamicLib, null, null, null);
		return lib;
	}


	static CompiledExpression compile(String expression, Library lib) {
		try {
			lib.markStateDependent("random", null);  // because we use the Math library, and random requires special treatment.
		} catch (CompilationException cex) {
			cex.printStackTrace();
		};

		System.out.println("Compiling Expression: " + expression);
		CompiledExpression expressionCompiled = null;
		try {
			expressionCompiled = Evaluator.compile(expression, lib);
		} catch (CompilationException cex) {
			System.err.print("--- COMPILATION ERROR :");
			System.err.println(cex.getMessage());
			System.err.print("                       ");
			System.err.println(expression);
			int column = cex.getColumn(); // Column, where error was found
			for(int i=0; i<column+23-1; i++) System.err.print(' ');
			System.err.println('^');
			return null;
		}
		return expressionCompiled;
	}

}

