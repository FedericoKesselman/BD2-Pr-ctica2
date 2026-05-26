package unlp.info.bd2.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.*;
import unlp.info.bd2.model.Route;
import unlp.info.bd2.model.Stop;
import unlp.info.bd2.dto.RouteSummaryDTO;

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
            SELECT p FROM 
            Purchase p 
            WHERE p.route = r    
    )
    """)
    List<Route> getRoutesNotSell(); 

    @Query("""
        SELECT p.route
        FROM Purchase p
        JOIN p.review r
        GROUP BY p.route
        ORDER BY AVG(r.rating) DESC
    """)
    List<Route> getTop3RoutesWithMaxRating(Pageable pageable);
    
    List<Route> findByPriceLessThan(float price);

	Optional<Route> finById(Long idRoute);

	@Query("SELECT COUNT(r) FROM Route r JOIN r.tourGuideList g WHERE g.id ) :id")
	Long countRoutesByGuide(Long id);

	@Query("SELECT COUNT(p) FROM Purchase p WHERE p.route.id = :id")
	long countPurchasesByRoute(Long id);

    @Query("""
        SELECT new unlp.info.bd2.dto.RouteSummaryDTO(
            r.name,
            COUNT(p),
            AVG(p.totalPrice)
        )
        FROM Route r
        LEFT JOIN Purchase p ON p.route = r
        GROUP BY r.name
    """)
    List<RouteSummaryDTO> getRouteSummary();
}