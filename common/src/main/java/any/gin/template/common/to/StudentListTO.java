package any.gin.template.common.to;

import any.gin.template.common.jpa.extend.PageRequest;
import any.gin.template.common.jpa.extend.SpecificationQuery;

/**
 * @author Gin
 * @since 2019/8/19 16:21
 */
public class StudentListTO extends PageRequest {
    public StudentListTO(String name, String clazzName, Integer age) {
        this.name = name;
        this.clazzName = clazzName;
        this.age = age;
    }

    @SpecificationQuery
    private String name;

    @SpecificationQuery(join = "clazz", property = "name")
    private String clazzName;

    @SpecificationQuery
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
