package br.com.driver.finder.server.Connection;

import br.com.driver.finder.server.Util.PropertiesReader;

import java.io.IOException;
import java.net.ServerSocket;

public class ReceiverSocket {
    public static Integer receiverPort;

    public static void main(String[] args) throws IOException {

        ReceiverSocket.receiverPort = getReceiverPort();
        ServerSocket serverSocket = new ServerSocket();
    }

    private static Integer getReceiverPort() throws IOException {
        return Integer.parseInt(new PropertiesReader().getProperties().getProperty("server-receiver-port"));
    }
}
