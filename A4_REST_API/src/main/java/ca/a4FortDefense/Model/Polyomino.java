package ca.a4FortDefense.Model;

import java.util.List;
import java.util.Map;

/**
 * The Polyomino interface represents any particular enemy fort without any consideration
 * about how large the fort is or how it looks, and as such. Any class which represents
 * an opponent fort must implement this interface and the methods such that it is compatible
 * with the rest of the game. The operations that must be implemented are creating a fort,
 * assigning a unique identifier to the fort, retreiving score, and registering hits on the
 * fort.
 */
public interface Polyomino {

    //Generates a random shape of the fort which is represented by a list
    //of coordinates when given an initial starting coordinate
    public void createFortCells(int y, int x);

    public List<Map.Entry<Integer, Integer>> getFortCells();

    public char getIdentifier();

    public void setIdentifier(char identifier);

    public int getScore();

    //Given a coordinate, determine if it belongs to this polyomino and if it has already been hit, otherwise
    //update the number of undamaged cells
    public void registerHit(int row, int col);

}
