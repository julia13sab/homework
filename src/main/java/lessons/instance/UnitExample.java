package lessons.instance;

import lessons.annotations_for_test.*;
import lessons.enums.Priority;
import lessons.runner.TestRunner;

public class UnitExample {

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("BeforeSuite is calling!");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("AfterSuite is calling!");
    }

    @BeforeTest
    public void beforeTest1() {
        System.out.println("BeforeTest1 is calling! For each test method");
    }

    @BeforeTest
    public void beforeTest2() {
        System.out.println("BeforeTest2 is calling! For each test method");
    }

    @AfterTest
    public void afterTest1() {
        System.out.println("AfterTest1 is calling! For each test method");
    }

    @AfterTest
    public void afterTest2() {
        System.out.println("AfterTest2 is calling! For each test method");
    }

    @Test(priority = Priority.P1)
    public void testWithP1() {
        System.out.println("testWithP1 is calling!");
    }

    @Test(priority = Priority.P10)
    public void testWithP10() {
        System.out.println("testWithP10 is calling!");
    }

    @Test(priority = Priority.P1)
    @CsvSource(parameter = "10, Java, 20, true")
    public void testWithCsvSource(int a, String b, int c, boolean d) {
        System.out.println("Test1 is calling!");
        printParameterTypeAndValue("a", a);
        printParameterTypeAndValue("b", b);
        printParameterTypeAndValue("c", c);
        printParameterTypeAndValue("d", d);
    }

    @Test(priority = Priority.P5)
    @CsvSource(parameter = "StringTest, 10.057, 0.1, 123, true, False")
    public void testWithCsvSourceAllParam(String a, double b, Double c, Integer d, boolean e, Boolean f) {
        System.out.println("testWithCsvSourceAllParam");
        printParameterTypeAndValue("a", a);
        printParameterTypeAndValue("b", b);
        printParameterTypeAndValue("c", c);
        printParameterTypeAndValue("d", d);
        printParameterTypeAndValue("e", d);
        printParameterTypeAndValue("f", f);
    }

    @Test(priority = Priority.P2)
    public void test2() {
        System.out.println("Test2 is calling!");
    }

    @Test(priority = Priority.P4)
    public void test4() {
        System.out.println("Test4 is calling!");
    }

    public static void main(String[] args) {
        TestRunner.runTests(new UnitExample());
    }

    private static void printParameterTypeAndValue(String paramName, Object paramValue) {
        System.out.println("Parameter: " + paramName + ", Type: " + paramValue.getClass().getSimpleName() + ", Value: " + paramValue);
    }
}
