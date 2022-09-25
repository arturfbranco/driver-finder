package br.com.driver.finder.client.Service.OutputService;

import br.com.driver.finder.client.Connection.OutputHandler;
import br.com.driver.finder.client.Connection.ReceiverSocket;
import br.com.driver.finder.client.Connection.SenderSocket;
import br.com.driver.finder.client.GlobalVariables;
import br.com.driver.finder.client.Util.ClientType;
import org.json.JSONObject;

public class OutputService {
    private final OutputHandler outputHandler;

    public OutputService(){
        this.outputHandler = new OutputHandler();
    }

    public void configure(String serverIp, Integer serverPort, Integer listeningPort){
        GlobalVariables.serverIp = serverIp;
        GlobalVariables.serverPort = serverPort;
        GlobalVariables.listeningPort = listeningPort;
    }

    public boolean registerDriver(String name, String address) throws Exception {
        JSONObject request = this.genericClientRequest(name, address);
        request.put("route", "registerDriver");
        JSONObject response = this.outputHandler.sendRequest(request);
        if(response.getString("status").equals("200")){
            System.out.println("Driver registered with success!");
            System.out.println("Name: " + response.getString("driverName"));
            System.out.println("Address:" + response.getString("driverAddress"));
            GlobalVariables.userId = response.getInt("driverId");
            GlobalVariables.clientType = ClientType.DRIVER;
            System.out.println("Waiting for passenger to connect...");
            ReceiverSocket.startListening();
            return true;
        }
        System.out.println("Something wrong happened when connecting to server.");
        return false;
    }
    public boolean findDriver(String name, String address) throws Exception {
        JSONObject request = this.genericClientRequest(name, address);
        request.put("route", "findDriver");
        JSONObject response = this.outputHandler.sendRequest(request);
        if(response.getString("status").equals("200")){
            System.out.println("Driver found with success!");
            System.out.println("Driver's name: " + response.getString("driverName"));
            System.out.println("Driver's address: " + response.getString("driverAddress"));
            GlobalVariables.userId = response.getInt("passengerId");
            GlobalVariables.clientType = ClientType.PASSENGER;
            GlobalVariables.connectedUserId = response.getInt("driverId");
            System.out.println("Driver is one its way to get "
                    + response.getString("passengerName")
                    + " at " + response.getString("passengerAddress") + ".");
            ReceiverSocket.startListening();
            return true;
        }
        if(response.getString("status").equals("404")){
            System.out.println("Something went wrong! " + response.getString("message"));
            return false;
        }
        System.out.println("Something wrong happened when connecting to server.");
        return false;
    }

    public boolean sendMessage(String message) throws Exception {
        JSONObject request = new JSONObject();
        request.put("route", "sendMessage");
        request.put("senderType", GlobalVariables.clientType);
        request.put("from", GlobalVariables.userId);
        request.put("to", GlobalVariables.connectedUserId);
        request.put("message", message);
        JSONObject response = this.outputHandler.sendRequest(request);
        if(response.getString("status").equals("200")){
            System.out.println("Message received.");
            return true;
        }
        System.out.println("Could not deliver message.");
        return false;
    }

    public boolean endRide() throws Exception {
        JSONObject request = new JSONObject();
        request.put("route", "endRide");
        request.put("senderType", GlobalVariables.clientType);
        request.put("from", GlobalVariables.userId);
        request.put("to", GlobalVariables.connectedUserId);
        JSONObject response = this.outputHandler.sendRequest(request);
        if(response.getString("status").equals("200")){
            GlobalVariables.connectedUserId = null;
            System.out.println("Ride finished.");
            if(GlobalVariables.clientType.equals(ClientType.DRIVER)){
                System.out.println("Waiting for a new passenger to connect...");
            } else {
                ReceiverSocket.stopListening();
                GlobalVariables.userId = null;
                System.out.println("Register again to request for another ride.");
            }
            return true;
        }else{
            System.out.println("It was not possible to finish the ride. Try again.");
            return false;
        }
    }

    private JSONObject genericClientRequest(String name, String address){
        JSONObject request = new JSONObject();
        request.put("port", GlobalVariables.listeningPort);
        request.put("name", name);
        request.put("address", address);
        return request;
    }
}
