package any.gin.example.demo;

import any.gin.example.demo.entity.Clazz;
import any.gin.example.demo.entity.Student;
import any.gin.example.demo.repository.ClazzRepository;
import any.gin.example.demo.repository.StudentRepository;
import any.gin.example.demo.to.StudentListTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Arrays;

/**
 * @author Gin
 * @since 2019/8/19 16:24
 */
public class StudentQuery extends QueryExampleApplicationTests {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ClazzRepository clazzRepository;

    @Test
    public void query() {
        Page<Student> page = studentRepository.findAllWith(new StudentListTO("", "c1",18));
        System.out.println(page.getTotalPages());
    }


    @Test
    public void persist() {
        Clazz clazz = new Clazz("2019", 3, "c1");
        clazzRepository.save(clazz);
        Student student1 = new Student("zs", 18, "1", clazz);
        Student student2 = new Student("ls", 18, "2", clazz);
        Student student3 = new Student("w2", 18, "3", clazz);

        studentRepository.saveAll(Arrays.asList(student1, student2, student3));

    }
}
