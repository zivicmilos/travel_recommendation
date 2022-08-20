package travel_recommendation.model;

import java.util.ArrayList;
import java.util.List;

public class Destination {
    private Weather weather;
    private List<DestinationType> destinationTypes;
    private Location location;
    private List<TransportationType> transportationTypes;
    private List<Like> likes;
    private String username;
    private double score;
    private TransportationType recommendedTransportationType;
    private double cost;
    private double grade;

    public Destination() {
    }

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

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public List<DestinationType> getDestinationTypes() {
        return destinationTypes;
    }

    public void setDestinationTypes(List<DestinationType> destinationTypes) {
        this.destinationTypes = destinationTypes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<TransportationType> getTransportationTypes() {
        return transportationTypes;
    }

    public void setTransportationTypes(List<TransportationType> transportationTypes) {
        this.transportationTypes = transportationTypes;
    }

    public void addTransportationType(TransportationType transportationType) {
        this.transportationTypes.add(transportationType);
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }

    public TransportationType getRecommendedTransportationType() {
        return recommendedTransportationType;
    }

    public void setRecommendedTransportationType(TransportationType recommendedTransportationType) {
        this.recommendedTransportationType = recommendedTransportationType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
