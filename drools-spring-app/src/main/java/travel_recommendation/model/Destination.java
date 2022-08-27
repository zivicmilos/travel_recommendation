package travel_recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "destinations")
public class Destination {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Weather weather;

    @ElementCollection(targetClass = DestinationType.class)
    @CollectionTable(name = "destination_types", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "destination_type", nullable = false)
    private List<DestinationType> destinationTypes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ElementCollection(targetClass = TransportationType.class)
    @CollectionTable(name = "transportation_types", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "transportation_type", nullable = false)
    private List<TransportationType> transportationTypes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "destination")
    private List<Like> likes;

    @Transient
    private String username;

    @Transient
    private double score;

    @Transient
    private TransportationType recommendedTransportationType;

    @Transient
    private double cost;

    @Transient
    private double grade;

    public Destination(Weather weather, List<DestinationType> destinationTypes, Location location, List<TransportationType> transportationTypes) {
        this.weather = weather;
        this.destinationTypes = destinationTypes;
        this.location = location;
        this.transportationTypes = transportationTypes;
        this.likes = new ArrayList<>();
        this.username = "";
        this.score = 0;
        this.recommendedTransportationType = TransportationType.NA;
        this.cost = 0;
        this.grade = 0;
    }

    public void addTransportationType(TransportationType transportationType) {
        this.transportationTypes.add(transportationType);
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }

    public double costByTransportType(TransportationType transportationType, Location userLocation) {
        double distance = Location.distance(userLocation.getCoordinates(), this.location.getCoordinates());
        double cost = 0;
        switch (transportationType) {
            case CAR:
                return (distance * 0.2);
            case BUS:
                return (distance * 0.12);
            case TRAIN:
                return (distance * 0.08);
            case PLANE:
                return (50 + (distance * 0.11));
            default:
                return cost;
        }
    }

}
