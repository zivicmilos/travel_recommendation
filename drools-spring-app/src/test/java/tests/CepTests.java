package tests;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import travel_recommendation.model.*;

import java.time.LocalDate;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CepTests {
    private KieContainer kContainer;
    public CepTests() {
        KieServices ks = KieServices.Factory.get();
        kContainer = ks
                .newKieContainer(ks.newReleaseId("travel_recommendation", "drools-spring-kjar", "0.0.1-SNAPSHOT"));
        KieScanner kScanner = ks.newKieScanner(kContainer);
        kScanner.start(10_000);
    }

    @Test
    public void testLoginFailing() {
        KieSession kieSession = kContainer.newKieSession();
        User user = new User("Petar", "Petrovic", "pera", "pera", "pera@gmail.com", LocalDate.of(1996, 5, 5), Status.STUDENT,
                new Location("Novi Sad", "Serbia", "Europe", new Coordinates(45.267136, 19.833549)));
        kieSession.insert(user);
        kieSession.insert(new LoginFailure(user));
        kieSession.insert(new LoginFailure(user));
        kieSession.insert(new LoginFailure(user));
        kieSession.insert(new LoginFailure(user));
        kieSession.insert(new LoginFailure(user));
        kieSession.insert(new LoginFailure(user));

        kieSession.getAgenda().getAgendaGroup("check_likes").setFocus();
        int firedRules = kieSession.fireAllRules();

        assertThat(1, equalTo(firedRules));
        System.out.println(user.getLoginBlocked());
        System.out.println(new Date());
        assertEquals(true, user.getLoginBlocked().before(new Date()));

        kieSession.dispose();
    }

}
