package travel_recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel_recommendation.model.DeletedTravel;

@Repository
public interface DeletedTravelRepository extends JpaRepository<DeletedTravel, Integer> {
}
