package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Connection.SenderHandler;
import br.com.driver.finder.server.InMemoryDataBase.ClientEntity;
import br.com.driver.finder.server.InMemoryDataBase.ClientType;
import br.com.driver.finder.server.Util.Constants;
import br.com.driver.finder.server.Util.JsonParserSerializer;
import br.com.driver.finder.server.Util.Util;
import org.json.JSONObject;

public class MessageService {
    private final SenderHandler senderHandler;

    public MessageService(){
        this.senderHandler = new SenderHandler();
    }

    public JSONObject sendMessage(JSONObject request) {
        System.out.println("Preparing to send message...");
        try {
            JSONObject requestToSend = this.buildRequest(request);
            JSONObject receivedResponse = this.senderHandler.sendRequest(requestToSend);
            if(receivedResponse.getString("status").equals("200")){
                return this.buildResponseToSender();
            }
            return JsonParserSerializer.getJsonStatus500();
        }catch (Exception e){
            return JsonParserSerializer.getJsonStatus500();
        }
    }

    private JSONObject buildResponseToSender(){
        JSONObject response = new JSONObject();
        response.put("status", "200");
        return response;
    }

    private JSONObject buildRequest(JSONObject request) throws Exception {
        ClientType clientType = ClientType.valueOf(request.getString("senderType").toUpperCase());
        ClientEntity from = Util.getClient(clientType, request.getInt("from"));
        ClientEntity to = Util.getClient(Util.getReceiverClientType(clientType), request.getInt("to"));
        if(from == null || to == null){
            System.err.println("Invalid client IDs.");
            throw new Exception();
        }
        String message = request.getString("message");
        System.out.println("Sending message from " + from.getName() + " to " + to.getName() + ".\nMessage: " + message);
        JSONObject requestToSend = new JSONObject();
        requestToSend.put("clientIp", to.getClientIp());
        requestToSend.put("clientReceiverPort", to.getClientReceiverPort());
        requestToSend.put("route", Constants.RECEIVE_MESSAGE_REQUEST);
        requestToSend.put("from", from.getName());
        requestToSend.put("message", message);
        return requestToSend;
    }
}
