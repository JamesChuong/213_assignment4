package ca.a4FortDefense.restapi;
import ca.a4FortDefense.Model.*;
/**
 * DTO class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiBoardDTO {
    public int boardWidth;
    public int boardHeight;

    // celState[row]col] = {"fog", "hit", "fort", "miss", "field"}
    public String[][] cellStates;

    public ApiBoardDTO(Grid newGrid){
        this.boardWidth = newGrid.getWidth();
        this.boardHeight = newGrid.getHeight();
        cellStates = newGrid.retreiveCellStates();
    }

    public static ApiBoardDTO createApiBoard(Grid newGrid){
        ApiBoardDTO newGameBoard = new ApiBoardDTO(newGrid);
        return newGameBoard;
    }
}
