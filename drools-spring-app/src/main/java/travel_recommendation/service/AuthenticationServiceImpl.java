package travel_recommendation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import travel_recommendation.dto.RegisterUserDto;
import travel_recommendation.model.Role;
import travel_recommendation.model.User;
import travel_recommendation.repository.RoleRepository;
import travel_recommendation.repository.UserRepository;
import travel_recommendation.security.TokenManager;
import travel_recommendation.security.model.JwtRequest;
import travel_recommendation.security.model.JwtResponse;
import travel_recommendation.service.interfaces.AuthenticationService;
import travel_recommendation.service.interfaces.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtUserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public void register(RegisterUserDto registerUserDto){
        User user = new User(registerUserDto.getName(), registerUserDto.getLastname(), registerUserDto.getUsername(),
                passwordEncoder.encode(registerUserDto.getPassword()), registerUserDto.getEmail(), registerUserDto.getDateOfBirth(),
                registerUserDto.getStatus(), registerUserDto.getLocation());

        
        user.setRoles(roleRepository.findByName("ROLE_CLIENT"));
        userRepository.save(user);
    }

}
