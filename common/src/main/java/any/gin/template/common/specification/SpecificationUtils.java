package any.gin.template.common.specification;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;

import javax.validation.constraints.NotNull;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Gin
 * @since 2019/6/10 17:13
 */
class SpecificationUtils {
    /**
     * 将 TO # property 转换为 specification condition 过滤带有 {@link SpecificationIgnore} 注解的属性 带有{@link SpecificationQuery}
     * 注解的属性，将按照{@link SpecificationQuery} 属性进行解析， 否则按照默认定义解析
     *
     * @param queryTO
     *
     * @return
     */
    static List<CustomCondition> getCustomConditions(@NotNull Object queryTO) {
        List<CustomCondition> customConditions = new ArrayList<>();
        Map<String, Method> properties = getPropertiesAndGetter(queryTO.getClass());

        // 仅获取当前类的属性，不获取父类属性
        Field[] fields = queryTO.getClass().getDeclaredFields();

        Set<String> ignoreSet = new HashSet<>();
        Map<String, SpecificationQuery> queryMap = new HashMap<>();

        //
        for (Field field : fields) {
            if (field.getAnnotation(SpecificationIgnore.class) != null) {
                ignoreSet.add(field.getName());
                continue;
            }

            queryMap.put(field.getName(), field.getAnnotation(SpecificationQuery.class));
        }

        for (Map.Entry<String, Method> entry : properties.entrySet()) {
            // filed 标有忽略注解或者查询注解 不再进行处理
            if (ignoreSet.contains(entry.getKey())) {
                continue;
            }

            if (queryMap.get(entry.getKey()) != null) {
                continue;
            }

            // property 标有忽略注解不进行处理，此处可不添加到  ignoreSet里
            if (AnnotationUtils.findAnnotation(entry.getValue(), SpecificationIgnore.class) != null) {
                queryMap.remove(entry.getKey());
                continue;
            }

            queryMap.put(entry.getKey(), AnnotationUtils.findAnnotation(entry.getValue(), SpecificationQuery.class));
        }


        for (Map.Entry<String, SpecificationQuery> entry : queryMap.entrySet()) {
            if (ignoreSet.contains(entry.getKey())) {
                continue;
            }

            addAllConditions(entry.getValue(), entry.getKey(), properties.get(entry.getKey()), queryTO, customConditions);
            ignoreSet.add(entry.getKey());
        }

        return customConditions;
    }

    /**
     * 将 property 解析为 {@link CustomCondition}
     *
     * @param query
     * @param name
     * @param method
     * @param target
     * @param customConditions
     */
    static void addAllConditions(SpecificationQuery query, String name, Method method, Object target, List<CustomCondition> customConditions) {
        if (query != null) {
            // Arrays.asList()转化后是个内部的List,且未实现add方法，故长度固定
            List<String> columns = new ArrayList<>(Arrays.asList(query.property()));
            if (columns.size() == 0) {
                columns.add(name);
            }

            Object propertyValue;
            query.castMethod();

            if ("".equals(query.castMethod())) {
                propertyValue = invoke(method, target);
            } else {
                propertyValue = invoke(BeanUtils.findMethod(target.getClass(), query.castMethod()), target);
            }

            if (propertyValue == null) {
                return;
            }

            if (propertyValue instanceof String) {
                if ("".equals(propertyValue)) {
                    return;
                }
            }

            int i = 1;
            CustomCondition customCondition = null;
            for (String columnName : columns) {
                if (i == 1) {
                    customCondition = new CustomCondition(columnName, name, propertyValue, query.compare());
                    if ("".equals(query.ignoreCaseMethod())) {
                        customCondition.setIgnoreCase(query.ignoreCase());
                    } else {
                        Boolean ignoreCase = (Boolean) invoke(BeanUtils.findMethod(target.getClass(), query.ignoreCaseMethod()), target);
                        customCondition.setIgnoreCase(ignoreCase == null ? false : ignoreCase);
                    }
                    customCondition.setLinkedType(query.operator());
                    customCondition.setJoinName(query.join());
                    customConditions.add(customCondition);
                } else {
                    customCondition.inNames.add(columnName);
                    customCondition.inLinkedType = query.multiOperator();
                }
                i++;
            }
        } else {
            Object value = invoke(method, target);
            if (value == null) {
                return;
            }

            if (value instanceof String) {
                if ("".equals(value)) {
                    return;
                }
            }

            customConditions.add(new CustomCondition(name, name, invoke(method, target), CompareType.EQUAL));

        }
    }


    static Map<String, Object> getProperties(Object target) {
        if (target == null) {
            return new HashMap<>();
        }

        Map<String, Method> getterMap = getPropertiesAndGetter(target.getClass());

        // 仅获取当前类的属性，不获取父类属性
        Field[] fields = target.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(field -> field.getAnnotation(SpecificationIgnore.class) == null)
                .collect(HashMap::new, (m, f) -> {
                    try {
                        m.put(f.getName(), getterMap.get(f.getName()).invoke(target));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }, HashMap::putAll);

        // Collectors.toMap 底层使用HashMap.merge实现，当value == null时，抛出NPE
        // 方法不符合预期，也可能是个bug, 慎用
        // .collect(Collectors.toMap(Field::getName, field -> getFieldValue(field, target), (a, b) -> b));
    }

    private static Object invoke(Method method, Object target) {
        try {
            return method.invoke(target);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取 TYPE 的 property, 排除 Object # getClass
     *
     * @param target
     *         目标类
     *
     * @return
     */
    static Map<String, Method> getPropertiesAndGetter(Class target) {
        PropertyDescriptor[] propertyDescriptors = new PropertyDescriptor[0];
        try {
            propertyDescriptors = Introspector.getBeanInfo(target).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        Map<String, Method> getterMap = new HashMap<>();

        Arrays.asList(propertyDescriptors)
                .forEach(propertyDescriptor -> getterMap.put(propertyDescriptor.getName(), propertyDescriptor.getReadMethod()));

        // remove Object#getClass
        getterMap.remove("class");

        return getterMap;

    }

}
