package step.learning.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Car {
    private String id;
    private String model;
    private String brand;
    private int year;
    private String color;
    private double price;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Car(ResultSet res) throws SQLException {
        id = res.getString("id");
        model = res.getString("model");
        brand = res.getString("brand");
        year = res.getInt("year");
        color = res.getString("color");
        price = res.getDouble("price");
        description = res.getString("description");
    }

    public Car(){}
}
