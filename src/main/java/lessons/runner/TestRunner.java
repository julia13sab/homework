package lessons.runner;



import lessons.annotations_for_test.*;
import lessons.exception.TestRunException;
import lessons.instance.UnitExample;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestRunner<T> {

    public static void runTests(UnitExample instance) {
        beforeSuite(instance);
        execute(instance);
        afterSuite(instance);
    }

    private static void beforeSuite(UnitExample instance) {
        final var beforeSuites = getAnnotatedMethods(instance.getClass(), BeforeSuite.class, true);
        if (!beforeSuites.isEmpty()) {
            if (beforeSuites.size() > 1) {
                throw new TestRunException("Методов с аннотацией 'BeforeSuite' не может быть > 1");
            }
            invokeMethod(beforeSuites.get(0), instance, "Ошибка выполнения метода 'BeforeSuite'!");
        }
    }

    private static void execute(UnitExample instance) {
        final var tests = getAnnotatedMethods(instance.getClass(), Test.class, false)
                .stream().sorted(Comparator.comparingInt(a -> a.getAnnotation(Test.class).priority().getValue()))
                .toList();

        final var beforeTest = getAnnotatedMethods(instance.getClass(), BeforeTest.class, false);
        final var afterTest = getAnnotatedMethods(instance.getClass(), AfterTest.class, false);
        tests.forEach(test -> {
            beforeTest.forEach(before -> invokeMethod(before, instance, "Ошибка выполнения метода 'beforeTest'!"));
            invokeMethod(test, instance, "Ошибка выполнения теста");
            afterTest.forEach(before -> invokeMethod(before, instance, "Ошибка выполнения метода 'aferTest'!"));
        });
    }

    private static void afterSuite(UnitExample instance) {
        final var afterSuites = getAnnotatedMethods(instance.getClass(), AfterSuite.class, true);
        if (!afterSuites.isEmpty()) {
            if (afterSuites.size() > 1) {
                throw new TestRunException("Методов с аннотацией 'AfterSuite' не может быть > 1");
            }
            invokeMethod(afterSuites.get(0), instance, "Ошибка выполнения метода 'AfterSuite'!");
        }
    }

    private static void invokeMethod(Method method, UnitExample instance, String error) {
        try {
            final var csvSource = method.getAnnotation(CsvSource.class);
            if (Objects.nonNull(csvSource)) {
                final var parameters = csvSource.parameter().split(" ");
                final var parameterTypes = method.getParameterTypes();
                final var convertedParameters = convertParameters(parameters, parameterTypes);
                method.invoke(instance, convertedParameters);
                method.invoke(instance);
            } else {
                method.invoke(instance);
            }
        } catch (Exception e) {
            throw new TestRunException(error, e);
        }
    }

    private static List<Method> getAnnotatedMethods(Class clazz, Class annotationClazz, boolean checkStatic) {
        return Stream.of(clazz.getDeclaredMethods())
                .filter(method -> !checkStatic || Modifier.isStatic(method.getModifiers()))
                .filter(method -> Arrays.stream(method.getAnnotations()).anyMatch(annotation ->
                        annotationClazz.equals(annotation.annotationType())))
                .collect(Collectors.toList());
    }

    private static Object[] convertParameters(String[] parameters, Class<?>[] parameterTypes) {
        Object[] convertedParameters = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            convertedParameters[i] = convertParameter(parameters[i].trim(), parameterTypes[i]);
        }
        return convertedParameters;
    }

    private static Object convertParameter(String parameter, Class<?> parameterType) {
        return switch (parameterType.getName()) {
            case "java.lang.String" -> parameter;
            case "int", "java.lang.Integer" -> Integer.parseInt(parameter);
            case "long", "java.lang.Long" -> Long.parseLong(parameter);
            case "double", "java.lang.Double" -> Double.parseDouble(parameter);
            case "boolean", "java.lang.Boolean" -> Boolean.parseBoolean(parameter);
            default -> throw new TestRunException("Не поддерживаемый тип параметра: " + parameterType);
        };
    }
}
