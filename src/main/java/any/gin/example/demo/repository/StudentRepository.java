package any.gin.example.demo.repository;

import any.gin.example.demo.entity.Student;
import any.gin.example.demo.specification.JpaRepositoryExtend;

/**
 * @author Gin
 * @since 2019/8/19 16:19
 */
public interface StudentRepository extends JpaRepositoryExtend<Student, Long> {
}
