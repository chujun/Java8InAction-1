package lambdasinaction.chap12;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

public class DateTimeExamples {

    private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>() {
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd-MMM-yyyy");
        }
    };

    public static void main(String[] args) {
        useOldDate();
        useLocalDate();
        useChangeable();
        useTemporalAdjuster();
        useDateFormatter();
        useZoneId();
    }

    private static void useZoneId() {
        TimeZone timeZone = TimeZone.getDefault();
        ZoneId zoneId = timeZone.toZoneId();
        //Asia/Shanghai
        System.out.println(zoneId);
        LocalDate date = LocalDate.of(2014, 3, 18);
        LocalDateTime localDateTime = date.atStartOfDay();
        localDateTime = localDateTime.withHour(8);
        System.out.println(localDateTime);
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        System.out.println("zonedDateTime:" + zonedDateTime);

        Instant now = Instant.now();
        ZonedDateTime zonedDateTime1 = now.atZone(zoneId);

        //localDateTime.toInstant(zoneId);
        LocalDateTime timeFromInstant = LocalDateTime.ofInstant(now, zoneId);
        System.out.println("timeFromInstant:" + timeFromInstant);

        ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
        ZoneOffset beijingOffset = ZoneOffset.of("+08:00");
        System.out.println(newYorkOffset + "," + beijingOffset);
        OffsetDateTime dateTimeInNewYork = OffsetDateTime.of(localDateTime, newYorkOffset);
        System.out.println(dateTimeInNewYork);
    }

    private static void useOldDate() {
        Date date = new Date(114, 2, 18);
        System.out.println(date);

        System.out.println(formatters.get().format(date));

        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.FEBRUARY, 18);
        System.out.println(calendar);
    }

    private static void useLocalDate() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        int year = date.getYear(); // 2014
        Month month = date.getMonth(); // MARCH
        int day = date.getDayOfMonth(); // 18
        DayOfWeek dow = date.getDayOfWeek(); // TUESDAY
        int len = date.lengthOfMonth(); // 31 (days in March)
        boolean leap = date.isLeapYear(); // false (not a leap year)
        System.out.println(date);

        int y = date.get(ChronoField.YEAR);
        int m = date.get(ChronoField.MONTH_OF_YEAR);
        int d = date.get(ChronoField.DAY_OF_MONTH);
        System.out.println("ChronoField,year:" + y + ",month:" + m + ",dayOfMonth:" + d);

        LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
        int hour = time.getHour(); // 13
        int minute = time.getMinute(); // 45
        int second = time.getSecond(); // 20
        System.out.println(time);

        date = LocalDate.parse("2014-03-18");
        time = LocalTime.parse("13:45:20");
        System.out.println("parse:" + date + "," + time);
        try {
            System.out.println("parse:" + LocalTime.parse("13:45:"));
        } catch (DateTimeParseException e) {
            //RuntimeException
            System.out.println(e);
            e.printStackTrace();
        }

        LocalDateTime dt0 = LocalDateTime.of(2014, 3, 18, 13, 45, 20); // 2014-03-18T13:45
        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println(dt0 + "," + dt1);

        LocalDate date1 = dt1.toLocalDate();
        System.out.println(date1);
        LocalTime time1 = dt1.toLocalTime();
        System.out.println(time1);

        Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
        Instant now = Instant.now();

        Duration d1 = Duration.between(LocalTime.of(13, 45, 10), time);
        Duration d2 = Duration.between(instant, now);
        System.out.println("Duration seconds:" + d1.getSeconds());
        System.out.println("Duration seconds:" + d2.getSeconds());

        Duration threeMinutes = Duration.ofMinutes(3);
        threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println(threeMinutes);

        Period tenDays = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
        System.out.println("Period:" + tenDays + "," + threeWeeks + "," + twoYearsSixMonthsOneDay);

        JapaneseDate japaneseDate = JapaneseDate.from(date);
        System.out.println(japaneseDate);
    }

    private static void useChangeable() {
        System.out.println("useChangeable:");
        LocalDate date1 = LocalDate.of(2014, 3, 18);
        LocalDate date2 = date1.withYear(2011);
        LocalDate date3 = date2.withDayOfMonth(25);
        LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 9);
        //date1:2014-03-18,date2:2011-03-18,date3:2011-03-25,date4:2011-09-25
        System.out.println("date1:" + date1 + ",date2:" + date2 + ",date3:" + date3 + ",date4:" + date4);

        date1 = LocalDate.of(2014, 3, 18);
        date2 = date1.plusWeeks(1);
        date3 = date2.minusYears(3);
        date4 = date3.plus(6, ChronoUnit.MONTHS);
        //date1:2014-03-18,date2:2014-03-25,date3:2011-03-25,date4:2011-09-25
        System.out.println("date1:" + date1 + ",date2:" + date2 + ",date3:" + date3 + ",date4:" + date4);

    }

    private static void useTemporalAdjuster() {
        System.out.println("useTemporalAdjuster:");
        LocalDate date = LocalDate.of(2019, 4, 4);
        date = date.with(nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date);
        date = date.with(lastDayOfMonth());
        System.out.println(date);
        System.out.println("NextWorkingDay:");
        date = date.with(new NextWorkingDay());
        System.out.println(date);
        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(new NextWorkingDay());
        System.out.println(date);

        date = date.with(nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY)
                dayToAdd = 3;
            if (dow == DayOfWeek.SATURDAY)
                dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });
        System.out.println(date);
        date = date.with(nextWorkingDay());
        System.out.println("nextWorkingDay():" + date);
    }

    private static TemporalAdjuster nextWorkingDay() {
        return TemporalAdjusters.ofDateAdjuster(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY)
                dayToAdd = 3;
            if (dow == DayOfWeek.SATURDAY)
                dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });
    }

    private static class NextWorkingDay implements TemporalAdjuster {
        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY)
                dayToAdd = 3;
            if (dow == DayOfWeek.SATURDAY)
                dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }
    }

    private static void useDateFormatter() {
        System.out.println("useDateFormatter start:");
        LocalDate date = LocalDate.of(2014, 3, 18);
        LocalDateTime localDateTime = date.atTime(23, 12, 23);
        System.out.println(localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println(localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
        //DateTimeFormatter 线程安全
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(date.format(formatter));
        System.out.println(date.format(italianFormatter));

        DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder().appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ").appendText(ChronoField.MONTH_OF_YEAR).appendLiteral(" ")
                .appendText(ChronoField.YEAR).parseCaseInsensitive().toFormatter(Locale.ITALIAN);

        System.out.println("complexFormatter:" + date.format(complexFormatter));
    }

}
