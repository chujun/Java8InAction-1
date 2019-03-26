package lambdasinaction.chap5;

import lambdasinaction.chap4.*;

import java.util.*;
import java.util.stream.Collectors;

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
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> numbers2 = Arrays.asList(6, 7, 8);
        List<int[]> pairs = numbers1.stream()
                .flatMap((Integer i) -> numbers2.stream().map((Integer j) -> new int[] { i, j }))
                .filter(pair -> (pair[0] + pair[1]) % 3 == 0).collect(toList());
        pairs.forEach(pair -> System.out.println("(" + pair[0] + ", " + pair[1] + ")"));
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
