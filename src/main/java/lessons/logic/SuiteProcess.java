package lessons.logic;

import lessons.annotations_for_test.AfterSuite;
import lessons.annotations_for_test.BeforeSuite;
import lessons.exception.NonStaticMethodException;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class SuiteProcess {

    public void logicForAfterSuiteAnnotation(Class<?> userClass) throws NonStaticMethodException {
        for (Method method : userClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AfterSuite.class) || method.isAnnotationPresent(BeforeSuite.class)) {
                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new NonStaticMethodException("Метод не является статическим");
                }
                // Вызов метода, если он статический
                try {
                    method.invoke(null); // null, так как метод статический
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

