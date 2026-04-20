package unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import unlp.info.bd2.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}