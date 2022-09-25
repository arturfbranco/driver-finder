package br.com.driver.finder.server;

import br.com.driver.finder.server.Connection.Socket.ReceiverSocket;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        try {
            System.out.println("Starting server...");
            ReceiverSocket.listen();
        }catch (IOException e){
            System.err.println("Internal server error: " + e.getMessage());
        }

    }
}