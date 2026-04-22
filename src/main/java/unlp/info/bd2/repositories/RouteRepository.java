package unlp.info.bd2.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.*;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;

public interface RouteRepository extends CrudRepository<Route, Long> {

    List<Route> findyByStop(Stop stop);

    @Query("""
    SELECT MAX(SIZE(r.stops))
    FROM Route r
    """)
    Long getMaxStopOfRoutes();

    @Query("""
    SELECT r 
    FROM Route r
    WHERE NOT EXISTS (
        SELECT p FROM Purchase p WHERE p.route = r    
    )
    """)
    List<Route> getRoutesNotSell(); 

    @Query("""
    SELECT p.route
    FROM Purchase p
    JOIN p.review rv
    GROUP BY p.route
    ORDER BY AVG(rv.rating) DESC
    """)
    List<Route> getTop3RoutesWithMaxRating(Pageable pageable);
}