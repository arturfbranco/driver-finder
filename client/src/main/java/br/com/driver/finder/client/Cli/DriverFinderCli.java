package br.com.driver.finder.client.Cli;

import br.com.driver.finder.client.GlobalVariables;
import br.com.driver.finder.client.Service.OutputService.OutputService;
import br.com.driver.finder.client.Util.ClientType;

import java.util.Scanner;

public class DriverFinderCli {

    private final OutputService outputService;

    public DriverFinderCli(){
        this.outputService = new OutputService();
    }

    public void lauch(){
        try {
            this.settings();
            this.mainMenu();
        }catch (Exception e){
            System.out.println("Something unexpected happened! Lets try again...");
            this.lauch();
        }
    }

    private void settings(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("First of all, lets configure our client...");
        System.out.println("Server IP:");
        String serverIp = scanner.nextLine();
        System.out.println("Server Port:");
        Integer serverPort = Integer.valueOf(scanner.nextLine());
        System.out.println("Port where Client will listen:");
        Integer listeningPort = Integer.valueOf(scanner.nextLine());
        this.outputService.configure(serverIp, serverPort, listeningPort);
    }
    private void mainMenu() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Driver Finder!");
        System.out.println("Tell us a bit about yourself...");
        String option;
        do {
            System.out.println("Are you a Driver or a Passenger?\n1 - Driver\n2 - Passenger\n3 - Quit");
            option = scanner.nextLine();
            switch (option){
                case "1":
                    this.registerNewClient(ClientType.DRIVER);
                    break;
                case "2":
                    this.registerNewClient(ClientType.PASSENGER);
                    break;
                case "3":
                    break;
                default:
                    System.out.println("Invalid option. Please try again!");
            }
        } while (!option.equals("3"));
    }

    private void registerNewClient(ClientType clientType) throws Exception {
        String name = this.getUserName();
        String address = this.getUserAddress();
        System.out.println("We will process the information and get back to you as soon as possible...");
        boolean serviceOutput;
        if(clientType.equals(ClientType.DRIVER)){
            serviceOutput = this.outputService.registerDriver(name, address);
        }else{
            System.out.println("Looking for a driver for you...");
            serviceOutput = this.outputService.findDriver(name, address);
        }
        if(serviceOutput){
            this.startChat();
        }else{
            System.out.println("Do you wanna try again?");
            Scanner scanner = new Scanner(System.in);
            System.out.println("1 - Try again\n2 - Quit");
            switch (scanner.nextLine()){
                case "1":
                    this.registerNewClient(clientType);
                    break;
                case "2":
                    System.out.println("Taking you back to the main menu...");
                default:
                    System.out.println("Invalid option. Taking you back to main menu anyway...");
            }
        }
    }

    private String getUserName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("For starters, give us your full name:");
        return scanner.nextLine();
    }
    private String getUserAddress(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("And now the address you are in:");
        return scanner.nextLine();
    }

    private void startChat() throws Exception {
        Scanner scanner = new Scanner(System.in);
        if(GlobalVariables.clientType.equals(ClientType.DRIVER)){
            System.out.println("The chat will open once a passenger is connected to you...");
        }else{
            System.out.println("A chat channel was opened between you and your driver:");
        }
        System.out.println("Press 'q' at any time to finish or cancel the ride.");
        String input;
        do{
            input = scanner.nextLine();
            if(GlobalVariables.connectedUserId != null){
                if(input.equals("q")){
                    this.outputService.endRide();
                }else{
                    this.outputService.sendMessage(input);
                }
            }
        }while (!input.equals("q") && GlobalVariables.userId != null);
        if(GlobalVariables.clientType.equals(ClientType.DRIVER)){
            System.out.println("Would you like to keep looking for new passengers? (Y \\ N)");
            input = scanner.nextLine();
            if(input.equalsIgnoreCase("y")){
                this.startChat();
            }else{
                this.outputService.removeUser();
            }
        }
    }
}
