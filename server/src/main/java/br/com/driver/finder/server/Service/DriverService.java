package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Exception.AddressNotFoundException;
import br.com.driver.finder.server.InMemoryDataBase.ClientEntity;
import br.com.driver.finder.server.InMemoryDataBase.ClientDatabase;
import br.com.driver.finder.server.InMemoryDataBase.ClientStatus;
import br.com.driver.finder.server.Util.JsonParserSerializer;
import org.json.JSONObject;

public class DriverService {
    private final ClientDatabase clientDatabase;

    public DriverService(){
        this.clientDatabase = ClientDatabase.instance();
    }

    public JSONObject registerDriver( JSONObject driver ) throws AddressNotFoundException {
        try {
            ClientEntity savedDriver = this.clientDatabase.saveDriver( new ClientEntity( driver ) );
            System.out.println("Driver registered with success:\n" + savedDriver.toString());
            return this.buildResponse(savedDriver);
        }catch ( AddressNotFoundException e ){
            throw e;
        }catch ( Exception e ){
            System.out.println("Internal server error while registering driver.");
            return JsonParserSerializer.getJsonStatus500();
        }
    }

    private JSONObject buildResponse(ClientEntity driver){
        JSONObject response = new JSONObject();
        response.put("status", "200");
        response.put("driverId", driver.getId());
        response.put("driverName", driver.getName());
        response.put("driverAddress", driver.getAddress());
        return response;
    }

    public void setToInRide(ClientEntity driver){
        this.updateDriverStatus(driver, ClientStatus.IN_RIDE);
    }
    public void setToAvailable(ClientEntity driver){
        this.updateDriverStatus(driver, ClientStatus.AVAILABLE);
    }

    private void updateDriverStatus(ClientEntity driver, ClientStatus status){
        driver.setStatus(status);
        ClientDatabase.instance().updateDriver(driver);
    }
}
