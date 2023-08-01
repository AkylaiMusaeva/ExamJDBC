package peaksoft;

import peaksoft.config.DatabaseConfig;
import peaksoft.model.Car;
import peaksoft.model.Color;
import peaksoft.service.CarService;
import peaksoft.service.serviceImpl.CarServiceImpl;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        //DatabaseConfig.getConnection();
        CarService carService = new CarServiceImpl();
        while (true) {
            System.out.println("""
                    \n
                    1-create enum color
                    2-create table car
                    3-insert to car
                    4-find car by id
                    5-update car by id
                    7-get car by search
                    8-getCarsByPriceRange
                    """);
            switch (new Scanner(System.in).nextInt()) {
                case 1 -> carService.createEnumColor();
                case 2->carService.createTableCar();
                case 3->{
                    carService.insertCar(
                            new Car("Toyota","Camry",2016, Color.BLUE, BigDecimal.valueOf(490)));
                }
                case 4-> System.out.println(carService.getCarById(1L));
                case 5-> System.out.println(carService.updateCar(1L,
                        new Car("Porshe","S 110",2016,Color.WHITE,BigDecimal.valueOf(500))));
                case 6-> System.out.println(carService.deleteCar(1L));
                case 7->carService.getCarsBySearch("audi","a-6").forEach(System.out::println);
                case 8->carService.getCarsByPriceRange(BigDecimal.valueOf(45),BigDecimal.valueOf(500)).forEach(System.out::println);
            }
        }
    }
}
