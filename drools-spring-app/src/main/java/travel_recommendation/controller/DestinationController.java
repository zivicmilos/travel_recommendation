package travel_recommendation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel_recommendation.dto.LikeDto;
import travel_recommendation.model.*;
import travel_recommendation.service.DestinationService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
public class DestinationController {
    private static Logger log = LoggerFactory.getLogger(DestinationController.class);

    private final DestinationService destinationService;

    @Autowired
    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @RequestMapping(value = "/destination", method = RequestMethod.GET, produces = "application/json")
    public List<Destination> getParameters(@RequestParam(required = true) String username,
                                           @RequestParam(required = true) TransportationType transportationType,
                                           @RequestParam(required = true) double budget,
                                           @RequestParam(required = true) DestinationType destinationType,
                                           @RequestParam(required = true) Weather weather,
                                           @RequestParam(required = true) String continent) {

        return destinationService.getDestinationList(username, transportationType, budget, destinationType, weather, continent);
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST, consumes = "application/json")
    public String like(@RequestBody LikeDto like) {
        String message = destinationService.like(like);
        return "\"" + message + "\"";
    }

    @RequestMapping(value = "/reservation", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> reserve(@RequestBody Travel travel) {
        this.destinationService.reserve(travel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
