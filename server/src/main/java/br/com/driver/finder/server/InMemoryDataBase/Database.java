package br.com.driver.finder.server.InMemoryDataBase;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database database;

    private int driverCounter;
    private int passengerCounter;
    private final Map<Integer, ClientEntity> drivers;
    private final Map<Integer, ClientEntity> passengers;

    private Database(){
        this.driverCounter = 0;
        this.passengerCounter = 0;
        this.drivers = new HashMap<>();
        this.passengers = new HashMap<>();
    }

    public static Database instance(){
        if(Database.database == null){
            Database.database = new Database();
        }
        return Database.database;
    }

    public ClientEntity saveDriver(ClientEntity driver){
        driver.setId(this.driverCounter);
        driver.setClientType(ClientType.DRIVER);
        driver.setStatus(ClientStatus.AVAILABLE);
        this.drivers.put(driver.getId(), driver);
        this.driverCounter++;
        return driver;
    }
    public ClientEntity savePassenger(ClientEntity passenger){
        passenger.setId(this.passengerCounter);
        passenger.setClientType(ClientType.PASSENGER);
        passenger.setStatus(ClientStatus.IN_RIDE);
        this.passengers.put(passenger.getId(), passenger);
        this.passengerCounter++;
        return passenger;
    }
    public ClientEntity findDriverById(Integer id){
        return this.drivers.get(id);
    }
    public ClientEntity findPassengerById(Integer id){
        return this.passengers.get(id);
    }
    public Map<Integer, ClientEntity> findAllDrivers(){
        return this.drivers;
    }
    public ClientEntity deletePassengerById(Integer id){
        return this.passengers.remove(id);
    }
    public ClientEntity deleteDriverById(Integer id){
        return this.drivers.remove(id);
    }
}
