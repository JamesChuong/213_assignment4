package ca.a4FortDefense.Controllers;
import ca.a4FortDefense.Model.*;
import ca.a4FortDefense.restapi.ApiBoardDTO;
import ca.a4FortDefense.restapi.ApiGameDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardControllers {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class GameNotFoundException extends RuntimeException{
        public GameNotFoundException(String msg){
            super(msg);
        }
    }


}
