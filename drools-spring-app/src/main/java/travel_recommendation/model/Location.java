package travel_recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "locations")
public class Location {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "continent")
    private String continent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id", referencedColumnName = "id")
    private Coordinates coordinates;

    public Location(String city, String country, String continent, Coordinates coordinates) {
        this.city = city;
        this.country = country;
        this.continent = continent;
        this.coordinates = coordinates;
    }

    public static double distance(Coordinates coordinates1, Coordinates coordinates2) {
        double lon1 = Math.toRadians(coordinates1.longitude);
        double lon2 = Math.toRadians(coordinates2.longitude);
        double lat1 = Math.toRadians(coordinates1.latitude);
        double lat2 = Math.toRadians(coordinates2.latitude);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;

        return(c * r);
    }
}
