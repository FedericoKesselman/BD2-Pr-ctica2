package unlp.info.bd2.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.*;
import unlp.info.bd2.model.Serv;

public interface ServiceRepository extends CrudRepository<Serv, Long> {

    @Query("""
    SELECT i.service
    FROM ItemService i
    GROUP BY i.service
    ORDER BY SUM(i.quantity) DESC
    """)
    List<Serv> getMostDemandedService(Pageable pageable);

	Optional<Serv> findByNameAndSupplierId(String name, Long id); 
}