package com.zulily.store.controllers.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid input provided")
@Component
public class InvalidInputException extends Exception{
    /**
     * Serial UID
     */
    private static final long serialVersionUID = 6998256127773111099L;

    private int errorCode;

    public int getErrorCode()
    {
        return errorCode;
    }


    public InvalidInputException()
    {
        super();
    }


    public InvalidInputException(String message, int errorCode)
    {
        super(message);
        this.errorCode = errorCode;
    }


    public InvalidInputException(String message, Exception exception, int errorCode)
    {
        super(message, exception);
        this.errorCode = errorCode;
    }
}
