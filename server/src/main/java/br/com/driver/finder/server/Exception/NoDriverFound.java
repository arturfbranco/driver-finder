package br.com.driver.finder.server.Exception;

public class NoDriverFound extends Exception{
    private final String message;
    public NoDriverFound(){
        this.message = "No driver was found.";
    }

    public String getMessage(){
        return this.message;
    }
}
