package travel_recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel_recommendation.model.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
}
