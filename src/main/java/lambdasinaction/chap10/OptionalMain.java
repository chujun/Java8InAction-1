package lambdasinaction.chap10;

import java.util.*;

public class OptionalMain {
    public static void main(String[] args) {
        Optional<Car> optCar = Optional.empty();
        System.out.println("empty optional:" + optCar);
        Car car = new Car();
        optCar = Optional.of(car);
        System.out.println("of optional:" + optCar);
        optCar = Optional.ofNullable(null);
        System.out.println("ofNullable optional:" + optCar);

        Optional<Person> person = Optional.of(new Person());
        person.get().setCar(optCar);
        System.out.println("多级null判断验证:" + getCarInsuranceName(person));
    }

    public static String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unknown");
    }
}
