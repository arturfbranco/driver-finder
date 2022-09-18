package br.com.driver.finder.server.Service;

import org.json.JSONObject;

import java.io.IOException;

public class AddressService {
    private final GoogleApi googleApi;

    public AddressService(){
        this.googleApi = new GoogleApi();
    }

    public JSONObject buildAddress(String address){
        JSONObject jsonResponse = new JSONObject();
        try{
            JSONObject originalAddress = this.getEnrichedAddress(address);
            jsonResponse.put("address", this.reduceAddress(originalAddress));
            jsonResponse.put("status", "200");
        }catch (Exception e) {
            jsonResponse.put("status", "500");
        }
        return jsonResponse;
    }
    private JSONObject getEnrichedAddress(String address) throws IOException, InterruptedException {
        return address.isEmpty()
                ? this.getFromGeolocation()
                : this.getFromAddress(address);
    }

    private JSONObject getFromGeolocation() throws IOException, InterruptedException {
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

    private JSONObject reduceAddress(JSONObject json){
        JSONObject reducedAddress = new JSONObject();
        JSONObject originalAddress = json.getJSONObject("address");
        reducedAddress.put("formattedAddress", originalAddress.get("formatted_address"));
        JSONObject location = originalAddress.getJSONObject("geometry").getJSONObject("location");
        reducedAddress.put("latitude", location.get("lat"));
        reducedAddress.put("longitude", location.get("lng"));
        return reducedAddress;
    }
}
