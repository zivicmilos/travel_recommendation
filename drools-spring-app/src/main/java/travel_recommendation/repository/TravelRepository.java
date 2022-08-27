package travel_recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import travel_recommendation.model.Travel;

import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Integer> {

    @Query("select t from Travel t where t.user.username=:username")
    List<Travel> findByUsername(@Param("username") String username);
}
