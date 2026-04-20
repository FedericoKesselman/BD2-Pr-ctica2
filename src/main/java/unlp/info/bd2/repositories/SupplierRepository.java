package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Supplier;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {
}