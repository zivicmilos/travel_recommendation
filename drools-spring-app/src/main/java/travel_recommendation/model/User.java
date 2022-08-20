package travel_recommendation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private LocalDate dateOfBirth;
    private Status status;
    private Location location;
    private UserRank userRank;
    @JsonManagedReference
    private List<Travel> travels;
    private TransportationType transportationType;
    private DestinationType destinationType;
    private Weather weather;
    private String continent;
    private double budget;
    private double averageSpent;
    private double sumSpent;
    private Date loginBlocked;
    private Date reservationBlocked;

    public User() {
    }

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
        this.destinationType = DestinationType.NA;
        this.transportationType = TransportationType.NA;
        this.weather = Weather.NA;
        this.continent = "";
        this.budget = 0;
        this.averageSpent = 0;
        this.sumSpent = 0;
        this.loginBlocked = new Date(2021, 5,5);
        this.reservationBlocked =  new Date(2021, 5,5);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public TransportationType getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(TransportationType transportationType) {
        this.transportationType = transportationType;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getAge() {
        return LocalDate.now().getYear() - this.dateOfBirth.getYear();
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRank getUserRank() {
        return userRank;
    }

    public void setUserRank(UserRank userRank) {
        this.userRank = userRank;
    }

    public List<Travel> getTravels() {
        return travels;
    }

    public void setTravels(List<Travel> travels) {
        this.travels = travels;
    }

    public void addTravel(Travel travel) {
        this.travels.add(travel);
    }

    public double getAverageSpent() {
        return averageSpent;
    }

    public void setAverageSpent(double averageSpent) {
        this.averageSpent = averageSpent;
    }

    public double getSumSpent() {
        return sumSpent;
    }

    public void setSumSpent(double sumSpent) {
        this.sumSpent = sumSpent;
    }

    public Date getLoginBlocked() {
        return loginBlocked;
    }

    public void setLoginBlocked(Date loginBlocked) {
        this.loginBlocked = loginBlocked;
    }

    public Date getReservationBlocked() {
        return reservationBlocked;
    }

    public void setReservationBlocked(Date reservationBlocked) {
        this.reservationBlocked = reservationBlocked;
    }
}
