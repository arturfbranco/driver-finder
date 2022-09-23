package br.com.driver.finder.server.Util;

import br.com.driver.finder.server.Exception.AddressNotFoundException;
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
}
