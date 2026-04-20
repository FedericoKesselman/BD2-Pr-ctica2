package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
}