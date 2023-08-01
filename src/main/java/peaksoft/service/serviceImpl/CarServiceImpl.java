package peaksoft.service.serviceImpl;

import peaksoft.dao.CarDao;
import peaksoft.dao.daoImpl.CarDaoImpl;
import peaksoft.model.Car;
import peaksoft.service.CarService;

import java.math.BigDecimal;
import java.util.List;

public class CarServiceImpl implements CarService {
    CarDao carDao=new CarDaoImpl();

    @Override
    public void createEnumColor() {
        carDao.createEnumColor();
    }

    @Override
    public void createTableCar() {
        System.out.println(carDao.createTableCar());
    }

    @Override
    public void insertCar(Car car) {
        carDao.insertCar(car);
    }

    @Override
    public Car getCarById(Long id) {
        return carDao.getCarById(id);
    }

    @Override
    public String updateCar(Long id, Car car) {
        carDao.updateCar(id,car);
        return id+" is successfully updated!";

    }

    @Override
    public String deleteCar(Long id) {
        carDao.deleteCar(id);
        return "Car with id ="+id +" is successfully deleted!";
    }

    @Override
    public List<Car> getCarsBySearch(String brand, String model) {
        return carDao.getCarsBySearch(brand,model);
    }

    @Override
    public List<Car> getCarsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return carDao.getCarsByPriceRange(minPrice, maxPrice);
    }
}
