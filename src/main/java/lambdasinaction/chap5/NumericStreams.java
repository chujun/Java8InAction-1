package lambdasinaction.chap5;

import lambdasinaction.chap4.*;

import java.util.stream.*;
import java.util.*;

import static lambdasinaction.chap4.Dish.menu;

public class NumericStreams {

    public static void main(String... args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);

        Arrays.stream(numbers.toArray()).forEach(System.out::println);
        int calories = menu.stream().mapToInt(Dish::getCalories).sum();
        System.out.println("Number of calories:" + calories);

        // max and OptionalInt
        OptionalInt maxCalories = menu.stream().mapToInt(Dish::getCalories).max();
        //get default value is stream is empty 
        int max = maxCalories.orElse(1);
        System.out.println(max);

        // numeric ranges
        IntStream evenNumbers = IntStream.range(1, 100).filter(n -> n % 2 == 0);
        System.out.println(evenNumbers.count());
        evenNumbers = IntStream.rangeClosed(1, 100).filter(n -> n % 2 == 0);

        System.out.println(evenNumbers.count());

        //pythagorean triple practice
        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
                        .map(b -> new int[] { a, b, (int) Math.sqrt(a * a + b * b) }));

        pythagoreanTriples.limit(5).forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

    }

    public static boolean isPerfectSquare(int n) {
        return Math.sqrt(n) % 1 == 0;
    }

}
