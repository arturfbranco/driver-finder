package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Util.Constants;
import org.json.JSONObject;

public class Router {

    private final DriverService driverService;
    private final PassengerService passengerService;
    private final MessageService messageService;
    private final FinalizerService finalizerService;

    public Router(){
        this.driverService = new DriverService();
        this.passengerService = new PassengerService();
        this.messageService = new MessageService();
        this.finalizerService = new FinalizerService();
    }
    public JSONObject callService(JSONObject request){
        if(request.getString("status").equals("400")){
            return request;
        }
        return this.routeRequest(request);
    }
    private JSONObject routeRequest(JSONObject jsonObject){
        switch (jsonObject.getString("route")){
            case Constants.REGISTER_DRIVER_ROUTE:
                return this.driverService.registerDriver(jsonObject);
            case Constants.FIND_DRIVER_ROUTE:
                return this.passengerService.findDriver(jsonObject);
            case Constants.SEND_MESSAGE_ROUTE:
                return this.messageService.sendMessage(jsonObject);
            case Constants.END_RIDE_ROUTE:
                return this.finalizerService.finalizeRide(jsonObject);
            default:
                JSONObject errorJson = new JSONObject();
                errorJson.put("status", "400");
                return errorJson;
        }
    }
}
