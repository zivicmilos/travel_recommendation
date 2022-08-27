package travel_recommendation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "status")
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Column(name = "user_rank")
    private UserRank userRank;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    //@JsonManagedReference
    private List<Travel> travels;

    @Transient
    private UserParameters userParameters = new UserParameters();

    @Transient
    private double averageSpent;

    @Transient
    private double sumSpent;
    private Date loginBlocked;
    private Date reservationBlocked;

    public User(String name, String lastname, String username, String password, String email, LocalDate dateOfBirth,
                Status status, Location location) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.location = location;
        this.userRank = UserRank.REGULAR;
        this.travels = new ArrayList<>();
        this.userParameters = new UserParameters();
        this.averageSpent = 0;
        this.sumSpent = 0;
        this.loginBlocked = new Date(2021, 5,5);
        this.reservationBlocked =  new Date(2021, 5,5);
    }

    public void addTravel(Travel travel) {
        this.travels.add(travel);
    }

    public int getAge() {
        return LocalDate.now().getYear() - this.dateOfBirth.getYear();
    }

    public Weather getUserWeather() {
        int month = LocalDate.now().getMonth().getValue();
        if (this.location.getContinent().equals("Europe") || this.location.getContinent().equals("Asia") || this.location.getContinent().equals("North America")) {
            if (month <= 2 || month == 12) return Weather.COLD;
            else if (month >= 6 && month <= 8) return Weather.WARM;
            else return Weather.NEUTRAL;
        }
        else if (this.location.getContinent().equals("Australia") || this.location.getContinent().equals("South America")) {
            if (month <= 2 || month == 12) return Weather.WARM;
            else if (month >= 6 && month <= 8) return Weather.COLD;
            else return Weather.NEUTRAL;
        }
        else return Weather.WARM;
    }

}
