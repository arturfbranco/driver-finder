package br.com.driver.finder.server.Connection;

import br.com.driver.finder.server.Connection.Socket.SenderSocket;
import br.com.driver.finder.server.Util.JsonParserSerializer;

import org.json.JSONObject;


public class SenderHandler {
    private final SenderSocket senderSocket;

    public SenderHandler(){
        this.senderSocket = new SenderSocket();
    }

    public JSONObject sendRequest(JSONObject jsonRequest){
        try{
            String serializedRequest = JsonParserSerializer.serializeJson(jsonRequest);
            String serializedResponse = this.senderSocket.sendRequest(
                    serializedRequest,
                    jsonRequest.getString("clientIp"),
                    jsonRequest.getInt("clientReceiverPort")
            );
            System.out.println("Request sent to IP " + jsonRequest.getString("clientIp") + " on port " + jsonRequest.getInt("clientReceiverPort") + ".");
            return JsonParserSerializer.parseString(serializedResponse, true);
        }catch (Exception e){
            return JsonParserSerializer.getJsonStatus400();
        }

    }
}
