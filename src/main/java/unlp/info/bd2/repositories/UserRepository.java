package unlp.info.bd2.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.*;

import unlp.info.bd2.model.TourGuideUser;
import unlp.info.bd2.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("""
    SELECT u
    FROM User u
    JOIN u.purchases p
    GROUP BY u
    HAVING SUM(p.totalPrice) > :amount
    """)
    List<User> getUserSpendingMoreThan(@Param("amount") float amount);

    @Query("""
    SELECT DISTINCT rg
    FROM Purchase p
    JOIN p.review rv
    JOIN p.route r
    JOIN r.tourGuides tg
    WHERE rv.rating = 1
    """)
    List<TourGuideUser> getTourGuidesWithRating1();  
}