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

    public JSONObject registerDriver( JSONObject driver ){
        try {
            ClientEntity savedDriver = this.database.saveDriver( new ClientEntity( driver ) );
            JSONObject jsonResponse = new JSONObject(savedDriver);
            jsonResponse.put("status", "200");
            return jsonResponse;
        }catch ( AddressNotFoundException e ){
            JSONObject notFound = JsonParserSerializer.getJsonStatus500();
            notFound.put("message", e.getMessage());
            return notFound;
        }catch ( Exception e ){
            return JsonParserSerializer.getJsonStatus500();
        }

    }
}
