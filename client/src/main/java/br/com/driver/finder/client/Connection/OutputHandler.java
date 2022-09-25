package br.com.driver.finder.client.Connection;

import br.com.driver.finder.client.Util.JsonParserSerializer;
import org.json.JSONObject;

public class OutputHandler {

    private final SenderSocket senderSocket;

    public OutputHandler(){
        this.senderSocket = new SenderSocket();
    }

    public JSONObject sendRequest(JSONObject request) throws Exception {
        try {
            String serializedRequest = JsonParserSerializer.serializeJson(request);
            String serverResponse = this.senderSocket.sendRequest(serializedRequest);
            return JsonParserSerializer.parseString(serverResponse, true);
        }catch (Exception e){
            System.out.println("Could not connect to server.");
            throw new Exception();
        }
    }
}
