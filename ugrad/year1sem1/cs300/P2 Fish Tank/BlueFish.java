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
 * extends Fish, BlueFish is a Fish
 */
public class BlueFish extends Fish {
	
	// constructor for type BlueFish that creates a blue fish with initialized speed of 2
	public BlueFish() {
		// calls the constructor for Fish class
		super(2, "blue.png");
	}
	
	@Override
	// swim method for BlueFish overrides method from class Fish
	// makes the blue fish swim in opposite direction
	public void swim() {
		// variable to represent the new location of the blue fish after moving
		float newX = super.getX() - super.speed();
		
		// checks if fish is moving past left side of the tank, resets the fish to right side
		// otherwise keeps swimming like normal
		if(newX <= 0) {
			super.setX(tank.width);
		}
		else {
			super.setX(newX);
		}
	}
}