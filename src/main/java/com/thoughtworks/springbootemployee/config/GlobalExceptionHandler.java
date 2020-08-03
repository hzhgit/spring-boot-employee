package com.thoughtworks.springbootemployee.config;

import com.thoughtworks.springbootemployee.exception.IllegalOperationException;
import com.thoughtworks.springbootemployee.exception.NoSuchDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String NOT_SUCH_DATA = "not such data";
    public static final String ILLEGAL_OPERATION_EXCEPTION = "IllegalOperationException";

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchDataException.class)
    //todo return json
    String handleNotSuchDataException() {
        return NOT_SUCH_DATA;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalOperationException.class)
    String handleIllegalOperationException(){
        return ILLEGAL_OPERATION_EXCEPTION;
    }
}
