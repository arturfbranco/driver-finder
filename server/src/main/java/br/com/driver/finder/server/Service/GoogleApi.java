package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Connection.HttpConnection;
import br.com.driver.finder.server.Util.Constants;
import br.com.driver.finder.server.Util.PropertiesReader;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleApi{

    private final String GOOGLE_API_KEY;
    GoogleApi() throws IOException {
        this.GOOGLE_API_KEY = new PropertiesReader().getProperties().getProperty("google-api-key");
    }

    public JSONObject getGeolocation() throws IOException, InterruptedException {
        String response = new HttpConnection().postRequest(Constants.GEOLOCATION_URL + "?key=" + this.GOOGLE_API_KEY);
        return new JSONObject(response);
    }

    public JSONObject getFullAddress(String identifier, boolean isReverse) throws IOException, InterruptedException {
        String encodedAddress = URLEncoder.encode(identifier, StandardCharsets.UTF_8);
        String request = Constants.GEOCODING_URL + "?" + (isReverse ? "latlng=" : "address=") + encodedAddress + "&key=" + this.GOOGLE_API_KEY;
        String response = new HttpConnection().getRequest(request);
        return new JSONObject(response);
    }
}
