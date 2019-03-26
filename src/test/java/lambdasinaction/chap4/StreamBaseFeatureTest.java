package lambdasinaction.chap4;

import jun.chu.arch.MyAssert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author jun.chu
 * @date 2019-03-26 14:04
 */
public class StreamBaseFeatureTest {
    @Test
    public void onlyIterateOnce() {
        List<String> title = Arrays.asList("Java8", "In", "Action");
        Stream<String> stream = title.stream();
        stream.forEach(System.out::println);
        //流只能被遍历一遍，消费掉了
        MyAssert.assertException(() -> {
            stream.forEach(System.out::println);
            return null;
        }, IllegalStateException.class, "stream has already been operated upon or closed");
    }
}
