package any.gin.example.demo.specification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Gin
 * @since 2019/8/19 17:03
 */
@Transactional(readOnly = true)
@SuppressWarnings("all")
public class JpaRepositoryExtendedImpl<T extends BaseEntity,ID> extends SimpleJpaRepository implements JpaRepositoryExtend {
    public JpaRepositoryExtendedImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public Page findAllWith(PageRequest pageRequest) {
        return findAll(SpecificationFactory.getConditions(pageRequest, getDomainClass()), pageRequest.toPageable());
    }

    @Override
    public PaginationResult findAllWith(PageRequest query, Function transfer) {
        return new PaginationResult().of(
                findAll(SpecificationFactory.getConditions(query, getDomainClass()), query.toPageable())
                , transfer
        );
    }

    @Override
    public Optional<T> findByIdAndDeletedFalse(Object id) {
        TypedQuery<T> query = getQuery(SpecificationFactory.getConditions(new DeletedFalse(false, id ), getDomainClass()), Pageable.unpaged());
        return Optional.of(query.getSingleResult());
    }

    @Override
    public BaseEntity getOneByDeletedFalse(Object id) {
        return findByIdAndDeletedFalse(id).get();
    }

    class DeletedFalse {
        public DeletedFalse(boolean deleted, Object id) {
            this.deleted = deleted;
            this.id = id;

        }

        private boolean deleted;

        private Object id;

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }
    }
}
