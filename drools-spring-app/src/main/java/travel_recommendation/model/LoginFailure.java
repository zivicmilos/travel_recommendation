package travel_recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login_failures")
@Role(Role.Type.EVENT)
@Expires("2h30m")
public class LoginFailure {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "failure_date")
    private LocalDateTime failureDate;

    public LoginFailure(User user, LocalDateTime failureDate) {
        this.user = user;
        this.failureDate = failureDate;
    }
}
