package travel_recommendation.dto;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("time")
@Expires("2h30m")
public class LikeDto implements Serializable {
    private String user;
    private String destination;
    private Date time;

    public LikeDto(String user, String destination, Date time) {
        this.user = user;
        this.destination = destination;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
