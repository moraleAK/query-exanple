package any.gin.template.common.repository;

import any.gin.template.common.entity.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Gin
 * @since 2019/8/19 16:20
 */
public interface ClazzRepository extends JpaRepository<Clazz, Long> {
}
