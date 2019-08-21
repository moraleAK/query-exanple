package any.gin.template.common.entity;

import any.gin.template.common.jpa.extend.BaseEntity;

import javax.persistence.*;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @author Gin
 * @since 2019/8/19 16:17
 */
@Table(name = "student")
@Entity
public class Student extends BaseEntity {
    public Student(String name, int age, String idNo, Clazz clazz) {
        this.name = name;
        this.age = age;
        this.idNo = idNo;
        this.clazz = clazz;
    }

    public Student() {
    }

    private String name;

    private int age;

    @Column(name = "id_no")
    private String idNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clazz_id")
    private Clazz clazz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public static void main(String[] args) throws IntrospectionException {
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Student.class).getPropertyDescriptors();

        System.out.println(propertyDescriptors.length);
    }
}
