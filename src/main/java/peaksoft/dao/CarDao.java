package peaksoft.dao;

import peaksoft.model.Car;

import java.math.BigDecimal;
import java.util.List;

public interface CarDao {

    void createEnumColor();

    String createTableCar();

    void insertCar(Car car);

    Car getCarById(Long id);

    void updateCar(Long id, Car car);

    void deleteCar(Long id);

    List<Car> getCarsBySearch(String brand, String model);

    List<Car> getCarsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}
