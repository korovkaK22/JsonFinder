package utils;

import com.github.javafaker.Faker;
import com.jsonproject.finder.entity.TaxiDriver;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TaxiDriverCreator {
    private final Random random;
    private final Faker faker;

    public TaxiDriverCreator(Random random) {
        this.random = random;

        faker = new Faker(random);
    }

    public TaxiDriver getTaxiDriver(){
        String name = faker.name().firstName();
        String company = faker.company().name();
        int age = faker.number().numberBetween(20,50);
        int drivingExperience = faker.number().numberBetween(1, age - 10 < 18 ? 2 : 10);
        long salary = faker.number().numberBetween(2000, 10000);
        int carAmount = random.nextInt(0, 6);
        List<String> cars = new ArrayList<>(carAmount);
        for (int i=0; i <= carAmount; i++){
            cars.add(getCar(random.nextInt(0, 38)));
        }
        return new TaxiDriver(name, company, age, drivingExperience, salary, String.join( ", ", cars));
    }



    private static String getCar(int number){
        List<String> cars = new ArrayList<>(37);
        cars.add("Toyota Corolla");
        cars.add("Ford Focus");
        cars.add("Honda Civic");
        cars.add("Chevrolet Impala");
        cars.add("Ford Fusion");
        cars.add("Hyundai Elantra");
        cars.add("Volkswagen Golf");
        cars.add("Nissan Sentra");
        cars.add("Toyota Camry");
        cars.add("Honda Accord");
        cars.add("Hyundai Sonata");
        cars.add("Nissan Altima");
        cars.add("Dodge Charger");
        cars.add("Chevrolet Malibu");
        cars.add("Kia Optima");
        cars.add("BMW 3 Series");
        cars.add("Toyota Prius");
        cars.add("Volkswagen Jetta");
        cars.add("Kia Soul");
        cars.add("Honda Fit");
        cars.add("Ford Mustang");
        cars.add("Chevrolet Camaro");
        cars.add("Audi A4");
        cars.add("Mercedes-Benz C-Class");
        cars.add("Tesla Model S");
        cars.add("Porsche 911");
        cars.add("Subaru Impreza");
        cars.add("Mazda3");
        cars.add("Lexus IS");
        cars.add("Infiniti Q50");
        cars.add("Acura TLX");
        cars.add("Cadillac ATS");
        cars.add("Jaguar XE");
        cars.add("Alfa Romeo Giulia");
        cars.add("Volvo S60");
        cars.add("Lincoln MKZ");
        cars.add("Buick LaCrosse");
        cars.add("Chrysler 300");
        return cars.get(number);
    }
}
