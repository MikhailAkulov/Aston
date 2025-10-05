package task2.utils;

import java.lang.reflect.Field;

public class TestUtils {
    /**
     * Утилита для подмены приватных полей
     * Чтобы не переделывать конструктор
     */
    public static void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
