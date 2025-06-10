package lessons.task_2;

import lessons.task_2.Worker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TetsMain {
    public static void main(String[] args) {
        List<Integer> firstTestList = new ArrayList<>(Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13));
        firstTestList.stream().sorted(Comparator.reverseOrder()).skip(2).findFirst()
                .ifPresent(element -> System.out.println("3-е наибольшее число " + element));

        List<Integer> secondTestList = new ArrayList<>(Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13));
        secondTestList.stream().distinct().sorted(Comparator.reverseOrder()).skip(2).findFirst()
                .ifPresent(element -> System.out.println("3-е наибольшее уникальное число " + element));


        Worker one = new Worker("Anna", 55, "Инженер");
        Worker two = new Worker("Oleg", 60, "qa");
        Worker three = new Worker("Tanya", 18, "Инженер");
        Worker four = new Worker("Mike", 20, "lead");
        Worker five = new Worker("Nik", 30, "Инженер");
        Worker six = new Worker("Anna", 55, "qa");
        Worker seven = new Worker("Oleg", 60, "qa");
        Worker eight = new Worker("Tanya", 18, "Инженер");
        Worker nine = new Worker("Mike", 20, "lead");
        Worker ten = new Worker("Ross", 40, "Инженер");

        List<Worker> thirdTestList = new ArrayList<>(Arrays.asList(one, two, three, four, five ,six, seven, eight, nine, ten));
       thirdTestList.stream().filter(worker -> "Инженер".equals(worker.getPosition()))
                .sorted(Comparator.comparing(Worker::getAge).reversed()).limit(3)
                .filter(worker -> "Инженер".equals(worker.getPosition())).map(Worker::getName).forEach(System.out::println);



        List<String> fourthTestList = new ArrayList<>(Arrays.asList("это", "список", "слов", "найти", "самое", "длинное", "например", "энциклопедия1", "энциклопедия2"));
        fourthTestList.stream().max(Comparator.comparingInt(String::length)).ifPresent(System.out::println);

    }
}
