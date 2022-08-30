package travel_recommendation.service;

import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import travel_recommendation.dto.LikeDto;
import travel_recommendation.dto.TravelDto;
import travel_recommendation.model.*;
import travel_recommendation.model.enums.DestinationType;
import travel_recommendation.model.enums.TransportationType;
import travel_recommendation.model.enums.Weather;
import travel_recommendation.repository.*;
import travel_recommendation.service.interfaces.DestinationService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {
    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final TravelRepository travelRepository;
    private final KieContainer kieContainer;
    private List<String> list = new ArrayList<>();
    private List<DeletedTravel> deletedTravels = new ArrayList<>();
    private List<LoginFailure> loginFailures = new ArrayList<>();

    @Override
    public List<Destination> getDestinationList(String username, TransportationType transportationType, double budget,
                                                DestinationType destinationType, Weather weather, String continent) {
        KieSession kieSession = kieContainer.newKieSession();

        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setUserParameters(new UserParameters(transportationType, destinationType, weather, continent, budget));
            }
            else {
                user.setUserParameters(new UserParameters());
            }
            kieSession.insert(user);
        }

        List<Destination> destinations = destinationRepository.findAll();
        for (Destination d : destinations) {
            d.setUsername(username);
            kieSession.insert(d);
        }

        kieSession.getAgenda().getAgendaGroup("add-transportation-types").setFocus();
        kieSession.fireAllRules();
        kieSession.getAgenda().getAgendaGroup("grade-destinations").setFocus();
        kieSession.fireAllRules();
        kieSession.getAgenda().getAgendaGroup("grade-destinations-budget").setFocus();
        kieSession.fireAllRules();
        kieSession.getAgenda().getAgendaGroup("grade-destinations-combined-rules").setFocus();
        kieSession.fireAllRules();
        kieSession.getAgenda().getAgendaGroup("transportation_related_rules").setFocus();
        kieSession.fireAllRules();
        kieSession.getAgenda().getAgendaGroup("user_activity_rules").setFocus();
        kieSession.fireAllRules();
        kieSession.getAgenda().getAgendaGroup("update_user_rank").setFocus();
        kieSession.fireAllRules();
        kieSession.getAgenda().getAgendaGroup("discount_by_user_rank").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();

        destinations.sort(Comparator.comparing(Destination::getScore).reversed());

        return destinations;
    }

    @Override
    public String like(LikeDto like) {
        list = new ArrayList<>();
        Destination d = destinationRepository.findByCity(like.getDestination());
        Like newLike = likeRepository.save(new Like(userRepository.findByUsername(like.getUser()), d, like.getTime()));

        cepRules();

        if (!list.isEmpty() && list.get(0).equals("Too many likes within the hour")) {
            likeRepository.deleteById((newLike.getId()));
            return list.get(0);
        }
        return "Ok";
    }

    @Override
    public void reserve(TravelDto travelDto) {
        Travel travel = new Travel();
        travel.setUser(userRepository.findByUsername(travelDto.getUser()));
        travel.setDestination(destinationRepository.findByCity(travelDto.getDestination()));
        travel.setTransportationType(travelDto.getTransportationType());
        travel.setGrade(travelDto.getGrade());
        travel.setTravelDate(travelDto.getTravelDate());
        travel.setCost(travel.getDestination().costByTransportType(travel.getTransportationType(), travel.getUser().getLocation()));

        travelRepository.save(travel);

        cepRules();
    }

    @Override
    public void cepRules() {
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

        for (DeletedTravel dt : this.deletedTravels) {
            kieSession.insert(dt);
        }

        for (LoginFailure lf : this.loginFailures) {
            kieSession.insert(lf);
        }

        kieSession.setGlobal( "myGlobalList", list );

        kieSession.getAgenda().getAgendaGroup("check_likes").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    public void addDeletedTravel(DeletedTravel deletedTravel) {
        this.deletedTravels.add(deletedTravel);
    }

    public List<DeletedTravel> getDeletedTravels() {
        return this.deletedTravels;
    }

    public void setDeletedTravels(List<DeletedTravel> deletedTravels) {
        this.deletedTravels = deletedTravels;
    }

    public void addLoginFailure(LoginFailure loginFailure) {
        this.loginFailures.add(loginFailure);
    }
    public List<LoginFailure> getLoginFailures() {
        return loginFailures;
    }

    public void setLoginFailures(List<LoginFailure> loginFailures) {
        this.loginFailures = loginFailures;
    }
}
