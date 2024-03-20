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

    //Error handlers
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static class InvalidRequestException extends RuntimeException{
        public InvalidRequestException(String msg){
            super(msg);
        }
    }

    @GetMapping("/api/about")
    String returnName(){
        return "James Chuong";
    }

    //Game controllers

    @GetMapping("/api/games")
    public List<ApiGameDTO> retreiveGameList(){
        System.out.println("Loading game");
        List<ApiGameDTO> gameDTOList = new ArrayList<>();
        Iterator<GameManager> gameList = allGames.iterator();
        while(gameList.hasNext()){
            ApiGameDTO newGameDTO = ApiGameDTO.createNewGameDTO(gameList.next());
            gameDTOList.add(newGameDTO);
        }
        return gameDTOList;
    }

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameDTO addNewGame(){
        System.out.println("Adding game");
        allGames.addNewGame(NUM_OPPONENTS);
        return new ApiGameDTO(allGames.retreiveNewestGame());
    }

    @GetMapping("/api/games/{id}")
    public ApiGameDTO getGameByID(@PathVariable("id") int id){
        try{
            System.out.println("Displaying game");
            GameManager chosenGame = allGames.retreiveGame(id);
            return ApiGameDTO.createNewGameDTO(chosenGame);
        } catch (IndexOutOfBoundsException e){
            throw new GameNotFoundException("Error: Game Not Found");
        }
    }

    //Board controllers

    @GetMapping("/api/games/{id}/board")
    public ApiBoardDTO getBoardStatus(@PathVariable("id") int id){
        try{
            GameManager chosenGame = allGames.retreiveGame(id);
            ApiBoardDTO newBoard = new ApiBoardDTO(chosenGame.retreiveGrid());
            System.out.println("Displaying board");
            return newBoard;
        } catch (IndexOutOfBoundsException e){
            throw new GameNotFoundException("Error: Game not found");
        }
    }

    //Move controllers

    @PostMapping("/api/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void trackHit(@PathVariable("id") int id, @RequestBody ApiLocationDTO locationDTO){
        try{
            GameManager chosenGame = allGames.retreiveGame(id);
            chosenGame.receiveCoordinate(locationDTO.row, locationDTO.col);
        } catch (IndexOutOfBoundsException gameNotFound){
            throw new GameNotFoundException("Error: Game not found");
        } catch (RuntimeException outOfBoundsException){
            throw new CoordinateOutOfBoundsException("Error: Invalid coordinate");
        }
    }

    //Cheatstate controller

    @PostMapping("/api/games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void activateCheatState(@PathVariable("id") int id, @RequestBody String SHOW_ALL){
        try{
            allGames.retreiveGame(id).activateCheats(SHOW_ALL);
            System.out.println("Activating cheats");
        } catch (IndexOutOfBoundsException e){
            throw new GameNotFoundException("Error: Game not found");
        } catch (RuntimeException InvalidCheatString){
            throw new InvalidRequestException("Error: Invalid cheat string");
        }

    }
}
