package any.gin.template.common.entity;

import any.gin.template.common.specification.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Gin
 * @since 2019/8/19 16:16
 */
@Entity
@Table(name = "class")
public class Clazz extends BaseEntity {

    public Clazz(String classNo, int count, String name) {
        this.classNo = classNo;
        this.count = count;
        this.name = name;
    }

    public Clazz() {
    }

    @Column(name = "classNo")
    private String classNo;

    private int count;

    private String name;

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
