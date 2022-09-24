package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Connection.Http.GoogleApi;
import br.com.driver.finder.server.Util.JsonParserSerializer;
import org.json.JSONObject;

import java.io.IOException;

public class AddressService {
    private GoogleApi googleApi;

    public AddressService(){
        try {
            this.googleApi = new GoogleApi();
        }catch (IOException e){
            this.googleApi = null;
        }
    }

    public JSONObject buildAddress(String address){
        System.out.println("Requesting address to Google API...");
        try{
            JSONObject jsonResponse = new JSONObject();
            JSONObject originalAddress = this.getEnrichedAddress(address);
            jsonResponse.put("address", this.reduceAddress(originalAddress));
            jsonResponse.put("status", "200");
            return jsonResponse;
        }catch (Exception e) {
            return JsonParserSerializer.getJsonStatus500();
        }
    }
    private JSONObject getEnrichedAddress(String address) throws IOException, InterruptedException {
        return address.isEmpty()
                ? this.getFromGeolocation()
                : this.getFromAddress(address);
    }

    private JSONObject getFromGeolocation() throws IOException, InterruptedException {
        System.out.println("No address provided. Tracking user by Geolocation...");
        JSONObject geolocation = this.googleApi.getGeolocation().getJSONObject("location");
        String coordinates = geolocation.get("lat") + "," + geolocation.get("lng");
        JSONObject response = this.googleApi.getFullAddress(coordinates, true);
        return this.getFirstAddress(response);
    }
    private JSONObject getFromAddress(String address) throws IOException, InterruptedException {
        JSONObject response = this.googleApi.getFullAddress(address, false);
        return this.getFirstAddress(response);
    }

    private JSONObject getFirstAddress(JSONObject addresses){
        return addresses.getJSONArray("results").getJSONObject(0);
    }

    private JSONObject reduceAddress(JSONObject originalAddress){
        JSONObject reducedAddress = new JSONObject();
        reducedAddress.put("formattedAddress", originalAddress.get("formatted_address"));
        JSONObject location = originalAddress.getJSONObject("geometry").getJSONObject("location");
        reducedAddress.put("latitude", location.get("lat"));
        reducedAddress.put("longitude", location.get("lng"));
        return reducedAddress;
    }
}
