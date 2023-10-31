//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P05 Fish Tank 3000
// Course: CS 300 Fall 2021
//
// Author: Alan Luo
// Email: aluo7@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: Isha Rustagi
// Partner Email: irustagi@wisc.edu
// Partner Lecturer's Name: Hobbes LeGault
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// X Write-up states that pair programming is allowed for this assignment.
// X We have both read and understand the course Pair Programming Policy.
// X We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: none
// Online Sources: none
//
///////////////////////////////////////////////////////////////////////////////

/*
 * implements TankListener, Button is of type TankListener
 */
public class Button implements TankListener {
	// Width of this Button
	private static final int WIDTH = 85; 
	// Height of this Button
	private static final int HEIGHT = 32; 
	// PApplet object where this button will be displayed
	protected static FishTank tank; 
	// x-position of this button in the display window
	private float x; 
	// y-position of this button in the display window
	private float y; 
	// text/label which represents this button
	protected String label; 

	/*
	 *  Creates a new Button at a given position within the display window and sets its label
	 *  @param label represents the button type, x type float represents x location of the 
	 *  button, y type float represents the y location of the button
	 */

	public Button(String label, float x, float y) {
		this.x = x;
		this.y = y;
		this.label = label;
	}

	/*
	 * sets the PApplet display window where this button is displayed and handled
	 */
	public static void setProcessing(FishTank tank) {
		TankObject.setProcessing(tank);
		Button.tank = tank;
	}

	@Override
	/*
	 * Overrides the TankListener.isMouseOver() method
	 * Checks whether the mouse is over this button
     * returns true if the mouse is over this button, false otherwise.
	 */
	public boolean isMouseOver() {
		// The implementation of this behavior must be similar to the way to check
		// whether the mouse is over an image. The button is a rectangle whose x,y
		// position is at its center. The width and height of a button are defined as
		// static data fields.
		return tank.mouseX >= x - WIDTH / 2 && tank.mouseX <= x + WIDTH / 2 &&
				tank.mouseY >= y - HEIGHT / 2 && tank.mouseY <= y + HEIGHT / 2;
	}

	/* 
	 * Overrides TankListener.draw() method
	 * Draws this button to the display window
	 */
	public void draw() {
		// set line value to black
		tank.stroke(0);

		// TODO if the mouse is over this button, sets the fill color to dark gray.
		// Sets the fill color to light gray otherwise
		if(isMouseOver()) {
			tank.fill(100);
		}
		else {
			tank.fill(200);
		}

		// draw the button (rectangle with a centered text)
		tank.rect(x - WIDTH / 2.0f, y - HEIGHT / 2.0f, x + WIDTH / 2.0f, y + HEIGHT / 2.0f);
		// set the fill color to black
		tank.fill(0); 
		// display the text of the current button
		tank.text(label, x, y); 
	}

	@Override
	/* Overrides the TankListener.mousePressed() method
	 * Implements the default behavior of this button when the mouse is pressed.
	 * This method must be overridden in the sub-classes to implement a specific
	 * behavior if needed.
	 */
	public void mousePressed() {
		// TODO if the mouse is over this button, print
		// "A button was pressed." to the console
		if(isMouseOver()) {
			System.out.println("A button was pressed.");
		}
	}

	/* Overrides the TankListener.mouseReleased() method
	 * Implements the default behavior of this button when the mouse is released.
	 * This method must be overridden in the sub-classes to implement a specific
	 * behavior if needed.
	 */
	public void mouseReleased() {
		// Leave this method empty
	}
}
