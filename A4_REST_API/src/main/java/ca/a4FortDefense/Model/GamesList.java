package ca.a4FortDefense.Model;

import ca.a4FortDefense.restapi.ApiGameDTO;

import java.util.ArrayList;
import java.util.List;

public class GamesList {

    private final int NUM_OPPONENTS = 5;
    private List<GameManager> gamesStored = new ArrayList<>();

    public void addNewGame(){
        GameManager newGame = new GameManager(NUM_OPPONENTS);
        gamesStored.add(newGame);
    }

    public List<GameManager> getAllGames(){
        return this.gamesStored;
    }

    public GameManager retreiveGame(int gameNumber){
        return gamesStored.get(gameNumber-1);
    }
}
