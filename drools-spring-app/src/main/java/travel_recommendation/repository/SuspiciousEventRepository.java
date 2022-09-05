package travel_recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel_recommendation.model.SuspiciousEvent;

@Repository
public interface SuspiciousEventRepository extends JpaRepository<SuspiciousEvent, Integer> {
}
