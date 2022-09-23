package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Connection.SenderHandler;
import br.com.driver.finder.server.InMemoryDataBase.ClientDatabase;
import br.com.driver.finder.server.InMemoryDataBase.ClientEntity;
import br.com.driver.finder.server.InMemoryDataBase.ClientType;
import br.com.driver.finder.server.Util.Constants;
import br.com.driver.finder.server.Util.JsonParserSerializer;
import org.json.JSONObject;

public class MessageService {
    private final SenderHandler senderHandler;

    public MessageService(){
        this.senderHandler = new SenderHandler();
    }

    public JSONObject sendMessage(JSONObject request) {
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
        ClientEntity from = this.getClient(clientType, request.getInt("from"));
        ClientEntity to = this.getClient(clientType, request.getInt("to"));
        String message = request.getString("message");
        JSONObject requestToSend = new JSONObject();
        requestToSend.put("clientIp", to.getClientIp());
        requestToSend.put("clientReceiverPort", to.getClientReceiverPort());
        requestToSend.put("route", Constants.RECEIVE_MESSAGE_REQUEST);
        requestToSend.put("from", from.getName());
        requestToSend.put("message", message);
        return requestToSend;
    }
    private ClientEntity getClient(ClientType clientType, Integer id) throws Exception {
        switch (clientType){
            case DRIVER:
                return ClientDatabase.instance().findDriverById(id);
            case PASSENGER:
                return ClientDatabase.instance().findPassengerById(id);
            default:
                throw new Exception();
        }
    }
}
