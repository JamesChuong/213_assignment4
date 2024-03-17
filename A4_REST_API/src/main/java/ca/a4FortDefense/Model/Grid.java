package ca.a4FortDefense.Model;

import java.util.*;

/**
 * The Grid class acts as the game field and is repsonsible for tracking all hits made by the user
 * and displaying what it looks like after the end of each round, and displaying the location
 * of all enemy forts on the field if cheats are enables or at the end of the game. Changes are
 * tracked by updating the state of a particular cell as either a hit or a miss. This class is
 * also responsible for mapping all enemy forts on the field when given the coordinates.
 */
public class Grid {

    private final String CELL_NOT_HIT_STATUS = "~";
    private final String FORT_HIT_STATUS = "X";
    private final String FORT_MISS_STATUS = " ";
    private final char DEFAULT_IDENTIFIER = '!';
    private final int NUM_ROWS = 10;
    private final int NUM_COLUMNS = 10;

    private List<List<Cell>> field;



    private Map.Entry<Integer, Integer> convertCoordinate(String coordinate) {
        boolean isValidCoordinate = coordinate.length() >= 2
                && ((coordinate.charAt(0) >= 'A' && coordinate.charAt(0) <= 'J')
                || (coordinate.charAt(0) >= 'a' && coordinate.charAt(0) <= 'j'))
                && coordinate.substring(1).matches("\\d+")
                && coordinate.substring(1).matches("(10|[1-9])");
        if (!isValidCoordinate) {
            throw new RuntimeException("Invalid target. Please enter a coordinate such as D10.");
        }
        //To account for lower case letters in the input
        char row = Character.toUpperCase(coordinate.charAt(0));
        Map.Entry<Integer, Integer> xyCoor =
                new AbstractMap.SimpleEntry<>(Integer.parseInt(coordinate.substring(1)),
                        (row - 'A' + 1));
        return xyCoor;
    }

    private List<List<Cell>> createGrid() {
        final List<List<Cell>> field;
        this.field = new ArrayList<>(NUM_ROWS);

        for (int y = 0; y < NUM_ROWS; y++) {
            List<Cell> row = new ArrayList<>(NUM_COLUMNS);
            for (int x = 0; x < NUM_COLUMNS; x++) {
                row.add(new Cell(x, y, DEFAULT_IDENTIFIER, CELL_NOT_HIT_STATUS));
            }
            this.field.add(row);
        }
        return this.field;
    }



    // Determines the position which the polyomino placing starts from
    private boolean determinePolyominoPosition(Polyomino enemyFort) {
        Random rand = new Random();
        //Try to randomly place the enemy fort on the grid up to 100 times, if it cannot
        //do so, then it is likely that there is no space available
        for (int i = 0; i < 100; i++) {
            //Randomly generate a coordinate from which the fort is generated from on the grid
            int col = rand.nextInt(this.NUM_COLUMNS);
            int row = rand.nextInt(this.NUM_ROWS);
            if (this.placePolyomino(enemyFort, row, col)) {
                return true;
            }
        }
        // Cannot place the opponent
        return false;
    }

    // Determines if the coordinates an opponent's fort takes up is within the grid
    // and does not overlap with another fort
    // Returns true if the fort can be placed on the field, false otherwise
    private boolean canPlaceOpponents(Polyomino enemyFort) {
        // For every coordinate in the fort, determine if it is within the field and not already taken
        for (Map.Entry<Character, Integer> coor : enemyFort.getFortCells()) {
            int x = coor.getValue();
            int y = coor.getKey() - 'A';
            boolean isNotWithinGrid = x > this.NUM_COLUMNS - 1 || x < 0 || y > this.NUM_ROWS - 1 || y < 0;
            boolean coordinateAlreadyTaken = this.field.get(y).get(x).getIdentifier() != this.DEFAULT_IDENTIFIER;
            if (isNotWithinGrid || coordinateAlreadyTaken) {
                return false;
            }
        }
        return true;
    }

    //Creates a fort starting from the initial coordinates (startX, startY) and determines
    //if it can place it there or not
    //Returns true if it can, false otherwise
    private boolean placePolyomino(Polyomino enemyFort, int startY, int startX) {
        //Generate a fort from the initial coordinate
        enemyFort.createFortCells(startY, startX);
        if (this.canPlaceOpponents(enemyFort)) {
            for (Map.Entry<Character, Integer> coor : enemyFort.getFortCells()) {
                int x = coor.getValue();
                int y = coor.getKey() - 'A';
                //Mark every cell on the field at the coordinate of each part of the fort
                //as belonging to it
                Cell cell = this.field.get(y).get(x);
                cell.setPlaced();
                cell.setIdentifier(enemyFort.getIdentifier());
            }
            return true;
        }
        return false;
    }

    private String findCellStatus(int x, int y){
        Cell chosenCell = field.get(x).get(y);
        boolean cellIsAFort = chosenCell.getPlacedStatus();
        boolean cellIsHit = chosenCell.isHit();
        boolean cellUnknown = chosenCell.isFog();
        if(cellUnknown){
            return "fog";
        } else if(cellIsAFort && cellIsHit){
            return "hit";
        } else if (!cellIsAFort && cellIsHit){
            return "miss";
        } else if (cellIsAFort && !cellIsHit){
            return "fort";
        } else {
            return "field";
        }
    }

    public Grid() {
        field = createGrid();
    }

    public void placeOpponent(Polyomino enemyFort) {
        if (!determinePolyominoPosition(enemyFort)) {
            throw new RuntimeException("Try running game again with fewer forts.");
        }
    }

    //Track a hit on the grid to determine if it was a hit or a miss
    // Return 2 if both hit and not previously hit. Return 1 if hit and previously hit. Return 0 if it's a miss.
    public int registerHit(String coordinate) {
        Map.Entry<Integer, Integer> xyCoor = convertCoordinate(coordinate);
        try {
            Cell chosenCell = this.field.get(xyCoor.getValue() - 1)
                    .get(xyCoor.getKey() - 1);
            boolean isFortCell = chosenCell.getIdentifier() != this.DEFAULT_IDENTIFIER;
            boolean previouslyHit = chosenCell.getDamageStatus().equals("X");
            if (isFortCell && !previouslyHit) {
                chosenCell.registerHit(this.FORT_HIT_STATUS);
                return 2;
            } else if (isFortCell && previouslyHit) {
                chosenCell.registerHit(this.FORT_HIT_STATUS);
                return 1;
            } else {
                chosenCell.registerHit(this.FORT_MISS_STATUS);
                return 0;
            }
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }

    //Returns the entire grid based on which type, either the default grid shown during the game
    //or the grid shown at the end of the game or when cheats are enabled
    public String toString(boolean isCheat) {
        String grid = "\nGame board:\n";
        grid += "     ";
        // Add the numbering for each column
        for (int i = 1; i <= this.NUM_COLUMNS; i++) {
            grid += String.format("%3d", i);
        }
        for (int y = 0; y < this.NUM_ROWS; y++) {
            // Add the labels for each row
            grid += "\n";
            grid += "    " + (char) ('A' + y) + "  ";
            for (int x = 0; x < this.NUM_COLUMNS; x++) {
                Cell cell = this.field.get(y).get(x);
                String status;
                //Check what to display for each cell depending on its status (is it hit?, is it a part of a fort?)
                if (isCheat && cell.getPlacedStatus() && cell.getDamageStatus().equals(this.FORT_HIT_STATUS)) {
                    status = String.valueOf(cell.getIdentifier()).toLowerCase();
                } else if (isCheat && cell.getPlacedStatus()) {
                    status = String.valueOf(cell.getIdentifier());
                } else {
                    status = cell.getDamageStatus();
                }
                grid += status + "  ";
            }
        }
        return grid;
    }

    public String[][] retreiveCellStates(){
        String[][] cellStates = new String[NUM_ROWS][NUM_COLUMNS];
        for(int y = 0; y < NUM_COLUMNS; y ++){
            for(int x = 0; x < NUM_ROWS; x++){
                cellStates[x][y] = findCellStatus(x, y);
            }
        }
        return cellStates;
    }


}
