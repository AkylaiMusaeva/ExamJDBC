package peaksoft.dao.daoImpl;

import peaksoft.config.DatabaseConfig;
import peaksoft.dao.CarDao;
import peaksoft.model.Car;
import peaksoft.model.Color;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private final Connection connection = DatabaseConfig.getConnection();


    @Override
    public void createEnumColor() {
        String sql = """
                create type Color as enum ('WHITE','BLUE','BLACK','BROWN','RED','YELLOW')
                """;
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
            System.out.println("Successfully created enums!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public String createTableCar() {
        String sql = """
                create table cars(
                id serial primary key ,
                brand varchar,
                model varchar,
                year int,
                color Color,
                price numeric
                )
                """;
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "Successfully created table cars!";
    }

    @Override
    public void insertCar(Car car) {
        String sql = """
                insert into cars(brand,model,year,color,price)
                values (?,?,?,?::color,?)
                """;
        try (
                Connection connection = DatabaseConfig.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,car.getBrand());
            preparedStatement.setString(2,car.getModel());
            preparedStatement.setInt(3,car.getYear());
            preparedStatement.setString(4,car.getColor().name());
            preparedStatement.setBigDecimal(5, car.getPrice());
            preparedStatement.executeUpdate();
            System.out.printf("Car with model=%s is successfully saved!",car.getModel());
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    @Override
    public Car getCarById(Long id) {
        Car car=new Car();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                            select * from cars where id=?
                            """);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!(resultSet.next())) {
                throw new RuntimeException("Car with id :" + id + " not found!");
            } else {
                car.setId(resultSet.getLong("id"));
                car.setBrand(resultSet.getString("brand"));
                car.setModel(resultSet.getString("model"));
                car.setYear(resultSet.getInt("year"));
                car.setColor(Color.valueOf(resultSet.getString("color")));
                car.setPrice(resultSet.getBigDecimal("price"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public void updateCar(Long id, Car car) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("""
                update cars set brand=?,model=?,year=?,color=?::color,price=? where id=?
                """);) {
            preparedStatement.setString(1, car.getBrand());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setString(4, car.getColor().name());
            preparedStatement.setBigDecimal(5, car.getPrice());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated car with id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCar(Long id) {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("""
                        delete from cars where id=?
                        """);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Car> getCarsBySearch(String brand, String model) {
        List<Car>cars=new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                            select * from cars where brand ilike ? and model ilike ?
                            """);
            preparedStatement.setString(1,"%"+"brand"+"%");
            preparedStatement.setString(2,"%"+"model"+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!(resultSet.next())) {
                throw new RuntimeException("Car not found!");
            } else {
                while (resultSet.next()) {
                    cars.add(new Car(
                            resultSet.getLong("id"),
                            resultSet.getString("brand"),
                            resultSet.getString("model"),
                            resultSet.getInt("year"),
                            Color.valueOf(resultSet.getString("color")),
                            resultSet.getBigDecimal("price")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }

    @Override
    public List<Car> getCarsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Car>cars=new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                            select * from cars where price between ? and ?
                            """);
            preparedStatement.setBigDecimal(1,minPrice);
            preparedStatement.setBigDecimal(2,maxPrice);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!(resultSet.next())) {
                throw new RuntimeException("Car not found!");
            } else {
                while (resultSet.next()) {
                    cars.add(new Car(
                            resultSet.getLong("id"),
                            resultSet.getString("brand"),
                            resultSet.getString("model"),
                            resultSet.getInt("year"),
                            Color.valueOf(resultSet.getString("color")),
                            resultSet.getBigDecimal("price")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }
}
