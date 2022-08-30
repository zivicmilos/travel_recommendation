package travel_recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import travel_recommendation.model.enums.TransportationType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelDto {
    private String user;

    private String destination;

    private LocalDate travelDate;

    private TransportationType transportationType;

    private int grade;

    private double cost;
}
