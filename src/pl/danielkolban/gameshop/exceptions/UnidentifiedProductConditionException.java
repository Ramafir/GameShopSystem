package pl.danielkolban.gameshop.exceptions;

public class UnidentifiedProductConditionException extends RuntimeException{
    public UnidentifiedProductConditionException(String message) {
        super(message);
    }
}
