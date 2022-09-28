package br.com.driver.finder.client.Connection;

import br.com.driver.finder.client.GlobalVariables;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiverSocket implements Runnable {
    private void listen() throws IOException {
        ServerSocket serverSocket = new ServerSocket(GlobalVariables.listeningPort);
        while (GlobalVariables.shouldListen){
            Socket socket = serverSocket.accept();
            InputHandler inputHandler = new InputHandler(socket);
            new Thread(inputHandler).start();
        }
        serverSocket.close();
    }

    public static void startListening(){
        GlobalVariables.shouldListen = true;
        if(GlobalVariables.listener == null){
            GlobalVariables.listener = new Thread(new ReceiverSocket());
        }
        if(!GlobalVariables.listener.isAlive()){
            GlobalVariables.listener.start();
        }
    }
    public static void stopListening(){
        GlobalVariables.shouldListen = false;
    }

    @Override
    public void run() {
        try {
            this.listen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
