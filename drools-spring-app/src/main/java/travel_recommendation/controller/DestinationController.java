package travel_recommendation.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import travel_recommendation.dto.LikeDto;
import travel_recommendation.dto.TravelDto;
import travel_recommendation.model.*;
import travel_recommendation.model.enums.DestinationType;
import travel_recommendation.model.enums.TransportationType;
import travel_recommendation.model.enums.Weather;
import travel_recommendation.service.interfaces.DestinationService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/destination")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class DestinationController {
    private final DestinationService destinationService;

    private final Counter getRequests;
    private final Counter postRequests;
    private final Counter allRequests;

    @Autowired
    public DestinationController(DestinationService destinationService, MeterRegistry registry) {
        this.destinationService = destinationService;
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

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Destination> getParameters(@RequestParam() TransportationType transportationType,
                                           @RequestParam() double budget,
                                           @RequestParam() DestinationType destinationType,
                                           @RequestParam() Weather weather,
                                           @RequestParam() String continent,
                                           Principal user) {

        this.allRequests.increment();
        this.getRequests.increment();
        return destinationService.getDestinationList(user.getName(), transportationType, budget, destinationType, weather, continent);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/like", method = RequestMethod.POST, consumes = "application/json")
    public String like(@RequestBody LikeDto like) {
        this.allRequests.increment();
        this.postRequests.increment();
        String message = destinationService.like(like);
        return "\"" + message + "\"";
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/reservation", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> reserve(@RequestBody TravelDto travelDto) {
        this.allRequests.increment();
        this.postRequests.increment();
        this.destinationService.reserve(travelDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
