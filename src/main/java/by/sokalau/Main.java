package by.sokalau;

import by.sokalau.model.Animal;
import by.sokalau.model.Car;
import by.sokalau.model.Flower;
import by.sokalau.model.House;
import by.sokalau.model.Person;
import by.sokalau.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("\nTask1:");
        task1();
        System.out.println("\nTask2:");
        task2();
        System.out.println("\nTask3:");
        task3();
        System.out.println("\nTask4:");
        task4();
        System.out.println("\nTask5:");
        task5();
        System.out.println("\nTask6:");
        task6();
        System.out.println("\nTask7:");
        task7();
        System.out.println("\nTask8:");
        task8();
        System.out.println("\nTask9:");
        task9();
        System.out.println("\nTask10:");
        task10();
        System.out.println("\nTask11:");
        task11();
        System.out.println("\nTask12:");
        task12();
        System.out.println("\nTask13:");
        task13();
        System.out.println("\nTask14:");
        task14();
        System.out.println("\nTask15:");
        task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip(2*7)
                .limit(7)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> "Japanese".equals(animal.getOrigin()))
                .peek(animal -> animal.setBread(animal.getBread().toUpperCase()))
                .filter(animal -> "Female".equals(animal.getGender()))
                .map(Animal::getBread)
                .forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> "Female".equals(animal.getGender()))
                .count());
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> "Hungarian".equals(animal.getOrigin())));
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .allMatch(animal -> "Male".equals(animal.getGender())));
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        System.out.println(animals.stream()
                .filter(animal -> "Oceania".equals(animal.getOrigin()))
                .findAny().orElse(null));
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .max(Comparator.comparingInt(Animal::getAge))
                .ifPresent(animal -> System.out.println(animal.getAge()));
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .min(Comparator.comparingInt(chars -> chars.length))
                .ifPresent(chars -> System.out.println(chars.length));
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .mapToInt(Animal::getAge)
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> "Indonesian".equals(animal.getOrigin()))
                .mapToDouble(Animal::getAge)
                .average().ifPresent(System.out::println);
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();

        Collection<Person> personCollection =  people.stream()
                .filter(person -> "Male".equals(person.getGender()))
                .filter(person -> ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) >= 18
                        && ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) <= 27)
                .toList();

        Stream<Person> categoryOne = personCollection.stream().filter(person -> person.getRecruitmentGroup() == 1);
        Stream<Person> categoryTwo = personCollection.stream().filter(person -> person.getRecruitmentGroup() == 2);
        Stream<Person> categoryThree = personCollection.stream().filter(person -> person.getRecruitmentGroup() == 3);

        Stream.concat(Stream.concat(categoryOne, categoryTwo), categoryThree)
                .limit(200)
                .forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();

        Stream<Person> personFromHospital = houses.stream()
                .filter(house -> "Hospital".equals(house.getBuildingType()))
                .map(House::getPersonList)
                .flatMap(Collection::stream);
        Stream<Person> childrenAndRetiredPersons = houses.stream()
                .skip(1)
                .map(House::getPersonList)
                .flatMap(Collection::stream)
                .filter(person -> ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) < 18 ||
                                ("Female".equals(person.getGender()) &&
                                        ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) >= 58) ||
                        ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) >= 63);
        Stream<Person> otherPersons = houses.stream()
                .skip(1)
                .map(House::getPersonList)
                .flatMap(Collection::stream)
                .filter(person -> ("Female".equals(person.getGender()) &&
                        ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) >= 18 &&
                        ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) < 58) ||
                        (ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) >= 18 &&
                        ChronoUnit.YEARS.between(person.getDateOfBirth(), LocalDate.now()) < 63));
        Stream.concat(Stream.concat(personFromHospital, childrenAndRetiredPersons), otherPersons)
                .limit(500)
                .forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        //наделал предикатов по условиям
        Predicate<Car> predicate1 = car -> "Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor());
        Predicate<Car> predicate2 = car -> car.getMass() < 1500 ||
                (
                        "BMW".equals(car.getCarMake()) ||
                                "Lexus".equals(car.getCarMake()) ||
                                "Chrysler".equals(car.getCarMake()) ||
                                "Toyota".equals(car.getCarMake())
                );
        Predicate<Car> predicate3 = car -> ("Black".equals(car.getColor()) && car.getMass() > 4000) ||
                (
                        "GMC".equals(car.getCarMake()) ||
                                "Dodge".equals(car.getCarMake())
                );
        Predicate<Car> predicate4 = car -> car.getReleaseYear() < 1982 ||
                (
                        "Civic".equals(car.getCarModel()) ||
                                "Cherokee".equals(car.getCarModel())
                );
        Predicate<Car> predicate5 = car ->
                !("Yellow".equals(car.getColor()) ||
                        "Red".equals(car.getColor()) ||
                        "Green".equals(car.getColor()) ||
                        "Blue".equals(car.getColor())) || car.getPrice() < 40000;
        Predicate<Car> predicate6 = car -> car.getVin().contains("59");
        //в лист их загрузил
        List<Predicate<Car>> predicateList = List.of(
                predicate1, predicate2, predicate3, predicate4, predicate5, predicate6
        );
        //сюда записл нужные мне цифры
        AtomicInteger echelonNumber = new AtomicInteger(1);
        AtomicReference<Double> totalRevenue = new AtomicReference<>(0D);
        final double expensesValue = 7.14D;
       //побежал по предикатам
        predicateList.forEach(predicate -> {
            List<Car> carsForRemove = cars.stream()
                    .filter(predicate)
                    .toList();

            long totalPrice = carsForRemove.parallelStream()
                    .mapToLong(Car::getPrice)
                    .sum();
            double totalMass = carsForRemove.parallelStream()
                    .mapToDouble(Car::getMass)
                    .sum();
            double totalExpenses = totalMass/1000*expensesValue;
            //вывожу инфо по каждому предикату, предикат = эшелон
            System.out.println("Echelon" + echelonNumber + " info:" +
                    "\nTotal mass:" + totalMass +
                    "\nTotal expenses:" + totalExpenses +
                    "\nTotal profit:" + (totalPrice - totalExpenses));

            //тут считаются числа
            totalRevenue.getAndUpdate(v -> v + (totalPrice - totalExpenses));
            echelonNumber.getAndIncrement();
            //тут удаляю лист полученый по предиакату, тем самым меняю колекцию
            cars.removeAll(carsForRemove);
        });
        //тут общую выручку вывожу
        System.out.println("\nTotal revenue:" + totalRevenue.get());
    }

    private static void task15() throws IOException {

        Comparator<Flower> comparator = Comparator
                .comparing(Flower::getOrigin, Comparator.reverseOrder())
                .thenComparing(Flower::getPrice, Comparator.reverseOrder())
                .thenComparing(Flower::getWaterConsumptionPerDay, Comparator.reverseOrder());

        Predicate<Flower> predicateSC= flower ->
                flower.getCommonName().charAt(0) <= 83 &&
                        flower.getCommonName().charAt(0) > 67;

        Predicate<Flower> predicateShadowAndVase = flower -> flower.isShadePreferred() &&
                flower.getFlowerVaseMaterial().stream()
                        .anyMatch(vase -> "Glass".equals(vase) ||
                                "Aluminum".equals(vase) ||
                                "Steel".equals(vase));
        List<Flower> flowers = Util.getFlowers();

        long fiveYear = ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.now().plusYears(5));
        AtomicReference<Double> totalSum = new AtomicReference<>(0D);

        flowers.stream()
                .sorted(comparator)
                .filter(predicateSC.and(predicateShadowAndVase))
                .forEach(flower -> {
                    System.out.println(
                                    "waterPrice for 5 years + price:" +
                                            ((((flower.getWaterConsumptionPerDay() * fiveYear)/1000)*1.39) + flower.getPrice())
                            );
                    totalSum.updateAndGet(v -> (v + ((((flower.getWaterConsumptionPerDay() * fiveYear) / 1000) * 1.39) + flower.getPrice())));
                    }
                );

        System.out.println("Total sum:" + totalSum);
    }
}