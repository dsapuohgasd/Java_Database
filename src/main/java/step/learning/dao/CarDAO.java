package step.learning.dao;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.checkerframework.checker.units.qual.C;
import step.learning.entities.Car;
import step.learning.entities.User;
import step.learning.hash.HashService;
import step.learning.services.DataService;
import step.learning.services.EmailService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Singleton
public class CarDAO {

    private final Connection connection;

    @Inject
    public CarDAO(DataService dataService) {
        this.connection = dataService.getConnection();
    }

    public boolean add(Car car){
        // генерируем id для новой записи
        String id = UUID.randomUUID().toString();
        String sql = " INSERT INTO car(`Id`, `Model`, `Brand`, `Year`,`Color`,`Price`,`Description`) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1, id);
            prep.setString(2, car.getModel());
            prep.setString(3, car.getBrand());
            prep.setInt(   4, car.getYear());
            prep.setString(5, car.getColor());
            prep.setDouble(6, car.getPrice());
            prep.setString(7, car.getDescription());

            prep.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public List<Car> loadCars() {
        List<Car> cars= new ArrayList<>();
        String car = "SELECT * FROM car";
        try (PreparedStatement prep = connection.prepareStatement(car)){
            ResultSet res = prep.executeQuery();
          while (res.next()){
            Car buff= new Car(res);
            cars.add(buff);
          }
          return cars;

        } catch (SQLException e) {
            System.out.println("DB connection error! "+ e.getMessage());
        }
        return null;
    }
}
