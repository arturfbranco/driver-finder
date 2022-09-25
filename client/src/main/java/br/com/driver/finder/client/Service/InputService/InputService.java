package br.com.driver.finder.client.Service.InputService;

import br.com.driver.finder.client.Connection.ReceiverSocket;
import br.com.driver.finder.client.GlobalVariables;
import br.com.driver.finder.client.Util.ClientType;
import br.com.driver.finder.client.Util.JsonParserSerializer;
import org.json.JSONObject;

public class InputService {

    public JSONObject passengerConnection(JSONObject request){
        try {
            System.out.println("A new passenger has connected and is waiting for you!");
            System.out.println("Name: " + request.getString("passengerName"));
            System.out.println("Address: " + request.getString("passengerAddress"));
            GlobalVariables.connectedUserId = request.getInt("passengerId");
            JSONObject response = new JSONObject();
            response.put("status", "200");
            return response;
        }catch (Exception e){
            return JsonParserSerializer.getJsonStatus400();
        }


    }
    public JSONObject receiveMessage(JSONObject request){
        try {
            String sender = request.getString("from");
            String message = request.getString("message");
            System.out.println(sender + ": " + message);
            JSONObject response = new JSONObject();
            response.put("status", "200");
            return response;
        }catch (Exception e){
            return JsonParserSerializer.getJsonStatus400();
        }

    }
    public JSONObject rideFinished(){
        try {
            GlobalVariables.connectedUserId = null;
            if(GlobalVariables.clientType.equals(ClientType.DRIVER)){
                System.out.println("Ride finished by Passenger.");
                System.out.println("Waiting for a new passenger to connect...");
            }else {
                System.out.println("Ride finished by Driver.");
                ReceiverSocket.stopListening();
                GlobalVariables.userId = null;
                System.out.println("Register again to request for another ride.");
            }
            JSONObject response = new JSONObject();
            response.put("status", "200");
            return response;
        }catch (Exception e){
            return JsonParserSerializer.getJsonStatus500();
        }
    }

}
