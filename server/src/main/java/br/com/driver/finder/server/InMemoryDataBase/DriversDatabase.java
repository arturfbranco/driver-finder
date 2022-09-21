package br.com.driver.finder.server.InMemoryDataBase;

import java.util.HashMap;
import java.util.Map;

public class DriversDatabase {
    private static DriversDatabase driversDatabase;

    private int counter;
    private final Map<Integer, DriverEntity> drivers;

    private DriversDatabase(){
        this.counter = 0;
        this.drivers = new HashMap<>();
    }

    public static DriversDatabase instance(){
        if(DriversDatabase.driversDatabase == null){
            DriversDatabase.driversDatabase = new DriversDatabase();
        }
        return DriversDatabase.driversDatabase;
    }

    public DriverEntity addDriver(DriverEntity driver){
        driver.setId(this.counter);
        this.drivers.put(driver.getId(), driver);
        this.counter++;
        return driver;
    }

    public DriverEntity getDriverById(Integer id){
        return this.drivers.get(id);
    }

    public Map<Integer, DriverEntity> getDrivers() {
        return this.drivers;
    }
}
