package travel_recommendation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel_recommendation.dto.LikeDto;
import travel_recommendation.model.*;
import travel_recommendation.service.interfaces.DestinationService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/destination")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class DestinationController {
    //private static Logger log = LoggerFactory.getLogger(DestinationController.class);

    private final DestinationService destinationService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Destination> getParameters(@RequestParam() String username,
                                           @RequestParam() TransportationType transportationType,
                                           @RequestParam() double budget,
                                           @RequestParam() DestinationType destinationType,
                                           @RequestParam() Weather weather,
                                           @RequestParam() String continent) {

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
