package ru.bkiyakov.myexp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.bkiyakov.myexp.models.User;

import java.util.function.Supplier;

@ResponseStatus(HttpStatus.NOT_FOUND) //404
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long user_id){
        super("User with id: " + user_id + " not found");
    }

}
