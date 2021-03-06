package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        Predicate<Person> firstNameCondition = person -> person.getFirstName().equalsIgnoreCase("Erik");
        List<Person> personsWithErikName = storage.findMany(firstNameCondition);
        personsWithErikName.forEach(person -> System.out.println(person));
        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        Predicate<Person> findALLFemale = person -> person.getGender() == Gender.FEMALE;
        List<Person> females = storage.findMany(findALLFemale);
        females.forEach(person -> System.out.println(person));
        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        Predicate<Person> bornAfter = person -> person.getBirthDate().isAfter(LocalDate.of(2000, 01, 01));
        List<Person> whoBorn = storage.findMany(bornAfter);
        whoBorn.forEach(person -> System.out.println(person));
        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        Predicate<Person> personId = person -> person.getId() == 123;
        Person findId = storage.findOne(personId);
        System.out.println(findId);
        System.out.println("----------------------");

    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        Predicate<Person> findOnePredicate = person -> person.getId() == 456;
        Function<Person, String> toString = person -> "name:  " + person.getFirstName() + " " + person.getLastName()
                + " born " + person.getBirthDate();

        String findPersonById = storage.findOneAndMapToString(findOnePredicate, toString);
        System.out.println(findPersonById);
        System.out.println("----------------------");
    }

    /*
        6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message) {
        System.out.println(message);
        Predicate<Person> AllMale = person -> person.getFirstName().startsWith("E") && person.getGender() == Gender.MALE;
        Function<Person, String> personStringFunction = person -> person.getFirstName() + " " + person.getLastName();
        List<String> stringList = storage.findManyAndMapEachToString(AllMale, personStringFunction);
        stringList.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        Function<Person, String> mapper = person -> person.getFirstName() + " " + person.getLastName() + " "
                + Period.between(person.getBirthDate(), LocalDate.now()) + "years";
        Predicate<Person> personPredicate = person -> Period.between(person.getBirthDate(), LocalDate.now()).getYears() < 10;
        List<String> result = storage.findManyAndMapEachToString(personPredicate, mapper);
        result.forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        Predicate<Person> allPeople = person -> person.getFirstName().contains("Ulf");
        Consumer<Person> personConsumer = person -> System.out.println(person.getFirstName() + " " + person.getLastName());
        storage.findAndDo(allPeople, personConsumer);
        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        Predicate<Person> LastName = person -> person.getLastName().startsWith(person.getFirstName());
        Consumer<Person> personConsumer = person -> System.out.println(person.getFirstName() + " " + person.getLastName());
        storage.findAndDo(LastName, personConsumer);
        System.out.println("----------------------");
    }

    /*
        10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        Predicate<Person> personFirstNamePalindrome = person -> new StringBuilder(person.getFirstName())
                .reverse().toString().equalsIgnoreCase(person.getFirstName());
        Consumer<Person> pinter = person -> System.out.println(person.getFirstName() + " " + person.getLastName());
        storage.findAndDo(personFirstNamePalindrome, pinter);
        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        Predicate<Person> firstNameA = person -> person.getFirstName().startsWith("A") && person.getBirthDate() == person.getBirthDate();
        Comparator<Person> firstName = (o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName());
        Comparator<Person> birthdate = (o1, o2) -> o1.getBirthDate().compareTo(o2.getBirthDate());
        storage.findAndSort(firstNameA, birthdate).forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        Predicate<Person> FindWhoBornBefore1950 = person -> person.getBirthDate().isBefore(LocalDate.of(1950, 01, 1));
        Comparator<Person> personComparator = (o1, o2) -> o1.getBirthDate().compareTo(o2.getBirthDate());
        storage.findAndSort(FindWhoBornBefore1950, personComparator).forEach(System.out::println);
        System.out.println("----------------------");
    }

    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        Comparator<Person> firstNameComparator = Comparator.comparing(Person::getFirstName);
        Comparator<Person> lastNameComparator = Comparator.comparing(Person::getLastName);
        Comparator<Person> birthDatePersonComparator = Comparator.comparing(Person::getBirthDate);
        List<Person> personList = storage.findAndSort(lastNameComparator.thenComparing(firstNameComparator).thenComparing(birthDatePersonComparator));
        System.out.println("----------------------");
        personList.forEach(System.out::println);
        System.out.println("----------------------");


    }
}

