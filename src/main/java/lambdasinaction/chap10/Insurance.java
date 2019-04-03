package lambdasinaction.chap10;

import lombok.Data;

@Data
public class Insurance {

    private String name;

    public String getName() {
        return name;
    }
}
