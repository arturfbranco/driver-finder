package br.com.driver.finder.server.Connection.Socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SenderSocket {
    public String sendRequest(String request, String ip, Integer port) throws IOException {
        Socket socket = new Socket(ip, port);

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeBytes(request);
        String response = inputReader.readLine();
        socket.close();
        return response;

    }
}
