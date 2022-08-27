package travel_recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import travel_recommendation.model.Destination;
import travel_recommendation.model.User;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {

    @Query("select d from Destination d where d.location.city=:city")
    Destination findByCity(@Param("city") String city);
}
