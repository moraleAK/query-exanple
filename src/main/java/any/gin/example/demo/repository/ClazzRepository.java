package any.gin.example.demo.repository;

import any.gin.example.demo.entity.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Gin
 * @since 2019/8/19 16:20
 */
public interface ClazzRepository extends JpaRepository<Clazz, Long> {
}
