package any.gin.template.admin.config;


import any.gin.template.common.jpa.extend.JpaRepositoryExtendedImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Gin
 * @since 2019/8/19 17:32
 */
@Configuration
@EnableJpaRepositories(basePackages = "any.gin.template.common.repository",
        repositoryBaseClass = JpaRepositoryExtendedImpl.class)
public class Config {

}
