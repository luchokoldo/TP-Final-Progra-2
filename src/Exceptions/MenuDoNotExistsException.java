package Exceptions;

public class MenuDoNotExistsException extends RuntimeException
{
    public MenuDoNotExistsException(String message)
    {
        super(message);
    }
}
