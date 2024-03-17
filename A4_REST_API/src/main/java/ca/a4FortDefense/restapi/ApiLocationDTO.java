package ca.a4FortDefense.restapi;

/**
 * DTO class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiLocationDTO {
    public int row;
    public int col;

    public ApiLocationDTO(int row, int col){
        this.row = row;
        this.col = col;
    }

    public static ApiLocationDTO createApiLocationDTO(int row, int col){
        ApiLocationDTO newLocation = new ApiLocationDTO(row, col);
        return newLocation;
    }
}
