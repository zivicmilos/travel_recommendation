package travel_recommendation.service;

import org.apache.juli.logging.Log;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import travel_recommendation.dto.LikeDto;
import travel_recommendation.model.*;
import travel_recommendation.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DestinationService {

    private static Logger log = LoggerFactory.getLogger(DestinationService.class);
    private final Repository repository;
    private final KieContainer kieContainer;
    private List<String> list = new ArrayList<>();
    private List<DeletedTravel> deletedTravels = new ArrayList<>();
    private List<LoginFailure> loginFailures = new ArrayList<>();

    @Autowired
    public DestinationService(KieContainer kieContainer, Repository repository) {
        log.info("Initialising a new session.");
        this.kieContainer = kieContainer;
        this.repository = repository;
    }

    public List<Destination> getDestinationList(String username, TransportationType transportationType, double budget,
                                                DestinationType destinationType, Weather weather, String continent) {
        KieSession kieSession = kieContainer.newKieSession();
        repository.initRepository();

        List<User> users = repository.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setBudget(budget);
                user.setTransportationType(transportationType);
                user.setDestinationType(destinationType);
                user.setWeather(weather);
                user.setContinent(continent);
            }
            kieSession.insert(user);
        }

        List<Destination> destinations = repository.getDestinations();
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

    public String like(LikeDto like) {
        list = new ArrayList<>();
        Destination d = repository.getDestinationByName(like.getDestination());
        d.addLike(new Like(repository.getUserByUsername(like.getUser()), d, like.getTime()));

        cepRules();

        if (!list.isEmpty() && list.get(0).equals("Too many likes within the hour")) {
            d.getLikes().remove(d.getLikes().size() - 1);
            return list.get(0);
        }
        return "Ok";
    }

    public void reserve(Travel travel) {
        travel.setUser(repository.getUserByUsername(travel.getUser().getUsername()));
        travel.setDestination(repository.getDestinationByName(travel.getDestination().getLocation().getCity()));
        travel.setCost(travel.getDestination().costByTransportType(travel.getTransportationType(), travel.getUser().getLocation()));

        repository.getUserByUsername(travel.getUser().getUsername()).addTravel(travel);

        cepRules();
    }

    public void cepRules() {
        KieSession kieSession = kieContainer.newKieSession();

        for (User u : repository.getUsers()) {
            kieSession.insert(u);
        }

        for (Destination des : repository.getDestinations()) {
            for (Like l : des.getLikes()) {
                kieSession.insert(l);
            }
        }

        for (User u : repository.getUsers()) {
            for (Travel t : u.getTravels()) {
                kieSession.insert(t);
            }
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
