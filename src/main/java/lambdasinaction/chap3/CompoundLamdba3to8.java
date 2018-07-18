package lambdasinaction.chap3;

import lombok.Data;
import org.junit.Test;

import java.util.*;

/**
 * Created by chujun on 2018/7/18.
 */
public class CompoundLamdba3to8 {
    public List<MyDate> getData() {
        List<MyDate> result = new ArrayList<>();
        result.add(new MyDate(new Date()));
        result.add(new MyDate(new Date(10000)));
        //result.add(new MyDate(null));
        result.add(new MyDate(new Date(0)));
        result.add(new MyDate(new Date(100)));
        return result;
    }

    @Test
    public void testComparator() {
        List<MyDate> data = getData();
        //还是会抛出空指针异常的,如果内置currentDate为空的话
        data.sort(Comparator.comparing(myDate -> myDate.getCurrentDate()));
        System.out.println(data);
    }

    @Data
    static class MyDate {
        public MyDate(Date currentDate) {
            this.currentDate = currentDate;
        }

        private Date currentDate;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("MyDate{");
            sb.append("currentDate=").append(null != currentDate ? currentDate.getTime() : null);
            sb.append('}');
            return sb.toString();
        }
    }
}
