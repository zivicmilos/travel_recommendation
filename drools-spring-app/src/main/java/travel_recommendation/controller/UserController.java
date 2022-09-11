package travel_recommendation.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import travel_recommendation.dto.TravelDto;
import travel_recommendation.model.*;
import travel_recommendation.service.interfaces.UserService;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = {"http://localhost:4200", "https://pantravel.herokuapp.com/"}, maxAge = 3600)
public class UserController {
    private final UserService userService;
    private final Counter getRequests;
    private final Counter postRequests;
    private final Counter allRequests;

    @Autowired
    public UserController(UserService userService, MeterRegistry registry) {
        this.userService = userService;
        this.getRequests = Counter.builder("get_request")
                .description("Number of HTTP GET requests for server endpoints")
                .register(registry);
        this.postRequests = Counter.builder("post_request")
                .description("Number of HTTP POST requests for server endpoints")
                .register(registry);
        this.allRequests = Counter.builder("all_request")
                .description("Number of all HTTP requests for server endpoints")
                .register(registry);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public List<User> getUsers() {
        this.allRequests.increment();
        this.getRequests.increment();
        return userService.getUsers();
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public User getUserByUsername(Principal user) {
        this.allRequests.increment();
        this.getRequests.increment();
        return userService.getUserByUsername(user.getName());
    }

    @RequestMapping(value = "/travel", method = RequestMethod.GET, produces = "application/json")
    public List<Travel> getTravelsByUsername(Principal user) {
        this.allRequests.increment();
        this.getRequests.increment();
        return userService.getTravelsByUsername(user.getName());
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/travel/cancel", method = RequestMethod.POST, consumes = "application/json")
    public void cancelTravel(@RequestBody TravelDto travelDto) {
        this.allRequests.increment();
        this.postRequests.increment();
        userService.cancelTravel(travelDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/suspicious", method = RequestMethod.GET, produces = "application/json")
    public List<SuspiciousEvent> getSuspiciousEvents() {
        this.allRequests.increment();
        this.getRequests.increment();
        return userService.getSuspiciousEvents();
    }
}
