package travel_recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import travel_recommendation.model.enums.DestinationType;
import travel_recommendation.model.enums.TransportationType;
import travel_recommendation.model.enums.Weather;

@Data
@AllArgsConstructor
public class UserParameters {
    private TransportationType transportationType;
    private DestinationType destinationType;
    private Weather weather;
    private String continent;
    private double budget;

    public UserParameters() {
        this.destinationType = DestinationType.NA;
        this.transportationType = TransportationType.NA;
        this.weather = Weather.NA;
        this.continent = "";
        this.budget = 0;
    }
}