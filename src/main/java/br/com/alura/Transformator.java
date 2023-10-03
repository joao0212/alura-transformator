package br.com.alura;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Transformator {
    public <T, U> U transform(T source) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = Class.forName(sourceClass.getName() + "DTO");

        U target = (U) targetClass.getDeclaredConstructor().newInstance();

        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();

        Arrays.stream(sourceFields)
                .forEach(sourceField -> Arrays.stream(targetFields)
                        .forEach(targetField -> {
                            validate(sourceField, targetField);
                            try {
                                targetField.set(target, sourceField.get(source));
                            } catch (IllegalAccessException e) {
                                System.out.println(e.getMessage());
                            }
                        })
                );
        return target;
    }

    private void validate(Field sourceField, Field targetField) {
        if (sourceField.getName().equals(targetField.getName())
                && sourceField.getType() == targetField.getType()) {
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
        }
    }
}