package br.com.driver.finder.server.Connection;

import br.com.driver.finder.server.Connection.Socket.SenderSocket;
import br.com.driver.finder.server.Util.JsonParserSerializer;
import org.json.JSONObject;

import java.net.Socket;

public class SenderHandler {
    private final SenderSocket senderSocket;

    public SenderHandler(Socket socket){
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
            return new JSONObject(serializedResponse);
        }catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", "400");
            return jsonObject;
        }

    }
}
