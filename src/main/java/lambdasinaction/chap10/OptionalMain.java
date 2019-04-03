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
        System.out.println(nullSafeFindCheapestInsurance(person, optCar));
        System.out.println(nullSafeFindCheapestInsurance2(person, optCar));
        System.out.println(nullSafeFindCheapestInsurance3(person, optCar));
    }

    private static String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unknown");
    }

    private static Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> persion, Optional<Car> car) {
        if (persion.isPresent() && car.isPresent()) {
            return Optional.ofNullable(findCheapestInsurance(persion.get(), car.get()));
        }
        return Optional.empty();
    }

    private static Optional<Insurance> nullSafeFindCheapestInsurance2(Optional<Person> persion, Optional<Car> car) {
        return persion.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    private static Optional<Insurance> nullSafeFindCheapestInsurance3(Optional<Person> persion, Optional<Car> car) {
        return persion.flatMap(p -> car.flatMap(c -> findCheapestInsuranceOptional(p, c)));
    }

    private static Insurance findCheapestInsurance(Person person, Car car) {
        //不同的保险公司提供的查询服务
        //对比所有数据
        return new Insurance();
    }

    private static Optional<Insurance> findCheapestInsuranceOptional(Person person, Car car) {
        return Optional.of(new Insurance());
    }
}
