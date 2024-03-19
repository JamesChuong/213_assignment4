package ca.a4FortDefense.Model;

import ca.a4FortDefense.restapi.ApiGameDTO;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GamesList implements Iterable<GameManager> {

    private int currentGameNumber = 1;
    private List<GameManager> gamesStored = new ArrayList<>();

    public void addNewGame(int numOpponents){
        GameManager newGame = new GameManager(numOpponents, currentGameNumber);
        currentGameNumber++;
        gamesStored.add(newGame);
    }

    public GameManager retreiveGame(int gameNumber){
        return gamesStored.get(gameNumber-1);
    }

    @Override
    public Iterator<GameManager> iterator() {
        return gamesStored.iterator();
    }
}
