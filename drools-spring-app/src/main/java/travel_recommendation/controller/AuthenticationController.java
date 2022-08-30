package travel_recommendation.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import travel_recommendation.security.model.JwtRequest;
import travel_recommendation.service.interfaces.AuthenticationService;

@RestController
@CrossOrigin
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.login(request));
    }

}
