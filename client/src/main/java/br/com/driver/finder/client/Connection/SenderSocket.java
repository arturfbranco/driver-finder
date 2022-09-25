package br.com.driver.finder.client.Connection;

import br.com.driver.finder.client.GlobalVariables;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SenderSocket {

    public String sendRequest(String request) throws IOException {
        Socket socket = new Socket(GlobalVariables.serverIp, GlobalVariables.serverPort);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        dataOutputStream.writeBytes(request);
        String response = bufferedReader.readLine();
        socket.close();
        return response;
    }
}
