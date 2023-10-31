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

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Random;
import processing.core.PImage;

/*
 * extends PApplet, FishTank class is of type PApplet
 */
public class FishTank extends PApplet {

	private PImage backgroundImage; // PImage object which represents the background image
	protected ArrayList<TankListener> objects; // list storing interactive objects
	protected Random randGen; // Generator of random numbers

	public static void main(String[] args) {
		PApplet.main("FishTank"); // do not add any other statement to the main method
		// The PApplet.main() method takes a String input parameter which represents
		// the name of your PApplet class.
	}

	@Override
	// sets the size of this PApplet to 800 width x 600 height
	public void settings() {
		size(800, 600);
	}

	@Override
	// Defines initial environment properties such as screen size and
	// loads the background image and fonts as the program starts.
	// It also initializes all data fields.
	// The above IS NOT a javadoc style method header!
	/*
	 * initializes data fields, adds objects to declared arrayList objects to be
	 * drawn
	 */
	public void setup() {
		// Set and display the title of the display window
		this.getSurface().setTitle("Fish Tank 3000");
		// Set the location from which images are drawn to CENTER
		this.imageMode(PApplet.CENTER);
		// Set the location from which rectangles are drawn.
		this.rectMode(PApplet.CORNERS);
		// rectMode(CORNERS) interprets the first two parameters of rect() method
		// as the location of one corner, and the third and fourth parameters as
		// the location of the opposite corner.
		// rect() method draws a rectangle to the display window

		this.focused = true; // Confirms that our Processing program is focused,
		// meaning that it is active and will accept mouse or keyboard input.

		// sets the text alignment to center
		this.textAlign(PApplet.CENTER, PApplet.CENTER);

		// TODO load the background image and store the loaded image to backgroundImage
		// Note that you can call the loadImage() method directly (this.loadImage())
		backgroundImage = this.loadImage("images/background.png");

		// TODO create an empty array list of objects
		this.objects = new ArrayList<TankListener>();

		// TODO set randGen to the reference of a new Random objects
		this.randGen = new Random();

		// calls from TankObject to set the PApplet graphic display window for all
		// TankObjects
		TankObject.setProcessing(this);

		// declaring the 4 decoration objects flower log, ship, and shell as tank
		// objects and adding it to the arrayList objects
		TankObject flower = new TankObject(430, 60, "flower.png");
		TankObject log = new TankObject(580, 470, "log.png");
		TankObject shell = new TankObject(65, 520, "shell.png");
		TankObject ship = new TankObject(280, 535, "ship.png");
		objects.add(flower);
		objects.add(log);
		objects.add(shell);
		objects.add(ship);

		// two objects of class BlackFish being declared and added to the arrayList
		// objects
		BlackFish b1 = new BlackFish(log, flower);
		BlackFish b2 = new BlackFish(shell, flower);
		addObject(b1);
		addObject(b2);

		// creating objects for each button for each of their corresponding functions
		// nad
		// adding them to the arrayList objects
		Button.setProcessing(this);
		AddBlueFishButton b = new AddBlueFishButton(43, 16);
		AddOrangeFishButton o = new AddOrangeFishButton(129, 16);
		AddYellowFishButton y = new AddYellowFishButton(215, 16);
		ClearTankButton c = new ClearTankButton(301, 16);
		addObject(b);
		addObject(o);
		addObject(y);
		addObject(c);
	}

	// Removes instances of the class Fish from the tank by removing objects from
	// the arrayList
	// objects
	public void clear() {
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i) instanceof Fish) {
				objects.remove(i);
				i--;
			}
		}
	}

	@Override
	// Continuously draws and updates the application display window
	public void draw() {
		// TODO clear the display window by drawing the background image
		this.image(backgroundImage, this.width / 2, this.height / 2);

		// TODO traverse the objects list and draw each of the objects to this display
		// window
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).draw();
		}
	}

	@Override
	// Callback method called each time the user presses the mouse
	public void mousePressed() {
		// TODO traverse the objects list and call mousePressed method
		// of the first object being clicked in the list
		// traverse the fishes array and start dragging a fish if the mouse is over it
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).isMouseOver()) {
				objects.get(i).mousePressed();
				return;
				// only the fish at the lowest index will start dragging if there are fishes or
				// other objects overlapping
			}
		}
	}

	@Override
	// Callback method called each time the mouse is released
	public void mouseReleased() {
		// TODO traverse the objects list and call each object's mouseReleased() method
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).mouseReleased();
		}
	}

	/*
	 * adds an instance of TankListener passed as input to the objects arrayList
	 * @param TankListener object is an object of type tankListener representing either
	 * a Fish, Button, or Decoration
	 */
	public void addObject(TankListener object) {
		objects.add(object);
	}

	@Override
	// Callback method called each time the user presses a key
	public void keyPressed() {

		// if the key f is pressed, adds a new orange fish object to the fishes array
		// with
		// random location
		if (this.key == 'O' || this.key == 'o') {
			Fish orange = new Fish();
			addObject(orange);
		}

		// if the y key is pressed, adds a new yellow fish object to the fishes array
		// with
		// random location at speed 2
		if (this.key == 'Y' || this.key == 'y') {
			Fish yellow = new Fish(2, "yellow.png");
			addObject(yellow);
		}

		// if the b key is pressed, adds a new blue fish object to the fishes array with
		// random location at speed 2
		if (this.key == 'B' || this.key == 'b') {
			BlueFish blue = new BlueFish();
			addObject(blue);
		}

		// if the r key is pressed, removes any fish objects that the mouse is hovering
		// over
		// from the arrayList objects
		if (this.key == 'R' || this.key == 'r') {
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i).isMouseOver() && objects.get(i) instanceof Fish) {
					objects.remove(i);
					i--;
					break;
				}
			}
		}

		// if the s key is pressed, causes all the fish in the fishtank to start
		// swimming
		// in the correct direction at the instantiated speed value
		if (this.key == 'S' || this.key == 's') {
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i) instanceof Fish) {
					((Fish) objects.get(i)).startSwimming();
				}
			}
		}

		// if the x key is pressed, causes all fish in the objects arrayList to stop
		// swimming
		if (this.key == 'X' || this.key == 'x') {
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i) instanceof Fish) {
					((Fish) objects.get(i)).stopSwimming();
				}
			}
		}

		// if the c key is pressed, clears all fish in the objects arrayList by removing
		// the fish objects from the arrayList
		if (this.key == 'C' || this.key == 'c') {
			clear();
		}
	}
}
