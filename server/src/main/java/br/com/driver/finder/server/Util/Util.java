package br.com.driver.finder.server.Util;

import br.com.driver.finder.server.Exception.AddressNotFoundException;
import br.com.driver.finder.server.InMemoryDataBase.ClientDatabase;
import br.com.driver.finder.server.InMemoryDataBase.ClientEntity;
import br.com.driver.finder.server.InMemoryDataBase.ClientType;
import br.com.driver.finder.server.Service.AddressService;
import org.json.JSONObject;

public class Util {
    public static JSONObject getAddress(String address) throws AddressNotFoundException {
        JSONObject addressServiceResponse = new AddressService().buildAddress(address);
        if(addressServiceResponse.getString("status").equals("500")){
            throw new AddressNotFoundException();
        }
        return addressServiceResponse.getJSONObject("address");
    }

    public static ClientEntity getClient(ClientType clientType, Integer id) throws Exception {
        switch (clientType){
            case DRIVER:
                return ClientDatabase.instance().findDriverById(id);
            case PASSENGER:
                return ClientDatabase.instance().findPassengerById(id);
            default:
                throw new Exception();
        }
    }

    public static ClientType getReceiverClientType(ClientType senderType) throws Exception {
        switch (senderType){
            case DRIVER:
                return ClientType.PASSENGER;
            case PASSENGER:
                return ClientType.DRIVER;
            default:
                System.out.println("Invalid client type.");
                throw new Exception();
        }
    }
}
