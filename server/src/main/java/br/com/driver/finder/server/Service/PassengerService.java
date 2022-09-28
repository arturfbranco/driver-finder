package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Connection.SenderHandler;
import br.com.driver.finder.server.Exception.AddressNotFoundException;
import br.com.driver.finder.server.Exception.NoDriverFound;
import br.com.driver.finder.server.InMemoryDataBase.ClientDatabase;
import br.com.driver.finder.server.InMemoryDataBase.ClientEntity;
import br.com.driver.finder.server.InMemoryDataBase.ClientStatus;
import br.com.driver.finder.server.Util.Constants;
import br.com.driver.finder.server.Util.JsonParserSerializer;
import org.json.JSONObject;

import java.util.Collection;

public class PassengerService {

    private final SenderHandler senderHandler;
    private final DriverService driverService;

    public PassengerService(){
        this.senderHandler = new SenderHandler();
        this.driverService = new DriverService();
    }

    public JSONObject findDriver(JSONObject jsonRequest) throws NoDriverFound, AddressNotFoundException {
        try {
            ClientEntity passenger = new ClientEntity(jsonRequest);
            System.out.println("Searching closest available driver for address: " + passenger.getAddress());
            ClientEntity closestDriver = this.getAvailableClosestDriver(passenger.getLatitude(), passenger.getLongitude());
            ClientEntity savedPassenger = ClientDatabase.instance().savePassenger(passenger);
            System.out.println("Driver found: " + closestDriver + "\nTrying to establish connection...");
            JSONObject connectionResponse = this.connectPassengerToDriver(savedPassenger, closestDriver);
            if(connectionResponse.get("status").equals("200")){
                this.driverService.setToInRide(closestDriver);
                System.out.println("Connection established between driver and passenger.");
                return connectionResponse;
            }else{
                System.out.println("Driver unreachable. Looking for another one...");
                ClientDatabase.instance().deleteDriverById(closestDriver.getId());
                return this.findDriver(jsonRequest);
            }
        }catch ( AddressNotFoundException | NoDriverFound e ){
            throw e;
        } catch ( Exception e ){
            return JsonParserSerializer.getJsonStatus500();
        }
    }

    private JSONObject connectPassengerToDriver(ClientEntity passenger, ClientEntity driver) throws Exception {
        JSONObject requestToDriver = this.buildRequestToDriver(passenger, driver);
        JSONObject driverResponse = this.senderHandler.sendRequest(requestToDriver);
        String responseStatus = driverResponse.getString("status");
        if(responseStatus.equals("200")){
            return this.buildResponseToPassenger(passenger, driver);
        }
        return JsonParserSerializer.getJsonStatus500();
    }

    private JSONObject buildResponseToPassenger(ClientEntity passenger, ClientEntity driver){
        JSONObject response = new JSONObject();
        response.put("status", "200");
        response.put("driverId", driver.getId());
        response.put("driverName", driver.getName());
        response.put("driverAddress", driver.getAddress());
        response.put("passengerId", passenger.getId());
        response.put("passengerName", passenger.getName());
        response.put("passengerAddress", passenger.getAddress());
        return response;
    }
    private JSONObject buildRequestToDriver(ClientEntity passenger, ClientEntity driver){
        JSONObject request = new JSONObject();
        request.put("passengerId", passenger.getId());
        request.put("passengerName", passenger.getName());
        request.put("passengerAddress", passenger.getAddress());
        request.put("clientIp", driver.getClientIp());
        request.put("clientReceiverPort", driver.getClientReceiverPort());
        request.put("route", Constants.PASSENGER_CONNECTION_REQUEST);
        return request;
    }

    private ClientEntity getAvailableClosestDriver(Double latitude, Double longitude) throws NoDriverFound {
        Collection<ClientEntity> drivers = ClientDatabase.instance().findAllDrivers().values();
        ClientEntity closestDriver = null;
        Double closestDistance = null;
        for(ClientEntity driver : drivers){
            Double distance = this.calculateDistance(latitude, longitude, driver.getLatitude(), driver.getLongitude());
            if(driver.getStatus().equals(ClientStatus.AVAILABLE) && (closestDistance == null || distance < closestDistance)){
                closestDistance = distance;
                closestDriver = driver;
            }
        }
        if(closestDriver == null){
            throw new NoDriverFound();
        }
        return closestDriver;
    }

    private Double calculateDistance(Double ltA, Double lnA, Double ltB, Double lnB){
        Double theta = lnA - lnB;
        Double thetaInRadians = this.degreeToRadian(theta);
        double latitudeAInRadians = this.degreeToRadian(ltA);
        double latitudeBInRadians = this.degreeToRadian(ltB);
        double distance = Math.sin(latitudeAInRadians) * Math.sin(latitudeBInRadians) + Math.cos(latitudeAInRadians) * Math.cos(latitudeBInRadians) * Math.cos(thetaInRadians);
        distance = Math.acos(distance);
        distance = this.radianToDegree(distance);
        return distance * 1.609344 * 1000;

    }

    private Double degreeToRadian(Double degree){
        return degree * Math.PI / 180.0;
    }

    private Double radianToDegree(Double radian){
        return radian * 180.0 / Math.PI;
    }

}
