package travel_recommendation.service.interfaces;

import travel_recommendation.security.model.JwtRequest;
import travel_recommendation.security.model.JwtResponse;

public interface AuthenticationService {
    JwtResponse login(JwtRequest request) throws Exception;
}
