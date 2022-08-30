package travel_recommendation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import travel_recommendation.dto.TravelDto;
import travel_recommendation.model.*;
import travel_recommendation.repository.*;
import travel_recommendation.service.interfaces.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final DestinationServiceImpl destinationService;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Travel> getTravelsByUsername(String username) {
        return travelRepository.findByUsername(username);
    }

    @Override
    public void cancelTravel(TravelDto travelDto) {
        Travel travel = travelRepository.findByParams(travelDto.getUser(), travelDto.getDestination(), travelDto.getTravelDate());
        travelRepository.deleteById(travel.getId());

        destinationService.addDeletedTravel(new DeletedTravel(travel.getUser(), travel.getDestination(), travel.getTravelDate()));
        destinationService.cepRules();
    }
}
