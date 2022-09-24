package br.com.driver.finder.server.Service;

import br.com.driver.finder.server.Util.Constants;
import br.com.driver.finder.server.Util.JsonParserSerializer;
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
        try {
            if(request.getString("status").equals("200")){
                System.out.println("Service called with success.");
                return this.routeRequest(request);
            }
            return JsonParserSerializer.getJsonStatus400();
        } catch (Exception e) {
            System.out.println("Internal server error: " + e.getMessage());
            JSONObject errorObject = JsonParserSerializer.getJsonStatus500();
            errorObject.put("message", e.getMessage());
            return errorObject;
        }
    }
    private JSONObject routeRequest(JSONObject jsonObject) throws Exception {
        String route = jsonObject.getString("route");
        System.out.println("Routing request to: " + route);
        switch (route){
            case Constants.REGISTER_DRIVER_ROUTE:
                return this.driverService.registerDriver(jsonObject);
            case Constants.FIND_DRIVER_ROUTE:
                return this.passengerService.findDriver(jsonObject);
            case Constants.SEND_MESSAGE_ROUTE:
                return this.messageService.sendMessage(jsonObject);
            case Constants.END_RIDE_ROUTE:
                return this.finalizerService.finalizeRide(jsonObject);
            default:
                System.out.println("Requested route does not exist.");
                return JsonParserSerializer.getJsonStatus400();
        }
    }
}
