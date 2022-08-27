package travel_recommendation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel_recommendation.model.*;
import travel_recommendation.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    //private final Repository repository;

    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final TravelRepository travelRepository;
    private final DestinationService destinationService;

    @Autowired
    public UserService(DestinationRepository destinationRepository, UserRepository userRepository,
                       LikeRepository likeRepository, TravelRepository travelRepository, DestinationService destinationService) {
        this.destinationService = destinationService;
        this.destinationRepository = destinationRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.travelRepository = travelRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

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

    public List<Travel> getTravelsByUsername(String username) {
        return travelRepository.findByUsername(username);
    }

    public void cancelTravel(Travel travel) {
        travelRepository.deleteById(travel.getId());

        destinationService.addDeletedTravel(new DeletedTravel(travel.getUser(), travel.getDestination(), travel.getTravelDate()));
        destinationService.cepRules();
    }
}
