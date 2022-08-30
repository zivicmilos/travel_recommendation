package travel_recommendation.controller;

import lombok.AllArgsConstructor;
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

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/destination")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class DestinationController {
    private final DestinationService destinationService;

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Destination> getParameters(@RequestParam() String username,
                                           @RequestParam() TransportationType transportationType,
                                           @RequestParam() double budget,
                                           @RequestParam() DestinationType destinationType,
                                           @RequestParam() Weather weather,
                                           @RequestParam() String continent) {

        return destinationService.getDestinationList(username, transportationType, budget, destinationType, weather, continent);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/like", method = RequestMethod.POST, consumes = "application/json")
    public String like(@RequestBody LikeDto like) {
        String message = destinationService.like(like);
        return "\"" + message + "\"";
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @RequestMapping(value = "/reservation", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> reserve(@RequestBody TravelDto travelDto) {
        this.destinationService.reserve(travelDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
