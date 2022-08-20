package travel_recommendation.model;

public class Location {
    private String city;
    private String country;
    private String continent;
    private Coordinates coordinates;

    public Location() {
    }

    public Location(String city, String country, String continent, Coordinates coordinates) {
        this.city = city;
        this.country = country;
        this.continent = continent;
        this.coordinates = coordinates;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
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
