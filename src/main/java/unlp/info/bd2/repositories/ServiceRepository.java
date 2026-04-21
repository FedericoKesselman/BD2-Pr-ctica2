package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Service;

public interface ServiceRepository extends CrudRepository<Service, Long> {

    @Query("""
    SELECT i.service
    FROM ItemService i
    GROUP BY i.service
    ORDER BY SUM(i.quantity) DESC
    """)
    List<Service> getMostDemandedService(Pageable pageable);  
}