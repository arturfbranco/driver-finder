package br.com.driver.finder.server.Connection;

import br.com.driver.finder.server.Service.RequestHandler;
import br.com.driver.finder.server.Util.PropertiesReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiverSocket {

    public static void main(String[] args) throws IOException {

        Integer port = PropertiesReader.instance().getReceiverPort();
        ServerSocket serverSocket = new ServerSocket(port);

        while (true){
            Socket socket = serverSocket.accept();
            RequestHandler requestHandler = new RequestHandler(socket);
            requestHandler.run();
        }
    }

}
