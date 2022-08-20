package travel_recommendation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel_recommendation.model.*;
import travel_recommendation.service.DestinationService;
import travel_recommendation.service.UserService;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class UserController {
    private static Logger log = LoggerFactory.getLogger(DestinationController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/user/travel", method = RequestMethod.GET, produces = "application/json")
    public List<Travel> getTravelsByUsername(@PathParam("username") String username) {
        List<Travel> l = userService.getTravelsByUsername(username);
        return l;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody User user) {
        return new ResponseEntity<>(userService.login(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/travel/cancel", method = RequestMethod.POST, consumes = "application/json")
    public void cancelTravel(@RequestBody Travel travel) {
        userService.cancelTravel(travel);
    }
}
