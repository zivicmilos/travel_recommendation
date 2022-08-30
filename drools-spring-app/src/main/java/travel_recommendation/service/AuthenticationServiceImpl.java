package travel_recommendation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import travel_recommendation.security.TokenManager;
import travel_recommendation.security.model.JwtRequest;
import travel_recommendation.security.model.JwtResponse;
import travel_recommendation.service.interfaces.AuthenticationService;
import travel_recommendation.service.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;
    private final UserService userService;

    @Override
    public JwtResponse login(JwtRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwtToken = tokenManager.generateJwtToken(userDetails);

        userService.updateUserRank(request.getUsername());

        return new JwtResponse(jwtToken);
    }

}
