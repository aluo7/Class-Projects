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

import processing.core.PImage;

/*
 * implements TankListener, TankObject is of type TankListener
 */
public class TankObject implements TankListener {
	// PApplet object which represents the display window
	protected static FishTank tank;
	// image of this tank object
	protected PImage image; 
	// x-position of this tank in the display window
	private float x; 
	// y-position of this tank in the display window
	private float y; 
	// boolean indicating whether this tank object is being dragged or not
	private boolean isDragging; 
	// old x-position of the mouse
	private static int oldMouseX; 
	// old y-position of the mouse
	private static int oldMouseY; 

	/*
	 * TankObject constructor which sets the initial location and object type of the object
	 * @param x type float representing the x poisition of the object, y type float 
	 * representing the y position of the object, imageFileName type string representing
	 * the file name of the object being created
	 */
	public TankObject(float x, float y, String imageFileName) {
		this.x = x;
		this.y = y;
		// initializes isDragging to false to indicate that the object isn't being dragged
		this.isDragging = false;
		// loads the image
		image = tank.loadImage("images/" + imageFileName);
	}

	// Sets the PApplet graphic display window for all TankObjects
	public static void setProcessing(FishTank tank) {
		TankObject.tank = tank;
	}

	// Moves this tank object with dx and dy
	// dx move to the x-position of this tank object
	// dy move to the y-position of this tank object
	public void move(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	// Returns the x-position of this tank object
	public float getX() {
		return this.x;
	}

	// Returns the y-position of this tank object
	public float getY() {
		return this.y;
	}

	// Sets the x-position of this object
	public void setX(float x) {
		this.x = x;
	}

	// Sets the y-position of this object
	public void setY(float y) {
		this.y = y;
	}

	// Returns the image of this tank object
	public PImage getImage() {
		return this.image;
	}

	// Getter of the isDragging field.
	// returns true if this object is being dragged, false otherwise
	public boolean isDragging() {
		return isDragging;
	}

	// Starts dragging this tank object
	public void startDragging() {
		TankObject.oldMouseX = tank.mouseX;
		TankObject.oldMouseY = tank.mouseY;
		isDragging = true;
	}

	// Stops dragging this tank object
	public void stopDragging() {
		isDragging = false;
	}
	
	@Override
	// draws this tank object to the display window
	public void draw() {
	    // if this object is dragging, set its position to follow the mouse moves
	    if (this.isDragging) {
	      int dx = tank.mouseX - TankObject.oldMouseX;
	      int dy = tank.mouseY - TankObject.oldMouseY;
	      move(dx, dy);
	      oldMouseX = tank.mouseX;
	      oldMouseY = tank.mouseY;
	    }

	    // draw this object at its current position
	    tank.image(image, x, y);
	}

	@Override
	// called each time the mouse is Pressed and starts dragging
	public void mousePressed() {
		startDragging();
	}

	@Override
	// called each time the mouse is Released and stops dragging
	public void mouseReleased() {
		stopDragging();
	}

	@Override
	// checks whether the mouse is over this Tank GUI
	// return true if the mouse is over this tank GUI object, false otherwise
	public boolean isMouseOver() {
	    return tank.mouseX >= x - this.image.width / 2 && tank.mouseX <= x + this.image.width / 2
	            && tank.mouseY >= y - this.image.height / 2 && tank.mouseY <= y + this.image.height / 2;
	}
}
