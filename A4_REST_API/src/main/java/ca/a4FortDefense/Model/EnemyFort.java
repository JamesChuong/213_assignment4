package ca.a4FortDefense.Model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The EnemyFort class represents any particular enemy fort with 5 cells, and as such, it
 * needs to implement the Polyomino interface, and is responsible for implementing the methods
 * for creating the fort, keeping track of where it has been hit, and determining how many points
 * it can deal to the player.
 */
public class EnemyFort implements ca.a4FortDefense.Model.Polyomino {

    //When creating a fort, we want to create continuity between the individual cells. To do this,
    //for any cell, there are 4 directions to go to and place a new cell such that continuity is maintained
    //Each are Integer types because we want to randomly choose a direction when creating a fort.
    private final int PLACE_ABOVE = 1;
    private final int PLACE_BELOW = 2;
    private final int PLACE_LEFT = 3;
    private final int PLACE_RIGHT = 4;
    //A list of x-y coordinates which make up the enemy fort
    private List<Map.Entry<Integer, Integer>> fortCells;
    private List<Map.Entry<Integer, Integer>> damagedCells;
    private char identifier;
    private int numUndamagedCells;

    //Creates a random coordinate from a given starting coordinate
    private Map.Entry<Integer, Integer> createRandomCoordinate(Map.Entry<Integer, Integer> startingCoordinate) {
        Map.Entry<Integer, Integer> newCell;
        Random r = new Random();
        int randomDirection = r.nextInt(4) + 1;
        //Randomly select where the coordinate of the new block sits relative to the starting coordinate
        switch (randomDirection) {
            case PLACE_ABOVE:
                newCell = new AbstractMap.SimpleEntry<>(startingCoordinate.getKey(),
                        startingCoordinate.getValue()+1);
                break;
            case PLACE_BELOW:
                newCell = new AbstractMap.SimpleEntry<>(startingCoordinate.getKey(),
                        startingCoordinate.getValue() - 1);
                break;
            case PLACE_LEFT:
                newCell = new AbstractMap.SimpleEntry<>(
                        (startingCoordinate.getKey() - 1), startingCoordinate.getValue());
                break;
            case PLACE_RIGHT:
                newCell = new AbstractMap.SimpleEntry<>(
                        (startingCoordinate.getKey() + 1), startingCoordinate.getValue());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + randomDirection);
        }
        return newCell;
    }

    //Check if a coordinate is within the range of the grid
    private boolean checkCoordinate(Map.Entry<Integer, Integer> coor) {
        boolean isWithinYAxis = coor.getKey() >= 0 && coor.getKey() <= 9;
        boolean isWithinXAxis = coor.getValue() >= 0 && coor.getValue() <= 9;
        return isWithinYAxis && isWithinXAxis;
    }
    public EnemyFort() {
        this.fortCells = new ArrayList<>();
        this.damagedCells = new ArrayList<>();
        this.numUndamagedCells = 5;
    }

    public static EnemyFort createNewPolyomino() {
        EnemyFort newInstance = new EnemyFort();
        return newInstance;
    }

    @Override
    public List<Map.Entry<Integer, Integer>> getFortCells() {
        return fortCells;
    }

    //Given a coordinate, determine if it belongs to this polyomino and if it has already been hit
    @Override
    public void registerHit(int row, int col) {
        Map.Entry<Integer, Integer> newXYCoor = new AbstractMap.SimpleEntry<>(row, col);
        boolean isACell = false;
        for (Map.Entry<Integer, Integer> currentCoordinate : this.fortCells) {
            if (currentCoordinate.getKey() == newXYCoor.getKey()
                    && currentCoordinate.getValue() == newXYCoor.getValue()) {
                isACell = true;
                break;
            }
        }
        if (isACell) {
            this.damagedCells.add(newXYCoor);
            //Remove duplicates, that way no cell is damaged twice
            this.damagedCells = this.damagedCells.stream()
                    .distinct()
                    .collect(Collectors.toList());
            this.numUndamagedCells = this.fortCells.size() - this.damagedCells.size();
        }
    }
    @Override
    public char getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(char identifier) {
        this.identifier = identifier;
    }

    @Override
    public void createFortCells(int x, int y) {
        List<Map.Entry<Integer, Integer>> newFortCells = new ArrayList<>();
        //Create an initial random coordinate from which the rest of the fort is built upon
        Map.Entry<Integer, Integer> startingCoordinate = new AbstractMap.SimpleEntry<>(x, y);
        newFortCells.add(startingCoordinate);
        //Generate 5 connected and non-overlapping coordinates that represent the shape of the fort
        Random r = new Random();
        while (newFortCells.size() < 5) {
            //Generate a random index to randomly choose a coordinate from which the next cell
            //is built from
            int randomIndex = r.nextInt(newFortCells.size());
            Map.Entry<Integer, Integer> newCell = createRandomCoordinate(newFortCells.get(randomIndex));
            boolean isWithinGrid = checkCoordinate(newCell);
            if (isWithinGrid) {
                newFortCells.add(newCell);
                //Remove overlapping cells
                newFortCells = newFortCells.stream()
                        .distinct()
                        .collect(Collectors.toList());
            }
        }
        this.fortCells = newFortCells;
    }

    @Override
    public int getScore() {
        switch (numUndamagedCells) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 5;
            default:
                return 20;
        }
    }
}
