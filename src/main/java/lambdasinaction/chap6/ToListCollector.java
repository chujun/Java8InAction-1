package lambdasinaction.chap6;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import static java.util.stream.Collector.Characteristics.*;

public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return (list, item) -> list.add(item);
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        return i -> i;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }

    public static void main(String[] args) {
        List<Dish> result = Dish.menu.stream().collect(new ToListCollector<Dish>());
        System.out.println("use custom defined Collector interface:" + result);

        Set<Dish> resultSet = Dish.menu.stream().collect(new ToSetCollector<Dish>());
        System.out.println("use custom defined Collector interface,ToSetCollector:" + result);

        result = Dish.menu.stream().collect(ArrayList::new, List::add, List::addAll);
        System.out.println("use multi method" + result);
    }

    static class ToSetCollector<T> implements Collector<T, List<T>, Set<T>> {

        @Override
        public Supplier<List<T>> supplier() {
            return ArrayList::new;
        }

        @Override
        public BiConsumer<List<T>, T> accumulator() {
            return (list, item) -> list.add(item);
        }

        @Override
        public Function<List<T>, Set<T>> finisher() {
            return i -> new HashSet<>(i);
        }

        @Override
        public Set<Characteristics> characteristics() {
            //关于为何这儿不能添加IDENTITY_FINISH枚举，直接看源码说明和源码collect方法实现即可一目了然
            return Collections.unmodifiableSet(EnumSet.of(CONCURRENT));
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            return (list1, list2) -> {
                list1.addAll(list2);
                return list1;
            };
        }
    }
}
