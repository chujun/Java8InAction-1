package lambdasinaction.chap10;

import java.util.Optional;

/**
 * @author jun.chu
 * @date 2019-04-03 10:55
 */
public class Filter {
    public static void main(String[] args) {
        Person person = new Person();
        person.setAge(30);
        Car car = new Car();
        Insurance insurance = new Insurance();
        insurance.setName("jun");
        Optional<Insurance> optionalInsurance = Optional.ofNullable(insurance);
        car.setInsurance(optionalInsurance);
        Optional<Car> optCar = Optional.ofNullable(car);
        person.setCar(optCar);
        Optional<Person> optPerson = Optional.ofNullable(person);
        System.out.println(getCarInsuranceName(optPerson, 20));
        System.out.println(getCarInsuranceName(optPerson, 40));

        //还是存在空指针异常额，有点头痛
        person.setCar(null);
        try {
            System.out.println(getCarInsuranceName(optPerson, 20));
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private static String getCarInsuranceName(Optional<Person> person, int minAge) {
        return person.filter(p -> p.getAge() >= minAge).flatMap(Person::getCar).flatMap(Car::getInsurance)
                .map(Insurance::getName).orElse("Unknown");
    }
}
