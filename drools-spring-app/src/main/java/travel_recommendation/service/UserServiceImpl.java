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

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final LikeRepository likeRepository;
    private final DeletedTravelRepository deletedTravelRepository;
    private final LoginFailureRepository loginFailureRepository;
    private final SuspiciousEventRepository suspiciousEventRepository;
    private final KieContainer kieContainer;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(user);
        kieSession.getAgenda().getAgendaGroup("user_activity_rules").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
        return user;
    }

    @Override
    public List<Travel> getTravelsByUsername(String username) {
        return travelRepository.findByUsername(username);
    }

    @Override
    public void cancelTravel(TravelDto travelDto) {
        Travel travel = travelRepository.findByParams(travelDto.getUser(), travelDto.getDestination(), travelDto.getTravelDate());
        travelRepository.deleteById(travel.getId());

        deletedTravelRepository.save(new DeletedTravel(travel.getUser(), travel.getDestination(), travel.getTravelDate()));
        String retVal = this.cepRules();

        if (!retVal.equals("")) {
            suspiciousEventRepository.save(new SuspiciousEvent(retVal));
        }

        User user = userRepository.findByUsername(travelDto.getUser());
        user.getTravels().removeIf(t -> t.getUser().getUsername().equals(travelDto.getUser()) &&
                                        t.getDestination().getLocation().getCity().equals(travelDto.getDestination()) &&
                                        t.getTravelDate().isEqual(travelDto.getTravelDate()));
        this.updateUserRank(user);
    }

    @Override
    public void updateUserRank(String username) {
        User user = userRepository.findByUsername(username);
        this.updateUserRank(user);
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

    @Override
    public String cepRules() {
        KieSession kieSession = kieContainer.newKieSession();

        for (User u : userRepository.findAll()) {
            kieSession.insert(u);
        }

        for (Like l : likeRepository.findAll()) {
            kieSession.insert(l);
        }

        for (Travel t : travelRepository.findAll()) {
            kieSession.insert(t);
        }

        for (DeletedTravel dt : deletedTravelRepository.findAll()) {
            kieSession.insert(dt);
        }

        for (LoginFailure lf : loginFailureRepository.findAll()) {
            kieSession.insert(lf);
        }

        List<String> list = new ArrayList<>();
        kieSession.setGlobal( "myGlobalList", list );

        kieSession.getAgenda().getAgendaGroup("check_likes").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();

        if (list.isEmpty())
            return "";
        return list.get(0);
    }

    @Override
    public List<SuspiciousEvent> getSuspiciousEvents() {
        return suspiciousEventRepository.findAll();
    }

}
