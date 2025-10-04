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
 * extends Fish, BlackFish is of type Fish
 */
public class BlackFish extends Fish {
	// private instance field of type TankObject representing the location the BlackFish will
	// travel from
	private TankObject source;
	// private instance field of type TankObject representing the location the BlackFish will
	// travel to
	private TankObject destination;

	/*
	 * constructor for class BlackFish that creates a black fish object of speed 2
	 * @param source representing the object the fish starts at, destination representing the
	 * object the fish travels to
	 */
	public BlackFish(TankObject source, TankObject destination) {
		super(2, "black.png");
		this.source = source;
		this.destination = destination;
	}

	/*
	 * moves the fish towards the destination
	 */
	public void moveTowardsDestination() {
		// distance traveled in x direction
		float dx = destination.getX() - this.getX();
		//distance traveled in y direction
		float dy = destination.getY() - this.getY();
		// distance formula
		int d = (int) Math.sqrt(dx * dx + dy * dy);
		//sets new x and y values for the fish, moving it towards its destination
		this.setX(this.getX() + (this.speed() * dx) / d);
		this.setY(this.getY() + (this.speed() * dy) / d);
	}

	// returns true if this black fish is over another tank object, and false otherwise
	public boolean isOver(TankObject other) {
		return this.getX() < other.getX() + other.image.width && this.getX() + other.image.width > other.getX()
				&& this.getY() < other.getY() + other.image.height && this.getY() + other.image.height > other.getY();
	}

	@Override
	/* 
	 * Overrides Fish.swim() method
	 */
	public void swim() {
		// move the fish towards its destination
		moveTowardsDestination();
		// if destination is reached (meaning this fish is over its destination,
		// switch source and destination
		if (isOver(destination)) {
			TankObject placeHolder = destination;
			destination = source;
			source = placeHolder;
		}
	}
}
