package travel_recommendation.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
    public Object login(User user) {
        List<User> users = userRepository.findAll();
        User retVal = users.stream().filter(u->u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())).findFirst().orElse(null);
        if (retVal != null) {
            return retVal;
        }
        else if (users.stream().filter(u->u.getUsername().equals(user.getUsername())).findFirst().orElse(null) != null) {
            this.destinationService.addLoginFailure(new LoginFailure(users.stream().filter(u->u.getUsername().equals(user.getUsername())).findFirst().orElse(null)));
            this.destinationService.cepRules();
            return "\"" + "Wrong password!" + "\"";
        }
        else {
            return "\"" + "User with this username does not exists!" + "\"";
        }
    }

    @Override
    public List<Travel> getTravelsByUsername(String username) {
        return travelRepository.findByUsername(username);
    }

    @Override
    public void cancelTravel(Travel travel) {
        travelRepository.deleteById(travel.getId());

        destinationService.addDeletedTravel(new DeletedTravel(travel.getUser(), travel.getDestination(), travel.getTravelDate()));
        destinationService.cepRules();
    }
}
