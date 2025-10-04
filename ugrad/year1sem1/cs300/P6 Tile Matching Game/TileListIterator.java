
import java.util.Iterator;
import java.util.NoSuchElementException;


public class TileListIterator implements Iterator<Tile> {
    private Node current;

    public TileListIterator(Node head) {
        this.current = head;
    }

    @Override
    public boolean hasNext() {
        if (current == null) {
            return false;
        }
        return true;
    }

    @Override
    public Tile next() throws NoSuchElementException {
        if (hasNext()) {
            Tile curr = current.getTile();
            current = current.getNext();
            return curr;
        } else {
            throw new NoSuchElementException("There are no more tiles in the iteration");
        }
    }
}
