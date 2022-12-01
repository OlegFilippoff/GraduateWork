package data;


import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class DataHelper {
    public static Faker faker = new Faker(new Locale("EN"));

    private DataHelper() {
    }

    public static String plusMonth(int plusMonth) {
        return LocalDate.now().plusMonths(plusMonth).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getCurrentMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getCurrentYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getFirstCard() {
        return "4444 4444 4444 4441";
    }

    public static String getSecondCard() {
        return "4444 4444 4444 4442";
    }

    public static String invalidCard() {
        return faker.numerify("#### #### #### ####");
    }

    public static String invalidCardOneMinus() {
        return faker.numerify("#### #### #### ###");
    }

    public static String invalidCardZero() {
        return "0";
    }

    public static String fullZeroCard() {
        return "0000 0000 0000 0000";
    }

    public static String invalidCardOneDigit() {
        return faker.numerify("#");
    }

    public static String getValidMonth(int plusMonth) {
        return plusMonth(plusMonth);
    }

    public static String getZeroMonth() {
        return "00";
    }


    public static String getInvalidMonth() {
        int month = parseInt(getCurrentMonth());
        month = month + parseInt(faker.numerify("##"));
        return String.valueOf(month);
    }

    public static String getInvalidYear() {
        int years = faker.number().randomDigit();
        if (years == 0) {
            years = years + 1;
        }
        int year = parseInt(getCurrentYear()) - years;
        return String.valueOf(year);
    }

    public static String plusYears(int years) {
        return LocalDate.now().plusYears(years).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getZeroYear() {
        return "00";
    }

    public static String oneDigitYear() {
        return faker.numerify("#");
    }

    public static String treeDigitYear() {
        return faker.numerify("###");
    }

    public static Name getValidHolderName() {
        return faker.name();
    }

    public static String getHolderNamePlusDigits() {
        return faker.name() + faker.numerify(" ###");
    }

    public static String getHolderNamePlusSymbols() {
        return faker.name() + faker.internet().password();
    }

    public static Name getInvalidHolderName() {
        Faker faker1 = new Faker(new Locale("CN"));
        return faker1.name();
    }

    public static String getOneLetterName() {
        return faker.letterify("#");
    }

    public static String getLongName() {
        return String.valueOf(faker.lorem());
    }

}


