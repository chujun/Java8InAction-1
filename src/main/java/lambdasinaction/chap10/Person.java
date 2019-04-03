package lambdasinaction.chap10;

import lombok.Data;

import java.util.*;

@Data
public class Person {
    private int           age;

    private Optional<Car> car;
}
