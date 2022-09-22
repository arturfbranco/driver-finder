package br.com.driver.finder.server.Service;

import org.json.JSONObject;

public class Router {
    public JSONObject callService(JSONObject request){
        return new AddressService().buildAddress(request.getString("address"));
    }
}
