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
import travel_recommendation.service.interfaces.UserService;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {
    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final TravelRepository travelRepository;
    private final SuspiciousEventRepository suspiciousEventRepository;
    private final KieContainer kieContainer;
    private final UserService userService;

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
        kieSession.getAgenda().getAgendaGroup("discount_by_user_rank").setFocus();
        kieSession.fireAllRules();
        kieSession.dispose();

        destinations.sort(Comparator.comparing(Destination::getScore).reversed());

        return destinations;
    }

    @Override
    public String like(LikeDto like) {
        Destination d = destinationRepository.findByCity(like.getDestination());
        Like newLike = likeRepository.save(new Like(userRepository.findByUsername(like.getUser()), d, like.getTime()));

        String retVal = userService.cepRules();

        if (retVal.startsWith("Too many likes for one destination within the hour")) {
            likeRepository.deleteById((newLike.getId()));
            suspiciousEventRepository.save(new SuspiciousEvent(retVal));
            return retVal;
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

        User user = userRepository.findByUsername(travelDto.getUser());
        user.addTravel(travel);
        userService.updateUserRank(user);

        String retVal = userService.cepRules();
        if (!retVal.equals("")) {
            suspiciousEventRepository.save(new SuspiciousEvent(retVal));
        }
    }
}
