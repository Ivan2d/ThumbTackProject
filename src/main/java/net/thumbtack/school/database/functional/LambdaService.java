package net.thumbtack.school.database.functional;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LambdaService {

    // #1
    Function<String, List<String>> split = s -> Arrays.asList(s.split(" "));
    Function<List<?>, Integer> count = list -> list.size();
    String str = "Это пример строки, содержащей пробелы";
    List<String> strList = split.apply(str);
    int countResult = split.andThen(count).apply(str);

    // #2
    // Да, возможно избавиться от декларации типов в параметрах функций,
    // созданных с помощью функционального интерфейса java.util.function.Function и лямбда-выражений.
    // В данном случае это можно сделать благодаря выводу типов,
    // который был введен в Java 8 для упрощения написания кода с лямбда-выражениями,
    // т.е. вместо явного указания типа параметров в лямбда-выражениях, мы можем опустить их и
    // дать компилятору самому определить типы на основе контекста, в котором используется функция.

    // #3
    Function<String, List<String>> splitReference = MyStringService::split;
    Function<List<?>, Integer> countReference = List::size;
    // можно заменить лямбда-выражение на ссылку на метод (method reference), если
    // лямбда-выражение просто вызывает метод без дополнительной логики или вычислений.

    // #4
     Function<String, Integer> splitAndCount = ((Function<String, List<String>>) MyStringService::split)
            .andThen(List::size);
     Function<String, Integer> splitAndCountCompose = ((Function<List<String>, Integer>) List::size)
            .compose((Function<String, List<String>>) MyStringService::split);

    // Использование count.apply(split.apply(str)) подразумевает,
    // что мы явно вызываем функции в нужном порядке и передаем
    // результат одной функции в качестве аргумента в другую функцию,
    // использование методов andThen() и compose() позволяет
    // композировать функции в более явном и читаемом виде.

    // #5
    Function<String, Person> create = name -> new Person(name);
    Function<String, Person> createByReference = Person::new;

    // #6
    BinaryOperator<Integer> max = Math::max;
    // Здесь мы используем функциональный интерфейс BinaryOperator, который
    // принимает два аргумента одного типа и возвращает результат того же типа.

    // #7
    Supplier<Date> getCurrentDate = Date::new;
    Date currentDate = getCurrentDate.get();
    // Здесь можно использовать функциональный интерфейс Supplier<T>, который
    // определяем метод get() без параметров и возвращает значение типа T.

    // #8
    Predicate<Integer> isEven = a -> a % 2 == 0;
    // Здесь можно использовать функциональный интерфейс Predicate<T>, который
    // определяет метод test(T t) и возвращает булево значение.

    // #9
    BiPredicate<Integer, Integer> areEqual = (a, b) -> Objects.equals(a, b);
    // Здесь можно использовать функциональный интерфейс BiPredicate<T, U>, который
    // определяет метод test(T t, U u) и возвращает булево значение.

    // #10
    MyFunction<String, List<String>> mySplit = s -> Arrays.asList(s.split(" "));

    // #11
    // Мы получим ошибку компиляции, поскольку MyFunction больше не является функциональным интерфейсом,
    // мы пытаемся использовать декоратор @FunctionalInterface, который сообщает компилятору,
    // что интерфейс должен содержать только один абстрактный метод, но это не так, поскольку интерфейс
    // будет иметь больше одного абстрактного метода

    // #12
    // Решение в классах PersonNonOptional и PersonWithOptional

    // #13
    public static void transform(IntStream stream, IntUnaryOperator op) {
        stream.map(op).forEach(System.out::println);
    }

    // #14
    public static IntStream transformAnother(IntStream stream, IntUnaryOperator op) {
        return stream.map(op);
    }
    IntStream stream = Arrays.stream(new int[]{1, 2, 3, 4, 5});
    IntUnaryOperator op = x -> x * 2;
    IntStream resultStream = transformAnother(stream, op).parallel();

    // #15
    List<PersonWithAge> persons = Arrays.asList(
            new PersonWithAge("John", 25),
            new PersonWithAge("Jane", 35),
            new PersonWithAge("Mary", 40),
            new PersonWithAge("Bob", 50),
            new PersonWithAge("Alice", 30),
            new PersonWithAge("Jack", 60)
    );
    List<String> uniqueNames = persons
            .stream()
            .filter(p -> p.getAge() > 30)
            .map(PersonWithAge::getName)
            .distinct()
            .sorted(Comparator.comparing(String::length).reversed())
            .collect(Collectors.toList());

    // #16
    List<String> uniqueNames2 = persons
            .stream()
            .filter(p -> p.getAge() > 30)
            .collect(Collectors.groupingBy(PersonWithAge::getName, Collectors.counting()))
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

    // #17
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

    int sum = numbers.stream().reduce(0, Integer::sum);

    int product = numbers.stream().reduce(1, (a,b) -> a * b);
}
