package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Connection.HttpConnection;
import br.com.driver.finder.server.Util.Constants;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleApi{

    public JSONObject getGeolocation() throws IOException, InterruptedException {
        String response = new HttpConnection().postRequest(Constants.GEOLOCATION_URL + "?key=" + Constants.GOOGLE_API_KEY);
        return new JSONObject(response);
    }

    public JSONObject getFullAddress(String identifier, boolean isReverse) throws IOException, InterruptedException {
        String encodedAddress = URLEncoder.encode(identifier, StandardCharsets.UTF_8);
        String request = Constants.GEOCODING_URL + "?" + (isReverse ? "latlng=" : "address=") + encodedAddress + "&key=" + Constants.GOOGLE_API_KEY;
        String response = new HttpConnection().getRequest(request);
        return new JSONObject(response);
    }
}
