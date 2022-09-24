package br.com.driver.finder.server.Exception;

public class AddressNotFoundException extends Exception{
    private final String message;
    public AddressNotFoundException(){
        this.message = "Address not found.";
    }

    public String getMessage(){
        return this.message;
    }
}
