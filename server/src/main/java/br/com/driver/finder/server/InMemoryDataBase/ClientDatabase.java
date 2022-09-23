package br.com.driver.finder.server.InMemoryDataBase;

import java.util.HashMap;
import java.util.Map;

public class ClientDatabase {
    private static ClientDatabase clientDatabase;

    private int driverCounter;
    private int passengerCounter;
    private final Map<Integer, ClientEntity> drivers;
    private final Map<Integer, ClientEntity> passengers;

    private ClientDatabase(){
        this.driverCounter = 0;
        this.passengerCounter = 0;
        this.drivers = new HashMap<>();
        this.passengers = new HashMap<>();
    }

    public static ClientDatabase instance(){
        if(ClientDatabase.clientDatabase == null){
            ClientDatabase.clientDatabase = new ClientDatabase();
        }
        return ClientDatabase.clientDatabase;
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

    public ClientEntity updateDriver(ClientEntity driver){
        Integer id = driver.getId();
        return this.drivers.replace(id, driver);
    }
}
