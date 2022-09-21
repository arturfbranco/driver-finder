package br.com.driver.finder.server.Service;

import java.net.Socket;

public class RequestHandler implements Runnable{

    private final Socket socket;

    public RequestHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
