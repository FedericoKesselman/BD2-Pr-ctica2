package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.Stop;
import java.util.*;

public interface ItemServiceRepository extends CrudRepository<Stop, Long> {
}