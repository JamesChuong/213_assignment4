package ca.a4FortDefense.restapi;

import ca.a4FortDefense.Model.GameManager;

/**
 * DTO class for the REST API to define object structures required by the front-end.
 * HINT: Create static factory methods (or constructors) which help create this object
 *       from the data stored in the model, or required by the model.
 */
public class ApiGameDTO {
    public int gameNumber;
    public boolean isGameWon;
    public boolean isGameLost;
    public int opponentPoints;
    public long numActiveOpponentForts;

    // Amount of points that the opponents scored on the last time they fired.
    // If opponents have not yet fired, then it should be an empty array (0 size).
    public int[] lastOpponentPoints;

    public ApiGameDTO(GameManager newGame){
        this.gameNumber = newGame.getGameNumber();
        this.isGameWon = newGame.checkIfPlayerWins();
        this.isGameLost = newGame.checkIfOpponentsWin();
        this.opponentPoints = newGame.getRoundOpponentScore();
        this.numActiveOpponentForts = newGame.getNumActiveOpponents();
        int previousRound = newGame.getCurrentRound()-1;
        this.lastOpponentPoints = newGame.receiveRoundOpponentScores(previousRound);
    }

    public static ApiGameDTO createNewGameDTO(GameManager newGame){
        ApiGameDTO newGameDTO =  new ApiGameDTO(newGame);
        return newGameDTO;
    }
}
