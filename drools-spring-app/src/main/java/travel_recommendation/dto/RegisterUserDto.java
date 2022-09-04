package travel_recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import travel_recommendation.model.Location;
import travel_recommendation.model.enums.Status;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private String name;

    private String lastname;

    private String username;

    private String password;

    private String email;

    private LocalDate dateOfBirth;

    private Status status;

    private Location location;
}
