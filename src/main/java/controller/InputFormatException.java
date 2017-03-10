package controller;

/**
 * Created by melikaayoughi on 3/10/17.
 */
public class InputFormatException extends RuntimeException {
    private String message="";

    public InputFormatException(String message) {
        super(message);
        message = message;
    }

    public String getMessage(){
        return message;
    }
}
