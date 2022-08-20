package travel_recommendation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel_recommendation.model.*;
import travel_recommendation.repository.Repository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final Repository repository;
    private final DestinationService destinationService;

    @Autowired
    public UserService(Repository repository, DestinationService destinationService) {
        this.repository = repository;
        this.destinationService = destinationService;
    }

    public List<User> getUsers() {
        return repository.getUsers();
    }

    public Object login(User user) {
        List<User> users = repository.getUsers();
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
        return repository.getUserByUsername(username).getTravels();
    }

    public void cancelTravel(Travel travel) {
        List<Travel> travels = repository.getUserByUsername(travel.getDestination().getUsername()).getTravels();
        travels.removeIf(t -> t.getDestination().getLocation().getCity().equals(travel.getDestination().getLocation().getCity()) && t.getTravelDate().equals(travel.getTravelDate()));

        destinationService.addDeletedTravel(new DeletedTravel( repository.getUserByUsername(travel.getDestination().getUsername()), travel.getDestination(), travel.getTravelDate()));
        destinationService.cepRules();
    }
}
