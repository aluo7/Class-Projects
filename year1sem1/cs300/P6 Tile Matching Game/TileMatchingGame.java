//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P07 Folder Explorer
// Course: CS 300 Fall 2021
//
// Author: Alan Luo
// Email: aluo7@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: (name of your pair programming partner)
// Partner Email: (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: none
// Online Sources: none
//
///////////////////////////////////////////////////////////////////////////////

public class TileMatchingGame {
    // array of tileStacks
    private TileStack[] columns;

    /*
     * constructor initializes columns array
     * 
     * @param int columnCount represents the capacity of the array columns
     * 
     * @throws IllegalArgumentException if columnCounts is an invalid input
     */
    public TileMatchingGame(int columnCount) {
        if (columnCount <= 0) {
            throw new IllegalArgumentException("Invalid input, cannot initialize array");
        } else {
            columns = new TileStack[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columns[i] = new TileStack();
            }
        }
    }

    /*
     * removes all the tiles from a column with a given index
     * 
     * @param int index representing the index of the column
     * 
     * @throws IndexOutOfBoundsException if index is an invalid input
     */
    public void clearColumn(int index) {
        if (index < 0 || index >= columns.length) {
            throw new IndexOutOfBoundsException();
        } else {
            while (!columns[index].isEmpty()) {
                columns[index].pop();
            }
        }
    }

    /*
     * formats a string for the TileStack at the column index
     * 
     * @returns a string representation of the stack of tiles at a given column
     * index
     * 
     * @param int index representing the index of the column
     * 
     * @throws IndexOutOfBoundsException if index is an invalid input
     */
    public String column(int index) {
        if (index < 0 || index >= columns.length) {
            throw new IndexOutOfBoundsException();
        } else {
            String result = "";
            TileListIterator iterator = (TileListIterator) columns[index].iterator();

            while (iterator.hasNext()) {
                result += iterator.next() + " ";
            }
            return result.trim();
        }

    }

    /*
     * drops a tile at the top of the stack located at the given column index
     * 
     * @param Tile tile representing tile being droped
     * 
     * @param int index representing the index of the column
     * 
     * @throws IndexOutOfBoundsException if index is an invalid input
     */
    public void dropTile(Tile tile, int index) {
        if (index < 0 || index >= columns.length) {
            throw new IndexOutOfBoundsException();
        } else {
            columns[index].push(tile);
        }
    }

    /*
     * @returns the number of columns in this tile matching game
     */
    public int getColumnsNumber() {
        return columns.length;
    }

    /*
     * restarts the game
     */
    public void restartGame() {
        for (int i = 0; i < columns.length; i++) {
            clearColumn(i);
        }
    }

    @Override
    /*
     * @overrides toString()
     * 
     * @returns a string representation of this tile matching game
     */
    public String toString() {
        String result = "GAME COLUMNS:\n";

        for (int i = 0; i < columns.length; i++) {
            result += i + ": " + column(i) + "\n";
        }
        return result;
    }
}
