package lambdasinaction.chap5;

import lambdasinaction.chap4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static lambdasinaction.chap4.Dish.menu;

public class Reducing {

    public static void main(String... args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        int max = numbers.stream().reduce(0, Math::max);
        System.out.println(max);

        Optional<Integer> min = numbers.stream().reduce(Math::min);
        min.ifPresent(System.out::println);

        int calories = menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);
        System.out.println("Number of calories:" + calories);

        int dishSize = menu.stream().map(d -> 1).reduce(0, Integer::sum);
        System.out.println("dishSize:" + dishSize);
        long count = menu.stream().count();
        System.out.println(count);
    }
}
