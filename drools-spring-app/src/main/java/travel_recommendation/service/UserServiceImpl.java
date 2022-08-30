package travel_recommendation.service;

import lombok.AllArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import travel_recommendation.dto.TravelDto;
import travel_recommendation.model.*;
import travel_recommendation.model.enums.UserRank;
import travel_recommendation.repository.*;
import travel_recommendation.service.interfaces.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    //private final DestinationServiceImpl destinationService;
    private final KieContainer kieContainer;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Travel> getTravelsByUsername(String username) {
        return travelRepository.findByUsername(username);
    }

    @Override
    public void cancelTravel(TravelDto travelDto) {
        Travel travel = travelRepository.findByParams(travelDto.getUser(), travelDto.getDestination(), travelDto.getTravelDate());
        travelRepository.deleteById(travel.getId());

        /*destinationService.addDeletedTravel(new DeletedTravel(travel.getUser(), travel.getDestination(), travel.getTravelDate()));
        destinationService.cepRules();*/

        User user = userRepository.findByUsername(travelDto.getUser());
        user.getTravels().removeIf(t -> t.getUser().getUsername().equals(travelDto.getUser()) &&
                                        t.getDestination().getLocation().getCity().equals(travelDto.getDestination()) &&
                                        t.getTravelDate().isEqual(travelDto.getTravelDate()));
        updateUserRank(user);
    }

    @Override
    public void updateUserRank(String username) {
        User user = userRepository.findByUsername(username);
        updateUserRank(user);
    }

    @Override
    public void updateUserRank(User user) {
        UserRank oldUserRank = user.getUserRank();

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(user);
        kieSession.getAgenda().getAgendaGroup("update_user_rank").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();

        if (user.getUserRank() != oldUserRank) {
            userRepository.save(user);
        }
    }

}
