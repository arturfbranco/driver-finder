package br.com.driver.finder.server.InMemoryDataBase;

import java.util.ArrayList;
import java.util.List;

public class DriversDatabase {
    private static DriversDatabase driversDatabase;

    private int counter;
    private final List<DriverEntity> drivers;

    private DriversDatabase(){
        this.counter = 0;
        this.drivers = new ArrayList<>();
    }

    public static DriversDatabase getDriversDatabase(){
        if(DriversDatabase.driversDatabase == null){
            DriversDatabase.driversDatabase = new DriversDatabase();
        }
        return DriversDatabase.driversDatabase;
    }

    public DriverEntity addDriver(DriverEntity driver){
        driver.setId(this.counter);
        this.counter++;
        this.drivers.add(driver);
        return driver;
    }

    public DriverEntity getDriverById(int id){
        return this.drivers
                .stream()
                .filter(driver -> driver.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<DriverEntity> getDrivers() {
        return drivers;
    }
}
