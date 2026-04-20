package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Service;

public interface ServiceRepository extends CrudRepository<Service, Long> {
}