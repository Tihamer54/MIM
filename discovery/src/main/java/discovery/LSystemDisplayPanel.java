package discovery;

/*
 * LSystemDisplayPanel.java
 *
 * Created on August 31, 2004
 * Created by Joel Rathgaber
 */

import java.util.LinkedList;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.awt.geom.AffineTransform;

/* 
 * The purpose of the LSystemDisplayPanel is simply to extend a JPanel
 * component so that we can give it a GeneralPath object and it will 
 * draw it to the screen.
 */
public class LSystemDisplayPanel extends javax.swing.JPanel {

	// The GeneralPath object that will be drawn 
    private GeneralPath tree = null;
    
    public LSystemDisplayPanel() {
        initComponents();
    }
    
    /* This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        setLayout(new java.awt.BorderLayout());

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        setFocusable(false);
        setMaximumSize(new java.awt.Dimension(512, 512));
        setMinimumSize(new java.awt.Dimension(512, 512));
    }//GEN-END:initComponents

	/*
	 * Overloaded method for painting the GeneralPath to the Panel
	 */
    public void paint(java.awt.Graphics g) {
        super.paint(g);
   		
		// If there is a tree, draw it
        if (tree != null) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(Color.BLACK);
            g2.draw(tree);
        }
    }    
    
    public void setPlant(GeneralPath gp) {
        tree = gp;
        
		// Since the coordinates of the panel originate in the top left hand corner
		// and that is pretty un-intuitive for us we use an affine transformation to
		// rotate the entire object 180 degrees (so that it grows in the up direction)
        AffineTransform at = new AffineTransform();
        at.setToRotation(Math.PI, getWidth() / 2.0f, getHeight() / 2.0f);
        tree.transform(at);
    }
}