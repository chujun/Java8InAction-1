package lambdasinaction.chap5;

import lambdasinaction.chap4.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static lambdasinaction.chap4.Dish.menu;

public class Mapping {

    public static void main(String... args) {

        // map
        List<String> dishNames = menu.stream().map(Dish::getName).collect(toList());
        System.out.println(dishNames);

        // map
        List<String> words = Arrays.asList("Hello", "World");
        List<Integer> wordLengths = words.stream().map(String::length).collect(toList());
        System.out.println(wordLengths);
        printWordDistinctChar(words);

        // flatMap
        List<int[]> pairs = generateNumberPairs().collect(Collectors.toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));
        System.out.println("filter flatmap:");
        pairs = generateNumberPairs().filter(pair -> (pair[0] + pair[1]) % 3 == 0).collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));
    }

    /**
     * 生成数对 (1, 4) (1, 5) (1, 6) (2, 4) (2, 5) (2, 6) (3, 4) (3, 5) (3, 6)
     * 
     * @return
     */
    private static Stream<int[]> generateNumberPairs() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(4, 5, 6);
        return numbers1.stream().flatMap((Integer i) -> numbers2.stream().map((Integer j) -> new int[] { i, j }));
    }

    private static void printWordDistinctChar(List<String> words) {
        System.out.println("printWordDistinctChar start:");
        //需求：打印单词中所有不重复的字母
        //error version1,返回的流类型Stream<String[]>
        System.out.println("error version1:");
        words.stream().map(word -> word.split("")).distinct().forEach(System.out::println);
        //error version2,现在得到的是流的列表（Stream<String>列表）
        System.out.println("error version2:");
        words.stream().map(word -> word.split("")).map(Arrays::stream).distinct().collect(Collectors.toList())
                .forEach(System.out::println);
        // flatMap
        words.stream().map(word -> word.split("")).flatMap(Arrays::stream).distinct().forEach(System.out::println);
        words.stream().flatMap((String line) -> Arrays.stream(line.split(""))).distinct().forEach(System.out::println);
        System.out.println("printWordDistinctChar end");
    }
}
