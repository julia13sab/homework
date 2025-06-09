package lessons.runner;

import lessons.annotations_for_test.AfterSuite;
import lessons.annotations_for_test.AfterTest;
import lessons.annotations_for_test.BeforeSuite;
import lessons.annotations_for_test.BeforeTest;
import lessons.annotations_for_test.CsvSource;
import lessons.annotations_for_test.Test;
import lessons.exception.TestRunException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class TestRunner {

    private static Method beforeSuite = null;
    private static Method afterSuite = null;
    private static List<Method> beforeTest = new ArrayList<>();
    private static List<Method> afterTest = new ArrayList<>();
    private static List<Method> tests = new ArrayList<>();

    public static <T> void runTests(T instance) {
        init(instance);
        execute(instance);
    }

    private static <T> void init(T instance) {
        final var beforeSuites = getAnnotatedMethods(instance.getClass(), BeforeSuite.class, true);

        if (beforeSuites.size() > 1) {
            throw new TestRunException("Методов с аннотацией 'BeforeSuite' не может быть > 1");
        }
        if (!beforeSuites.isEmpty()) {
            beforeSuite = beforeSuites.get(0);
        }

        final var afterSuites = getAnnotatedMethods(instance.getClass(), AfterSuite.class, true);
        if (afterSuites.size() > 1) {
            throw new TestRunException("Методов с аннотацией 'AfterSuite' не может быть > 1");
        }
        if (!afterSuites.isEmpty()) {
            afterSuite = afterSuites.get(0);
        }
        beforeTest = getAnnotatedMethods(instance.getClass(), BeforeTest.class, false);
        afterTest = getAnnotatedMethods(instance.getClass(), AfterTest.class, false);

        final var rawTests = getAnnotatedMethods(instance.getClass(), Test.class, false);

        for (Method method : rawTests) {
            Test testAnnotation = method.getAnnotation(Test.class);
            int priority = testAnnotation.priority().getValue();
            if (priority < 1 || priority > 10) {
                throw new TestRunException("Метод " + method.getName() + " имеет приоритет " + priority + ", который не находится в диапазоне от 1 до 10.");
            }
            tests.add(method);
        }

        tests.sort(Comparator.comparingInt(a -> a.getAnnotation(Test.class).priority().getValue()));
    }

    private static <T> void execute(T instance) {
        invokeMethod(beforeSuite, instance, "Ошибка выполнения метода 'BeforeSuite'!");

        tests.forEach(test -> {
            beforeTest.forEach(before -> invokeMethod(before, instance, "Ошибка выполнения метода 'beforeTest'!"));
            invokeMethod(test, instance, "Ошибка выполнения теста");
            afterTest.forEach(after -> invokeMethod(after, instance, "Ошибка выполнения метода 'aferTest'!"));
        });

        invokeMethod(afterSuite, instance, "Ошибка выполнения метода 'AfterSuite'!");
    }

    private static <T> void invokeMethod(Method method, T instance, String error) {
        if (method == null) {
            return;
        }
        try {
            final var csvSource = method.getAnnotation(CsvSource.class);
            if (Objects.nonNull(csvSource)) {
                final var parameters = csvSource.parameter().split(",");
                final var parameterTypes = method.getParameterTypes();
                if (parameters.length != parameterTypes.length) {
                    throw new TestRunException("Количество параметров в аннотации CsvSource не соответствует количеству параметров метода: " + method.getName());
                }
                final var convertedParameters = convertParameters(parameters, parameterTypes);
                method.invoke(instance, convertedParameters);
            } else {
                method.invoke(instance);
            }
        } catch (Exception e) {
            throw new TestRunException(error, e);
        }
    }

    private static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotationClazz, boolean checkStatic) {
        List<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (!checkStatic || Modifier.isStatic(method.getModifiers())) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotationClazz.equals(annotation.annotationType())) {
                        methods.add(method);
                        break;
                    }
                }
            }
        }
        return methods;
    }

    private static Object[] convertParameters(String[] parameters, Class<?>[] parameterTypes) {
        Object[] convertedParameters = new Object[parameters.length];
        for (int i = 0; i < convertedParameters.length; i++) {
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
