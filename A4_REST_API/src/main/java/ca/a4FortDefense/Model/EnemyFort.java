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
    private List<Map.Entry<Character, Integer>> fortCells;
    private List<Map.Entry<Character, Integer>> damagedCells;
    private char identifier;
    private int numUndamagedCells;

    //Creates a random coordinate from a given starting coordinate
    private Map.Entry<Character, Integer> createRandomCoordinate(Map.Entry<Character, Integer> startingCoordinate) {
        Map.Entry<Character, Integer> newCell;
        Random r = new Random();
        int randomDirection = r.nextInt(4) + 1;
        //Randomly select where the coordinate of the new block sits relative to the starting coordinate
        switch (randomDirection) {
            case PLACE_ABOVE:
                newCell = new AbstractMap.SimpleEntry<>(startingCoordinate.getKey(),
                        startingCoordinate.getValue() + 1);
                break;
            case PLACE_BELOW:
                newCell = new AbstractMap.SimpleEntry<>(startingCoordinate.getKey(),
                        startingCoordinate.getValue() - 1);
                break;
            case PLACE_LEFT:
                newCell = new AbstractMap.SimpleEntry<>(
                        (char) (startingCoordinate.getKey() - 1), startingCoordinate.getValue());
                break;
            case PLACE_RIGHT:
                newCell = new AbstractMap.SimpleEntry<>(
                        (char) (startingCoordinate.getKey() + 1), startingCoordinate.getValue());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + randomDirection);
        }
        return newCell;
    }

    //Check if a coordinate is within the range of the grid
    private boolean checkCoordinate(Map.Entry<Character, Integer> coor) {
        boolean isWithinYAxis = coor.getKey() >= 'A' && coor.getKey() <= 'J';
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
    public List<Map.Entry<Character, Integer>> getFortCells() {
        return fortCells;
    }

    //Given a coordinate, determine if it belongs to this polyomino and if it has already been hit
    @Override
    public void registerHit(String coordinate) {
        String xCoor = coordinate.substring(1);
        char yCoor = Character.toUpperCase(coordinate.charAt(0));
        Map.Entry<Character, Integer> newXYCoor = new AbstractMap.SimpleEntry<>(yCoor, Integer.parseInt(xCoor) - 1);
        boolean isACell = false;
        for (Map.Entry<Character, Integer> currentCoordinate : this.fortCells) {
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
    public void createFortCells(int y, int x) {
        List<Map.Entry<Character, Integer>> newFortCells = new ArrayList<>();
        //Create an initial random coordinate from which the rest of the fort is built upon
        char yCoor = (char) ('A' + y);     //Randomly generate a value for the y-coordinate between A-J
        int xCoor = x;  //Randomly generate a value of the x-coordinate between 0-9
        Map.Entry<Character, Integer> startingCoordinate = new AbstractMap.SimpleEntry<>(yCoor, xCoor);
        newFortCells.add(startingCoordinate);
        //Generate 5 connected and non-overlapping coordinates that represent the shape of the fort
        Random r = new Random();
        while (newFortCells.size() < 5) {
            //Generate a random index to randomly choose a coordinate from which the next cell
            //is built from
            int randomIndex = r.nextInt(newFortCells.size());
            Map.Entry<Character, Integer> newCell = createRandomCoordinate(newFortCells.get(randomIndex));
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
