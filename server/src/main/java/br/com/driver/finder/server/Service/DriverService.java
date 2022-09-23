package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Exception.AddressNotFoundException;
import br.com.driver.finder.server.InMemoryDataBase.ClientEntity;
import br.com.driver.finder.server.InMemoryDataBase.Database;
import br.com.driver.finder.server.Util.JsonParserSerializer;
import org.json.JSONObject;

public class DriverService {

    private final AddressService addressService;
    private final Database database;

    public DriverService(){
        this.addressService = new AddressService();
        this.database = Database.instance();
    }

    public JSONObject registerDriver(JSONObject driver){
        try {
            ClientEntity clientEntity = this.buildClientEntity(driver);
            ClientEntity savedDriver = this.database.saveDriver(clientEntity);
            JSONObject jsonResponse = new JSONObject(savedDriver);
            jsonResponse.put("status", "200");
            return jsonResponse;
        }catch (AddressNotFoundException e){
            JSONObject notFound = JsonParserSerializer.getJsonStatus500();
            notFound.put("message", e.getMessage());
            return notFound;
        }catch (Exception e){
            return JsonParserSerializer.getJsonStatus500();
        }

    }
    private ClientEntity buildClientEntity(JSONObject jsonRequest) throws AddressNotFoundException {
        JSONObject data = jsonRequest.getJSONObject("data");
        JSONObject address = this.getAddress(data.getString("address"));

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(data.getString("name"));
        clientEntity.setClientIp(data.getString("clientIp"));
        clientEntity.setClientReceiverPort(data.getInt("port"));
        clientEntity.setLatitude(address.getDouble("latitude"));
        clientEntity.setLongitude(address.getDouble("longitude"));
        clientEntity.setAddress(address.getString("formattedAddress"));
        return clientEntity;

    }

    private JSONObject getAddress(String address) throws AddressNotFoundException {
        JSONObject addressServiceResponse = this.addressService.buildAddress(address);
        if(addressServiceResponse.getString("status").equals("500")){
            throw new AddressNotFoundException();
        }
        return addressServiceResponse.getJSONObject("address");
    }
}
