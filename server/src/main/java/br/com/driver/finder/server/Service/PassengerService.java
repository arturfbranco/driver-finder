package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Exception.AddressNotFoundException;
import br.com.driver.finder.server.InMemoryDataBase.ClientEntity;
import br.com.driver.finder.server.Util.JsonParserSerializer;
import org.json.JSONObject;

public class PassengerService {
    public JSONObject findDriver(JSONObject jsonRequest){
        try {
            ClientEntity passenger = new ClientEntity(jsonRequest);
        }catch ( AddressNotFoundException e ){
            JSONObject notFound = JsonParserSerializer.getJsonStatus500();
            notFound.put("message", e.getMessage());
            return notFound;
        }catch ( Exception e ){
            return JsonParserSerializer.getJsonStatus500();
        }

    }
}
