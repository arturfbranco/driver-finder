package br.com.driver.finder.server.Connection;

import br.com.driver.finder.server.Service.Router;
import br.com.driver.finder.server.Util.JsonParserSerializer;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable{

    private final Router router;
    private final Socket socket;


    public RequestHandler(Socket socket){
        this.router = new Router();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Request received from: " + this.getClientIp() + ".");
            BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(this.socket.getOutputStream());
            String request = inputBufferedReader.readLine();
            JSONObject jsonRequest = JsonParserSerializer.parseString(request, false);
            jsonRequest.put("clientIp", this.getClientIp());
            JSONObject jsonResponse = this.router.callService(jsonRequest);
            outputStream.writeBytes(JsonParserSerializer.serializeJson(jsonResponse));
            this.socket.close();
            System.out.println("Closing connection...\n\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getClientIp(){
        String fullIp = this.socket.getRemoteSocketAddress().toString();
        return fullIp.substring(1, fullIp.indexOf(':'));
    }
}
