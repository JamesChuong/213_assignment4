package ca.a4FortDefense.Model;

import ca.a4FortDefense.restapi.ApiBoardDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The GameManager class serves as a "facade" of the entire game from the users perspective and from
 * the perspective of the TextUI class. Its acts as a middleman between the TextUI, Polyomino, and
 * Grid class, communicating information between these classes so that game functions normally.
 * When given an input from the user in TextUI, it sends it to both the Grid and corresponding
 * Polyomino object. When a polyomino object is created, the GameManager class sends the coordinates
 * it occupies to the Grid object.
 */
public class GameManager {

    private final int MAX_OPPONENT_SCORE = 2500;
    private final int MAX_PLAYER_SCORE;
    private final Grid gameField;
    private final List<EnemyFort> opponentList;
    private int totalOpponentScore = 0;
    private int playerScore = 0;
    private int gameNumber = 0;

    //The current round that is being played
    private int currentRound = 0;

    //The score each opponent has collected for each round
    private List<int[]> opponentScoresPerRound = new ArrayList<>();

    private void updateTotalScores() {
        int numOpponents = opponentList.size();
        int[] endOfRoundScores = new int[numOpponents];
        for (int i = 0; i < numOpponents; i++) {
            EnemyFort currentOpponent = opponentList.get(i);
            totalOpponentScore += currentOpponent.getScore();
            endOfRoundScores[i] = currentOpponent.getScore();
        }
        opponentScoresPerRound.add(endOfRoundScores);
    }

    private void updateOpponentList(){

    }
    public GameManager(int numOpponents, int gameNumber) {
        this.gameNumber = gameNumber;
        this.MAX_PLAYER_SCORE = 5 * numOpponents; // There are 5 cells for each opponent's fort
        this.gameField = new Grid();
        this.opponentScoresPerRound.add(null); //On round "0", no opponents have scored yet
        // Initialize the list of opponents
        this.opponentList = Stream.generate(EnemyFort::createNewPolyomino)
                .limit(numOpponents)
                .collect(Collectors.toList());
        // Set the identifier for each polyomino, depending on the size of opponentList
        char identifier = 'A';
        for (EnemyFort polyomino : this.opponentList) {
            polyomino.setIdentifier(identifier++);
        }
        // If gameField cannot place all forts on the grid, then it throws an exception
        for (EnemyFort currentOpponent : this.opponentList) {
            gameField.placeOpponent(currentOpponent);
        }
        currentRound = 1;
    }

    public int getMaxScore() {
        return this.MAX_OPPONENT_SCORE;
    }

    // Checks whether the game has ended or not, does not specify who wins
    public boolean checkStateOfGame() {
        return this.totalOpponentScore >= this.MAX_OPPONENT_SCORE
                || this.playerScore >= this.MAX_PLAYER_SCORE;
    }

    // Determines who wins once the game ends
    public boolean checkIfOpponentsWin() {
        if (this.totalOpponentScore >= this.MAX_OPPONENT_SCORE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfPlayerWins(){
        if(this.playerScore >= this.MAX_PLAYER_SCORE){
            return true;
        } else {
            return false;
        }
    }

    public ApiBoardDTO retrieveGridStatus() {
        return ApiBoardDTO.createApiBoard(gameField);
    }

    public String retrieveCheatGrid() {
        return this.gameField.toString(true);
    }

    // Return the total score for the opponents after one round
    public int getRoundOpponentScore() {
        return this.totalOpponentScore;
    }

    public String receiveCoordinate(int row, int col) {
        String hitStatus;
        int cellIsHit = this.gameField.registerHit(row, col);
        boolean cellHitOnce = cellIsHit == 2;
        boolean hitIsMiss = cellIsHit == 0;
        if (hitIsMiss) {
            hitStatus = "Miss!";
        } else if (cellHitOnce) {
            this.playerScore++;
            hitStatus = "HIT!";
        } else {    //the cell at this coordinate was already hit, so the player does not get any score
            hitStatus = "HIT!";
        }
        //Since no fort shares a common cell, only the polyomino with the
        //coordinate will be affected
        this.opponentList.stream()
                .forEach(opponent -> opponent.registerHit(row, col));
        this.updateTotalScores();
        currentRound++;
        return hitStatus;
    }

    //Return a log of every opponent's hits during the round
    public String getOpponentHits() {
        String OpponentsTurns = "";
        for (int i = 0; i < this.opponentList.size(); i++) {
            EnemyFort currentOpponent = this.opponentList.get(i);
            if(currentOpponent.getScore() != 0){
                OpponentsTurns += String.format("Opponent #%d of %d shot you for %d points!\n",
                        i + 1, this.opponentList.size(), currentOpponent.getScore());
            }
        }
        return OpponentsTurns;
    }

    public int getGameNumber(){
        return gameNumber;
    }

    public long getNumActiveOpponents(){
        return opponentList.stream()
                .filter(enemyFort -> enemyFort.getScore() != 0)
                .count();
    }

    public String[][] receiveCellsStatus(){
        return gameField.retreiveCellStates();
    }

    public int getCurrentRound(){
        return currentRound;
    }

    public int[] receiveRoundOpponentScores(int roundNumber){
        return opponentScoresPerRound.get(roundNumber-1);
    }

}
