package jframestuff;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

/**
 * Used to experiment with the JFrame class
 * 
 * @author ken437
 * @version 11/13/2019
 */
public class JFrameWindow extends JFrame {
    
    /**
     * Eclipse made me add this
     */
    private static final long serialVersionUID = 1L;
    
    private static final Task DEFAULT_TASK = Task.GENERATE_RANDOM_LINES;
    private Task task;
    
    /**
     * Creates a new JFrameWindow object
     */
    public JFrameWindow()
    {
        super("My First JFrame!");
        task = Task.CREATE_TREE_FRACTAL;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }
    
    /**
     * Draw something on the board, depending on the task field
     * 
     * @param g a graphics object
     */
    public void paint(Graphics g)
    {
        //for draw statements in the if/else block to use
        int windowWidth = this.getWidth();
        int windowHeight = this.getHeight();
        
        if (task == Task.GENERATE_RANDOM_LINES)
        {
            for (int i = 0; i < 1000; i++)
            {
                Random generator = new Random();
                int random1 = generator.nextInt(windowWidth);
                int random2 = generator.nextInt(windowHeight);
                int random3 = generator.nextInt(windowWidth);
                int random4 = generator.nextInt(windowHeight);
            
                g.drawLine(random1, random2, random3, random4);
            }
        }
        else if (task == Task.CREATE_NESTED_SQUARES_FRACTAL)
        {
            this.drawNestedSquares(g);
        }
        else if (task == Task.CREATE_TREE_FRACTAL)
        {
            this.drawTree(g);
        }
        else
        {
            //fill in later
        }
    }
    
    /**
     * Calls the recursive nested squares method with upper left corner at
     * (100, 100), side length of 1800, and level of 10
     * 
     * @param g a graphics object
     */
    private void drawNestedSquares(Graphics g)
    {
        this.drawNestedSquares(g, 100, 100, 1800, 10);
    }
    
    /**
     * Uses recursion to draw a nested squares fractal
     * 
     * @param g a graphics object
     * 
     * @param upperLeftX the x coordinate of the upper left corner of the fractal
     * @param upperLeftY the y coordinate of the upper left corner of the fractal
     * @param sideLength the length of each side of this fractal
     * @param level the current level of this fractal
     */
    private void drawNestedSquares(Graphics g, int upperLeftX, int upperLeftY, int sideLength, int level)
    {
        if (level != 0)
        {
            int bottomRightX = upperLeftX + sideLength;
            int bottomRightY = upperLeftY + sideLength;
        
            //drawing outer square lines
            g.drawLine(upperLeftX, upperLeftY, bottomRightX, upperLeftY);
            g.drawLine(bottomRightX, upperLeftY, bottomRightX, bottomRightY);
            g.drawLine(bottomRightX, bottomRightY, upperLeftX, bottomRightY);
            g.drawLine(upperLeftX, bottomRightY, upperLeftX, upperLeftY);
            
            //filling the square with black
            g.setColor(Color.DARK_GRAY.darker());
            g.fillRect(upperLeftX, upperLeftY, sideLength, sideLength);
          
            //drawing inner square lines
            int centerX = upperLeftX + sideLength / 2;
            int centerY = upperLeftY + sideLength / 2;
            
            g.drawLine(upperLeftX, centerY, centerX, upperLeftY);
            g.drawLine(centerX, upperLeftY, bottomRightX, centerY);
            g.drawLine(bottomRightX, centerY, centerX, bottomRightY);
            g.drawLine(centerX, bottomRightY, upperLeftX, centerY);
            
            //filling the square with white
            g.setColor(Color.red.darker());
            int[] xPoints = {upperLeftX, centerX, bottomRightX, centerX};
            int[] yPoints = {centerY, upperLeftY, centerY, bottomRightY};
            g.fillPolygon(xPoints, yPoints, 4);  
            
            //recursive call
            int newUpperLeftX = upperLeftX + sideLength / 4;
            int newUpperLeftY = upperLeftY + sideLength / 4;
            int newSideLength = sideLength / 2;
            this.drawNestedSquares(g, newUpperLeftX, newUpperLeftY, newSideLength, level - 1);
        }
    }
    
    /**
     * Draws a recursively generated tree (level 14) with the root of its
     * trunk in the lower middle part of the screen, with its trunk vertical, and with
     * a trunk length of 500
     * 
     * @param g a Graphics object
     */
    private void drawTree(Graphics g)
    {
        this.drawTree(g, this.getWidth() / 2, 7 * this.getHeight() / 8,
            3 * Math.PI / 2, 500, 14);
    }
    
    /**
     * Draws a recursively generated tree
     * 
     * @param g a Graphics object
     * 
     * @param rootX the x coordinate of the tree's root
     * @param rootY the y coordinate of the tree's root
     * @param direction the angle that the tree's trunk will point (in radians).
     * 0 is right, pi/2 is up, pi is right, and 3*pi/2 is down
     * @param level the current level of this fractal
     */
    private void drawTree(Graphics g, double rootX, double rootY, double direction, double trunkLength, int level)
    {
        //on each level, the trunk's length is multiplied by this
        double trunkReductionRatio = 0.7; 
        //these determine the angle between each new trunk and the previous trunk
        double leftBranchAngle = Math.PI / 6;
        double rightBranchAngle = -Math.PI / 3;
        if (level != 0)
        {   
            //draw a line representing the trunk
            double tipX = rootX + trunkLength * Math.cos(direction);
            double tipY = rootY + trunkLength * Math.sin(direction);
            //make the line thickness proportional to the level
            ((Graphics2D) g).setStroke(new BasicStroke(level));
            g.drawLine((int) rootX, (int) rootY, (int) tipX, (int) tipY);
            
            //make two recursive calls for the two other "branches" that will be generated.
            //new trunk will start at this trunk's tip and have a different direction, level,
            //and trunkLength
            double newDirectionBranch1 = direction + leftBranchAngle;
            double newDirectionBranch2 = direction + rightBranchAngle;
            double newTrunkLength = 0.7 * trunkLength;
            
            this.drawTree(g, tipX, tipY, newDirectionBranch1, newTrunkLength, level - 1);
            this.drawTree(g, tipX, tipY, newDirectionBranch2, newTrunkLength, level - 1);            
        }
    }

}
