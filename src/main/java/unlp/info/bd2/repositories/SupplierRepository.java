package unlp.info.bd2.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.*;
import unlp.info.bd2.model.Supplier;


public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    @Query("""
    SELECT s
    FROM Supplier s
    JOIN s.services sv
    JOIN sv.items i 
    JOIN i.purchase p
    GROUP BY s
    ORDER BY COUNT(p) DESC
    """)
    List<Supplier> getTopSuppliers(Pageable pageable);

	Optional<Supplier> findByAuthorizationNumber(String authorizationNumber);
}