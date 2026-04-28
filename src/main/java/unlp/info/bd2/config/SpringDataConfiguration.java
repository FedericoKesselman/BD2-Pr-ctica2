package unlp.info.bd2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@EnableJpaRepositories(basePackages = "unlp.info.bd2.repositories")
@EntityScan(basePackages = "unlp.info.bd2.model")
public class SpringDataConfiguration {
}