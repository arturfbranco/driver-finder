package br.com.driver.finder.client.Service.InputService;

import br.com.driver.finder.client.Util.JsonParserSerializer;
import org.json.JSONObject;

public class Router {

    private final InputService inputService;

    public Router(){
        this.inputService = new InputService();
    }

    public JSONObject callService(JSONObject request) throws Exception {
        try {
            return this.route(request);
        }catch (Exception e){
            System.out.println("Not possible to process request received from server.");
            throw new Exception();
        }
    }
    private JSONObject route(JSONObject request){
        String route = request.getString("route");
        switch (route){
            case "passengerConnection":
                return this.inputService.passengerConnection(request);
            case "receiveMessage":
                return this.inputService.receiveMessage(request);
            case "rideFinished":
                return this.inputService.rideFinished();
            default:
                return JsonParserSerializer.getJsonStatus400();
        }
    }
}
