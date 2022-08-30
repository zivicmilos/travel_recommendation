package travel_recommendation.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import travel_recommendation.dto.TravelDto;
import travel_recommendation.model.*;
import travel_recommendation.service.interfaces.UserService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {
    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/travel", method = RequestMethod.GET, produces = "application/json")
    public List<Travel> getTravelsByUsername(@PathParam("username") String username) {
        return userService.getTravelsByUsername(username);
    }

    @RequestMapping(value = "/travel/cancel", method = RequestMethod.POST, consumes = "application/json")
    public void cancelTravel(@RequestBody TravelDto travelDto) {
        userService.cancelTravel(travelDto);
    }
}
