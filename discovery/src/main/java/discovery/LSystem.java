package discovery;

/*
 * LSystem.java
 *
 * Created on August 31, 2004
 * Created by Joel Rathgaber
 * Updated by Howard J. Hamilton, March 23, 2011: made stack templated
 */

import java.awt.geom.*;
import java.util.Stack;

/*
 * LSystem is a class that implements a Bracketed L-System.  Essentially all
 * it does is replace the axiom value in a string, with a replacement rule on 
 * every iteration and draw the plant using a turtle graphics approach.  
 * There are also a number of parameters such as branching angle and stem
 * length that can be adjusted for every system.
 * 
 * The grammar for creating an L-System is as follows:
 * 		F - Move forward and draw a line
 * 		f - Move forward and do no draw a line
 * 		+ - Turn left alpha degrees
 * 		- - Turn right alpha degrees
 * 		[ - Save current state and push onto stack 
 * 		] - restore top state from stack
 */
public class LSystem {

	private String axiom;
	private String rule;
	private Point2D.Float root;
	private double branchAngle;
	private int stemLength;

	/*
	 * Creates a new LSystem with a position, axiom and replacement rule.
	 * The position is that of the 'root' of the plant, and the axiom will
	 * be replaced by the rule on every iteration.
	 */
	public LSystem(Point2D.Float rt, String a, String r) {
		root  = rt;
		axiom = a;
		rule  = r;
	}

	public void setAlphaDegrees(double degrees) {
		branchAngle = Math.toRadians(degrees);
	}

	public void setStemLength(int pixels) {
		stemLength = pixels;
	}

	/*
	 * The CursorState class simply records the x and y position of the 
	 * cursor (or turtle) and the heading of the cursor so it can be
	 * pushed onto a stack and restored later.
	 */
	private class CursorState {
		public float x;
		public float y;
		public double a;

		public CursorState(float _x, float _y, double _a) {
			x = _x;
			y = _y;
			a = _a;
		}
	}

	/*
	 * The drawPlant method will generate a GeneralPath object that can
	 * be drawn to the screen based on the current parameters of the system
	 * (like axiom, replacement rule, alpha angle, etc.) and the number of 
	 * iterations passed to the method.
	 */
	public GeneralPath drawPlant(int iterations) {
		String plant = axiom;

		// Create the grammar string by doing parallel replacements
		for (int i = 0; i < iterations; i++) {
			plant = plant.replaceAll(axiom, rule);
		}

		// Once we have the string, we can start to draw
		GeneralPath gp = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

		Stack <CursorState> stack = new Stack <CursorState>();
		float currX = (float)root.getX();
		float currY = (float)root.getY();
		double alpha = 0.0;
		gp.moveTo(currX, currY);

		for (int i = 0; i < plant.length(); i++) {
			char c = plant.charAt(i);

			switch (c) {
			case 'F': // Move forward in the specified heading and draw a line
				currX += (float)Math.sin(alpha) * stemLength;
				currY += (float)Math.cos(alpha) * stemLength;
				gp.lineTo(currX, currY);
				break;

			case 'f': // Move forward in the specified heading but do not draw a line
				currX += (float)Math.sin(alpha) * stemLength;
				currY += (float)Math.cos(alpha) * stemLength;
				gp.moveTo(currX, currY);
				break;

			case '+': // rotate heading specified degrees to the left
				alpha += branchAngle;
				break;

			case '-': // rotate heading specified degrees to the right
				alpha -= branchAngle;
				break;

			case '[': // push current state onto the stack
				stack.push(new CursorState(currX, currY, alpha));
				break;

			case ']': // pop state from stack and restore it to current
				CursorState state = (CursorState)stack.pop();
				currX = state.x;
				currY = state.y;
				alpha = state.a;
				gp.moveTo(currX, currY);
				break;
			}
		}

		// return the GeneralPath object so it can be displayed
		return gp;
	}
}
