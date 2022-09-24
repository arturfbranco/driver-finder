package br.com.driver.finder.server.Connection.Socket;

import br.com.driver.finder.server.Connection.RequestHandler;
import br.com.driver.finder.server.Util.PropertiesReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiverSocket {

    public static void listen() throws IOException {

        Integer port = PropertiesReader.instance().getReceiverPort();
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("The server started with success.\nListening on port " + port + "...");
        while (true){
            Socket socket = serverSocket.accept();
            RequestHandler requestHandler = new RequestHandler(socket);
            requestHandler.run();
        }
    }

}
