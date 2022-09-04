package travel_recommendation.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travel_recommendation.dto.RegisterUserDto;
import travel_recommendation.security.model.JwtRequest;
import travel_recommendation.service.interfaces.AuthenticationService;

@RestController
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final Counter postRequests;
    private final Counter allRequests;

    public AuthenticationController(AuthenticationService authenticationService, MeterRegistry registry) {
        this.authenticationService = authenticationService;
        this.postRequests = Counter.builder("post_request")
                .description("Number of HTTP POST requests for server endpoints")
                .register(registry);
        this.allRequests = Counter.builder("all_request")
                .description("Number of all HTTP requests for server endpoints")
                .register(registry);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest request) throws Exception {
        this.allRequests.increment();
        this.postRequests.increment();
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterUserDto registerUserDto){
        this.allRequests.increment();
        this.postRequests.increment();
        authenticationService.register(registerUserDto);
    }

}
