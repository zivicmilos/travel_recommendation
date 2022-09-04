package travel_recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import travel_recommendation.model.Role;
import travel_recommendation.model.User;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("select r from Role r where r.name=:name")
    Set<Role> findByName(@Param("name") String name);
}
