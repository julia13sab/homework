package lessons.task_2;

import lessons.task_2.Worker;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TetsMain {
    public static void main(String[] args) {

        System.out.println("Задание 1. Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)");
        List<Integer> firstTestList = new ArrayList<>(Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13));
        firstTestList.stream().sorted(Comparator.reverseOrder()).skip(2).findFirst()
                .ifPresent(element -> System.out.println("3-е наибольшее число " + element));


        System.out.println("Задание 2. Найдите в списке целых чисел 3-е наибольшее «уникальное» число ");
        List<Integer> secondTestList = new ArrayList<>(Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13));
        secondTestList.stream().distinct().sorted(Comparator.reverseOrder()).skip(2).findFirst()
                .ifPresent(element -> System.out.println("3-е наибольшее уникальное число " + element));


        System.out.println("Задание 3. Имеется список объектов типа Сотрудник (имя, возраст, должность), " +
                "необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста");
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

        List<Worker> thirdTestList = new ArrayList<>(Arrays.asList(one, two, three, four, five, six, seven, eight, nine, ten));
        thirdTestList.stream().filter(worker -> "Инженер".equals(worker.getPosition()))
                .sorted(Comparator.comparing(Worker::getAge).reversed()).limit(3)
                .filter(worker -> "Инженер".equals(worker.getPosition())).map(Worker::getName).forEach(System.out::println);


        System.out.println("Задание 4. Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст сотрудников с должностью «Инженер»");
        thirdTestList.stream().filter(worker -> "Инженер".equals(worker.getPosition())).mapToInt(Worker::getAge).average().ifPresent(System.out::println);

        System.out.println("Задание 5. Найдите в списке слов самое длинное");
        List<String> fourthTestList = new ArrayList<>(Arrays.asList("это", "список", "слов", "найти", "самое", "длинное", "например", "энциклопедия1", "энциклопедия2"));
        fourthTestList.stream().max(Comparator.comparingInt(String::length)).ifPresent(System.out::println);


        System.out.println("Задание 6. Имеется строка с набором слов в нижнем регистре, разделенных пробелом. " +
                "Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке");
        Stream.of("мама папа мама брат брат сестра дочь сестра сестра")
                .flatMap(value -> Arrays.stream(value.split(" ")))
                .collect(Collectors.toMap(k -> k, v -> 1, Integer::sum))
                .forEach((k, v) -> System.out.println(k + ":" + v));


        System.out.println("Задание 7. Отпечатайте в консоль строки из списка в порядке увеличения длины слова, если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок");
        Stream.of("q", "qw", "qwe", "qwer", "qwert", "bil", "qwerty", "qwerty", "bob", "test1", "1test")
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(String::compareTo)).forEach(System.out::println);


        System.out.println("Задание 8. Имеется массив строк, в каждой из которых лежит набор из 5 слов, " +
                "разделенных пробелом, найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них");

        Stream.of("тест имеется массив строк библиотека",
                        "библиотека найдите среди всех слов",
                        "самое длинное получите любое тест")
                .flatMap(value -> Arrays.stream(value.split(" "))).max(Comparator.comparingInt(String::length)).ifPresent(System.out::println);

    }
}
