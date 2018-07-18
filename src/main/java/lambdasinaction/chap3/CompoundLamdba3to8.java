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
        result.add(new MyDate(new Date(), 1));
        result.add(new MyDate(new Date(10000), 2));
        //result.add(new MyDate(null));
        result.add(new MyDate(new Date(0), 3));
        result.add(new MyDate(new Date(100), 4));
        result.add(new MyDate(new Date(10), 4));
        return result;
    }

    @Test
    public void testComparator() {
        List<MyDate> data = getData();
        //还是会抛出空指针异常的,如果内置currentDate为空的话
        data.sort(Comparator.comparing(MyDate::getCurrentDate));
        System.out.println(data);
        data.sort(Comparator.comparing(MyDate::getIndex).thenComparing(MyDate::getCurrentDate));
        System.out.println(data);

        data.add(new MyDate());
        //TODO:cj 怎么处理空指针的问题呢?
    }

    @Data
    static class MyDate {
        public MyDate() {
        }

        public MyDate(Date currentDate, int index) {
            this.currentDate = currentDate;
            this.index = index;
        }

        private int  index;

        private Date currentDate;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("MyDate{");
            sb.append("index=").append(index);
            sb.append(", currentDate=").append(null == currentDate ? null : currentDate.getTime());
            sb.append('}');
            return sb.toString();
        }
    }
}
