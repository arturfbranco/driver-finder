package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Connection.SenderHandler;
import br.com.driver.finder.server.InMemoryDataBase.ClientDatabase;
import br.com.driver.finder.server.InMemoryDataBase.ClientEntity;
import br.com.driver.finder.server.InMemoryDataBase.ClientType;
import br.com.driver.finder.server.Util.Constants;
import br.com.driver.finder.server.Util.Util;
import org.json.JSONObject;

public class FinalizerService {

    private final DriverService driverService;
    private final SenderHandler senderHandler;

    public FinalizerService(){
        this.driverService = new DriverService();
        this.senderHandler = new SenderHandler();
    }

    public JSONObject finalizeRide(JSONObject request) throws Exception {
        ClientType clientType = ClientType.valueOf(request.getString("senderType").toUpperCase());
        ClientEntity from = Util.getClient(clientType, request.getInt("from"));
        ClientEntity to = Util.getClient(Util.getReceiverClientType(clientType), request.getInt("to"));
        if(from == null || to == null){
            System.out.println("Invalid client IDs.");
            throw new Exception();
        }
        this.registerFinalizationInDatabase(from);
        this.registerFinalizationInDatabase(to);
        return this.sendRequestToReceiver(to);
    }

    private JSONObject sendRequestToReceiver(ClientEntity to){
        JSONObject request = new JSONObject();
        request.put("route", Constants.RIDE_FINISHED_REQUEST);
        request.put("clientIp", to.getClientIp());
        request.put("clientReceiverPort", to.getClientReceiverPort());
        return this.senderHandler.sendRequest(request);
    }

    private void registerFinalizationInDatabase(ClientEntity client){
        if(client.getClientType().equals(ClientType.DRIVER)){
            this.driverService.setToAvailable(client);
        } else {
            ClientDatabase.instance().deletePassengerById(client.getId());
        }
    }
}
