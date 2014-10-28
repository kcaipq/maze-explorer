package uk.gov.dwp.maze;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by kcai on 28/10/2014.
 */
public class ReflectionTestUtil {

    public static Object getField(Object target, String name, Class<?> type) {
        Field field = findField(target.getClass(), name, type);
        makeAccessible(field);
        return getField(field, target);
    }

    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        Class<?> searchType = clazz;
        while (!Object.class.equals(searchType) && searchType != null) {
            Field[] fields = searchType.getDeclaredFields();
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName())) && (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    public static Object getField(Field field, Object target) {
        try {
            return field.get(target);
        } catch (IllegalAccessException ex) {

        }
           return null;
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }
}