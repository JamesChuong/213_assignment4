package ca.a4FortDefense.Controllers;
import ca.a4FortDefense.Model.*;
import ca.a4FortDefense.restapi.ApiBoardDTO;
import ca.a4FortDefense.restapi.ApiGameDTO;
import ca.a4FortDefense.restapi.ApiLocationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class GameControllers {

    private final int NUM_OPPONENTS = 5;
    private GamesList allGames = new GamesList();

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class GameNotFoundException extends RuntimeException{
        public GameNotFoundException(String msg){
            super(msg);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class CoordinateOutOfBoundsException extends RuntimeException{
        public CoordinateOutOfBoundsException(String msg){
            super(msg);
        }
    }

    @GetMapping("/games")
    public List<ApiGameDTO> retreiveGameList(){
        List<ApiGameDTO> gameDTOList = new ArrayList<>();
        Iterator<GameManager> gameList = allGames.iterator();
        while(gameList.hasNext()){
            ApiGameDTO newGameDTO = ApiGameDTO.createNewGameDTO(gameList.next());
            gameDTOList.add(newGameDTO);
        }
        return gameDTOList;
    }

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewGame(){
        allGames.addNewGame(NUM_OPPONENTS);
    }

    @GetMapping("/games/{id}")
    public ApiGameDTO getGameByID(@PathVariable("id") int id){
        try{
            GameManager chosenGame = allGames.retreiveGame(id);
            return ApiGameDTO.createNewGameDTO(chosenGame);
        } catch (IndexOutOfBoundsException e){
            throw new GameNotFoundException("Error: Game Not Found");
        }

    }

    @GetMapping("/games/{id}/board")
    public ApiBoardDTO getBoardStatus(@PathVariable("id") int id){
        try{
            GameManager chosenGame = allGames.retreiveGame(id);
            ApiBoardDTO newBoard = chosenGame.retrieveGridStatus();
            return newBoard;
        } catch (IndexOutOfBoundsException e){
            throw new GameNotFoundException("Error: Game not found");
        }
    }

    @PostMapping("/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void trackHit(@PathVariable("id") int id, @RequestBody ApiLocationDTO locationDTO){
        try{
            GameManager chosenGame = allGames.retreiveGame(id);
            chosenGame.receiveCoordinate(locationDTO.row, locationDTO.col);
        } catch (IndexOutOfBoundsException gameNotFound){
            throw new GameNotFoundException("Error: Game not found");
        } catch (RuntimeException outOfBoundsException){
            throw new CoordinateOutOfBoundsException(outOfBoundsException.getMessage());
        }
    }

    @PostMapping("/games/{id}/cheatstate")
    public void activateCheatState(@PathVariable("id") int id, @RequestBody boolean SHOW_ALL){
        GameManager chosenGame = allGames.retreiveGame(id);
    }
}
