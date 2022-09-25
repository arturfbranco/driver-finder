package br.com.driver.finder.client.Connection;

import br.com.driver.finder.client.Service.InputService.Router;
import br.com.driver.finder.client.Util.JsonParserSerializer;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputHandler implements Runnable {

    private final Socket socket;
    private final Router router;

    public InputHandler(Socket socket){
        this.socket = socket;
        this.router = new Router();
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            String request = bufferedReader.readLine();
            JSONObject response = this.router.callService(JsonParserSerializer.parseString(request, false));
            dataOutputStream.writeBytes(JsonParserSerializer.serializeJson(response));
            this.socket.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
