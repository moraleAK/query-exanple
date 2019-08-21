package any.gin.example.demo.specification;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Gin
 * @since 2019/8/19 16:59
 */
@NoRepositoryBean
public interface JpaRepositoryExtend<T extends BaseEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor {

    Optional<T> findByIdAndDeletedFalse(ID id);

    T getOneByDeletedFalse(ID id);

    <X extends PageRequest> Page<T> findAllWith(X pageRequest);

    <X extends PageRequest, R> PaginationResult findAllWith(X query, Function<T, R> transfer);

}
