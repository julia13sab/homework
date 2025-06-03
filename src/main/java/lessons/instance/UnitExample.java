package lessons.instance;

import lessons.annotations_for_test.*;
import lessons.enums.Priority;
import lessons.runner.TestRunner;

public class UnitExample {

    @BeforeSuite
    public static void initSuite() {
        System.out.println("BeforeSuite is calling!");
    }

  /*  @BeforeSuite
    public static void initNonStaticSuite() {
        System.out.println("BeforeSuite is calling!");
    }*/

    @AfterSuite
    public static void afterAllSuite() {
        System.out.println("AfterSuite is calling!");
    }

    @BeforeTest
    public void beforeTest1() {
        System.out.println("BeforeTest1 is calling!");
    }

    @BeforeTest
    public void beforeTest2() {
        System.out.println("BeforeTest2 is calling!");
    }

    @AfterTest
    public void afterTest1() {
        System.out.println("AfterTest1 is calling!");
    }

    @AfterTest
    public void afterTest2() {
        System.out.println("AfterTest2 is calling!");
    }

    @Test(priority = Priority.P3)
    @CsvSource(parameter = "1 test 5")
    public void test3() {
        System.out.println("Test3 is calling!");
    }

    @Test(priority = Priority.P1)
    @CsvSource(parameter = "1 test 5")
    public void test1() {
        System.out.println("Test1 is calling!");
    }

    @Test(priority = Priority.P5)
    @CsvSource(parameter = "1 test 5")
    public void test5() {
        System.out.println("Test5 is calling!");
    }

    @Test(priority = Priority.P2)
    @CsvSource(parameter = "1 test 5")
    public void test2() {
        System.out.println("Test2 is calling!");
    }

    @Test(priority = Priority.P4)
    @CsvSource(parameter = "1 test 5")
    public void test4() {
        System.out.println("Test4 is calling!");
    }

    public static void main(String[] args) {
        TestRunner.runTests(new UnitExample());
    }
}
