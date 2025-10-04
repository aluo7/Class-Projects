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
 * interface with methods common to the Fish, FishTank, and Button classes
 */
public interface TankListener {
	//draws this tank object to the display window
	public void draw();
	// called each time the mouse is Pressed
	public void mousePressed();
	// called each time the mouse is Released
	public void mouseReleased();
	// checks whether the mouse is over this Tank GUI
	// return true if the mouse is over this tank GUI object, false otherwise
	public boolean isMouseOver();

}
