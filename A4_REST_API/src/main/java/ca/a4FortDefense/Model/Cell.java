package ca.a4FortDefense.Model;

/**
 * The Cell class acts as an individual 1x1 block on the field and is responsible for keeping track of
 * its current state during the course of the game. That is, if it belongs to an enemy fort, and if so,
 * it keeps track of if it has been hit or not
 */
public class Cell {

    private final int X_COOR;
    private final int Y_COOR;
    private char identifier;
    private String hitStatus; //How the cell is displayed. "~" initially, " " if it's a miss, and "X" if it's a hit
    private boolean isFortCell;

    public Cell(int xInput, int yInput, char identifier, String displayStatus) {
        this.X_COOR = xInput;
        this.Y_COOR = yInput;
        this.identifier = identifier;
        this.hitStatus = displayStatus;
        this.isFortCell = false;
    }

    public String getDamageStatus() {
        return this.hitStatus;
    }

    public Boolean isHit(){
        if(!hitStatus.equals("~")){
            return true;
        } else {
            return false;
        }
    }

    public boolean isFog(){
        if(hitStatus.equals("~")){
            return true;
        } else{
            return false;
        }
    }
    public void registerHit(String hitStatus) {
        this.hitStatus = hitStatus;
    }

    public char getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(char identifier) {
        this.identifier = identifier;
    }

    public boolean getPlacedStatus() {
        return this.isFortCell;
    }

    public void setPlaced() {
        this.isFortCell = true;
    }
}
