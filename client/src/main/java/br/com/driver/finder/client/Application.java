package br.com.driver.finder.client;

import br.com.driver.finder.client.Service.OutputService.OutputService;

import java.util.Scanner;

public class Application {
    private static OutputService outputService = new OutputService();
    public static void main(String[] args) throws Exception {
        outputService.configure("192.168.100.84", 11111, 6666);
        outputService.findDriver("Pedro Santos", "porto alegre");
        while (true){
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            if(message.equals("1")){
                outputService.endRide();
            }else{
                outputService.sendMessage(message);
            }

        }
    }
}