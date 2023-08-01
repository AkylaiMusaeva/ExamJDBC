package peaksoft.service;

import peaksoft.model.Car;

import java.math.BigDecimal;
import java.util.List;

public interface CarService {
    void createEnumColor();

    void createTableCar();

    void insertCar(Car car);

    Car getCarById(Long id);

    String updateCar(Long id, Car car);

    String deleteCar(Long id);

    List<Car> getCarsBySearch(String brand, String model);

    List<Car> getCarsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}
