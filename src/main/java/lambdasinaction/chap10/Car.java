package lambdasinaction.chap10;

import lombok.Data;

import java.util.*;

@Data
public class Car {

    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
