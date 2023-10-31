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
 * extends TankObject, Fish class is of type TankObject
 */
public class Fish extends TankObject {
	// private instance field representing the speed that the fish travels at
	private int speed; 
	// private instance field that indicates whether the fish is swimming or not
	private boolean isSwimming; 

	/*
	 * constructor for fish class
	 * @throws IllegalArgumentException if speed input is invalid (negative or 0)
	 * @param takes in speed param which represents the speed that the fish travels at,
	 * fishImageFileName representing is the file name of the fish object being declared
	 */
	public Fish(int speed, String fishImageFileName) throws IllegalArgumentException {
		// calls the TankObject constructor in order to create a fish object at a random 
		// location of fish type fishImageFileName
		super((float) tank.randGen.nextInt(tank.width), 
				(float) tank.randGen.nextInt(tank.height), fishImageFileName);

		// checks if speed is negative or equals 0, throws IllegalArgumentException
		// and warning message, if so, otherwise sets instance variable speed to
		// parameter speed
		if (speed <= 0) {
			throw new IllegalArgumentException("Warning: speed cannot be negative");
		}
		this.speed = speed;
	}

	/*
	 * no argument constructor for fish, creates orange fish with speed 5
	 */
	public Fish() {
		this(5, "orange.png");
	}

	@Override
	// Overrides the draw() method implemented in the parent class.
	// This method sets the position of this fish to follow the
	// mouse moves if it is dragging, calls its swim() method
	// if it is swimming, and draws it to the display window.
	// You can use a partial overriding (call draw() method of
	// the super class and adds the behavior specific to drawing a fish.
	public void draw() {
		super.draw();
		if (isSwimming) {
			swim();
		}
	}

	// Checks whether this fish is swimming
	public boolean isSwimming() {
		return isSwimming;
	}

	// Starts swimming this fish
	public void startSwimming() {
		this.stopDragging();
		this.isSwimming = true;
	}

	// Stops swimming this fish
	public void stopSwimming() {
		this.isSwimming = false;
	}

	// Gets the speed of this fish
	public int speed() {
		return speed;
	}

	// Moves horizontally the fish one speed step from left to right.
	public void swim() {
		setX((getX() + this.speed) % tank.width);
	}

}
